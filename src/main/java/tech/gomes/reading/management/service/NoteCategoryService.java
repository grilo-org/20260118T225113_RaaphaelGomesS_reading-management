package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.domain.NoteCategory;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.category.CategoryRequestDTO;
import tech.gomes.reading.management.dto.category.CategoryResponseDTO;
import tech.gomes.reading.management.exception.NoteCategoryException;
import tech.gomes.reading.management.repository.NoteCategoryRepository;
import tech.gomes.reading.management.utils.ConvertUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteCategoryService {

    private final NoteCategoryRepository categoryRepository;

    public CategoryResponseDTO createCategoryIfNotExists(CategoryRequestDTO requestDTO, User user) throws Exception {

        String normalizedName = ConvertUtils.normalizeCategoryName(requestDTO.name());

        if (categoryRepository.existsByNameAndUserId(normalizedName, user.getId())) {
            throw new NoteCategoryException("Já existe uma categoria com esse nome.", HttpStatus.BAD_REQUEST);
        }

        NoteCategory newCategory = NoteCategory.builder()
                .name(normalizedName)
                .user(user)
                .build();

        NoteCategory noteCategory = categoryRepository.save(newCategory);

        return new CategoryResponseDTO(noteCategory.getId(), noteCategory.getName());
    }

    public List<CategoryResponseDTO> getAllCategoriesFromUser(User user) {
        List<NoteCategory> categories = categoryRepository.findAllByUserId(user.getId());

        return categories.stream().map(category -> new CategoryResponseDTO(category.getId(), category.getName())).collect(Collectors.toList());
    }

    public CategoryResponseDTO updateCategory(CategoryRequestDTO requestDTO, User user) throws Exception {

        String normalizedName = ConvertUtils.normalizeCategoryName(requestDTO.name());

        if (categoryRepository.existsByNameAndUserId(normalizedName, user.getId())) {
            throw new NoteCategoryException("Já existe uma categoria com esse nome.", HttpStatus.BAD_REQUEST);
        }

        NoteCategory category = findCategory(requestDTO.id(), user.getId());

        category.setName(normalizedName);

        NoteCategory updatedCategory = categoryRepository.save(category);

        return new CategoryResponseDTO(updatedCategory.getId(), updatedCategory.getName());
    }

    public void deleteCategory(long id, User user) throws Exception {
        NoteCategory category = findCategory(id, user.getId());

        categoryRepository.delete(category);
    }

    public NoteCategory takeCategoryOrCreateIfNotExists(String categoryName, User user) {

        String normalizedName = ConvertUtils.normalizeCategoryName(categoryName);

        if (normalizedName == null) {
            return null;
        }

        NoteCategory category = categoryRepository.findByNameAndUserId(normalizedName, user.getId()).orElse(null);

        if (category == null) {
            NoteCategory newCategory = NoteCategory.builder().name(normalizedName).user(user).build();
            return categoryRepository.save(newCategory);
        }

        return category;
    }

    private NoteCategory findCategory(long id, long userId) throws Exception {
        return categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NoteCategoryException("Categoria não foi encontrada.", HttpStatus.NOT_FOUND));
    }
}
