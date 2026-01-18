package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.builder.BookTemplateBuilder;
import tech.gomes.reading.management.builder.BookTemplateResponseDTOBuilder;
import tech.gomes.reading.management.controller.filter.BookTemplateFilter;
import tech.gomes.reading.management.domain.BookCategory;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.dto.book.request.BookTemplateRequestDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponsePageDTO;
import tech.gomes.reading.management.exception.BookTemplateException;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;
import tech.gomes.reading.management.repository.BookCategoryRepository;
import tech.gomes.reading.management.repository.BookTemplateRepository;
import tech.gomes.reading.management.repository.Specification.BookTemplateSpecification;
import tech.gomes.reading.management.utils.ConvertUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookTemplateService {

    private final BookTemplateRepository bookTemplateRepository;

    private final BookCategoryRepository bookCategoryRepository;

    private final UploadService uploadService;

    public BookTemplateResponsePageDTO findAllTemplatesByFilter(BookTemplateFilter filter) {
        if (filter.getTitle() == null && filter.getAuthor() == null && filter.getISBN() == null) {
            return BookTemplateResponseDTOBuilder.fromPage(Page.empty());
        }

        Specification<BookTemplate> spec = BookTemplateSpecification.byFilter(filter);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getPageSize());

        Page<BookTemplate> template = bookTemplateRepository.findAll(spec, pageable);

        return BookTemplateResponseDTOBuilder.fromPage(template);
    }

    @Transactional
    public BookTemplate getOrCreateBookTemplate(BookTemplateRequestDTO requestDTO, MultipartFile file) throws Exception {

        if (requestDTO.templateId() != null) {
            return findTemplateById(requestDTO.templateId());
        }

        String identifier = ConvertUtils.getIdentifierByRequestDTO(requestDTO);

        Optional<BookTemplate> existentTemplate = bookTemplateRepository.findByIdentifierWhenNotIsInactive(identifier);

        if (existentTemplate.isPresent()) {
            return existentTemplate.get();
        }

        Set<BookCategory> categories = getCategoriesOrCreateIfNotExist(requestDTO.categories());

        log.info(requestDTO.imgUrl());

        String coverImg = null;

        if (file != null && !file.isEmpty()) {
            coverImg = uploadService.uploadCoverImg(file);
        } else if (requestDTO.imgUrl() != null && !requestDTO.imgUrl().isBlank()) {
            coverImg = requestDTO.imgUrl();
        }

        BookTemplate template = BookTemplateBuilder.from(requestDTO, categories, coverImg);

        return bookTemplateRepository.save(template);
    }

    @Transactional
    public BookTemplateResponseDTO updateBookTemplateByAdminRequest(BookTemplateRequestDTO requestDTO, MultipartFile file) throws Exception {

        BookTemplate bookTemplate = findTemplateByIdWithAnyStatus(requestDTO.templateId());

        String identifier = ConvertUtils.getIdentifierByRequestDTO(requestDTO);

        if (!identifier.equals(bookTemplate.getISBN()) && !identifier.equals(bookTemplate.getTitleAuthor())) {
            verifyIfExistsAnyTemplateWithIdentifier(identifier);
        }

        Set<BookCategory> categories = getCategoriesOrCreateIfNotExist(requestDTO.categories());

        String coverImg = uploadService.uploadCoverImg(file);

        BookTemplateBuilder.updateBookTemplate(bookTemplate, requestDTO, categories, coverImg);

        BookTemplate updateTemplate = bookTemplateRepository.save(bookTemplate);

        return BookTemplateResponseDTOBuilder.from(updateTemplate);
    }

    public void inactiveInvalidTemplate(long id) throws Exception {
        BookTemplate bookTemplate = findTemplateByIdWithAnyStatus(id);

        bookTemplate.setStatus(TemplateStatusIndicator.INACTIVE);

        bookTemplateRepository.save(bookTemplate);
    }

    public void approveTemplate(long id) throws Exception {
        BookTemplate template = findTemplateByIdWithAnyStatus(id);

        template.setStatus(TemplateStatusIndicator.VERIFIED);

        bookTemplateRepository.save(template);
    }

    @Transactional
    public void updateBookTemplateBySuggestion(SuggestionTemplate suggestion) throws Exception {

        String identifier = ConvertUtils.getIdentifierBySuggestion(suggestion);

        if (!identifier.equals(suggestion.getBookTemplate().getISBN()) && !identifier.equals(suggestion.getBookTemplate().getTitleAuthor())) {
            verifyIfExistsAnyTemplateWithIdentifier(identifier);
        }

        Set<BookCategory> categories = getCategoriesOrCreateIfNotExist(suggestion.getSuggestedCategories());

        BookTemplate template = BookTemplateBuilder.from(suggestion, categories);

        template.setStatus(TemplateStatusIndicator.VERIFIED);

        bookTemplateRepository.save(template);
    }

    public BookTemplateResponsePageDTO findAllTemplatesByStatus(int page, int pageSize, String direction, String status) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(direction), "createdAt");

        Page<BookTemplate> bookTemplatePage = bookTemplateRepository.findByStatus(TemplateStatusIndicator.valueOf(status), pageable);

        return BookTemplateResponseDTOBuilder.fromPage(bookTemplatePage);
    }

    public BookTemplate findTemplateById(long id) throws Exception {
        return bookTemplateRepository.findByIdAndStatus(id, TemplateStatusIndicator.VERIFIED)
                .orElseThrow(() -> new BookTemplateException("O template não foi encontrado.", HttpStatus.NOT_FOUND));
    }

    public BookTemplate findTemplateByIdWithAnyStatus(long id) throws Exception {
        return bookTemplateRepository.findById(id)
                .orElseThrow(() -> new BookTemplateException("O template não foi encontrado.", HttpStatus.NOT_FOUND));
    }

    private Set<BookCategory> getCategoriesOrCreateIfNotExist(Set<String> categoriesName) {

        Set<String> categoriesNormalize = categoriesName.stream().map(ConvertUtils::normalizeCategoryName).filter(Objects::nonNull).collect(Collectors.toSet());

        Set<BookCategory> existentCategories = bookCategoryRepository.findByNameIn(categoriesNormalize);

        Set<String> existentCategoriesNames = existentCategories.stream().map(BookCategory::getName).collect(Collectors.toSet());

        Set<BookCategory> newCategories = categoriesNormalize.stream().filter(category -> !existentCategoriesNames.contains(category))
                .map(category -> BookCategory.builder().name(category).build())
                .collect(Collectors.toSet());

        if (!newCategories.isEmpty()) {
            bookCategoryRepository.saveAll(newCategories);
            existentCategories.addAll(newCategories);
        }

        return existentCategories;
    }

    private void verifyIfExistsAnyTemplateWithIdentifier(String identifier) throws Exception {

        Optional<BookTemplate> template = bookTemplateRepository.findByIdentifierWhenNotIsInactive(identifier);

        if (template.isPresent()) {
            throw new BookTemplateException("Já existe um template com esse identificador: " + identifier, HttpStatus.BAD_REQUEST);
        }
    }
}
