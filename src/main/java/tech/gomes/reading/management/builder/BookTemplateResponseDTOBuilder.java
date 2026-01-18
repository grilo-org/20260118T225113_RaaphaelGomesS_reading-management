package tech.gomes.reading.management.builder;

import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.BookCategory;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponsePageDTO;
import tech.gomes.reading.management.utils.ConvertUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookTemplateResponseDTOBuilder {

    public static BookTemplateResponsePageDTO fromPage(Page<BookTemplate> templatePage) {
        List<BookTemplateResponseDTO> responseDTOList = templatePage.getContent().isEmpty() ?
                Collections.emptyList() : templatePage.getContent().stream().map(BookTemplateResponseDTOBuilder::from).collect(Collectors.toList());

        return BookTemplateResponsePageDTO.builder()
                .page(templatePage.getNumber())
                .pageSize(templatePage.getSize())
                .totalPages(templatePage.getTotalPages())
                .totalElements(templatePage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }

    public static BookTemplateResponseDTO from(BookTemplate template) {
        return BookTemplateResponseDTO.builder()
                .id(template.getId())
                .isbn(template.getISBN())
                .title(template.getTitle())
                .author(template.getAuthor())
                .publisher(template.getPublisher())
                .edition(template.getEdition())
                .description(template.getDescription())
                .year(template.getYearPublication())
                .pages(template.getPages())
                .img(ConvertUtils.uriCoverImg(template.getImg()))
                .status(template.getStatus().getValue())
                .categories(template.getCategories().stream().map(BookCategory::getName).collect(Collectors.toSet()))
                .build();
    }
}
