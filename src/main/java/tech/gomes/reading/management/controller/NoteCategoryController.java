package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.category.CategoryRequestDTO;
import tech.gomes.reading.management.dto.category.CategoryResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.NoteCategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class NoteCategoryController {

    private final AuthService authService;

    private final NoteCategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        CategoryResponseDTO responseDTO = categoryService.createCategoryIfNotExists(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategoriesOfUser(JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        List<CategoryResponseDTO> categories = categoryService.getAllCategoriesFromUser(user);

        return ResponseEntity.ok(categories);
    }

    @PutMapping("/")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@RequestBody CategoryRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        CategoryResponseDTO responseDTO = categoryService.updateCategory(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable long id, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        categoryService.deleteCategory(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
