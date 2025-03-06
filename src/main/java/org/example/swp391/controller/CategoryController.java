package org.example.swp391.controller;

import jakarta.validation.Valid;
import org.example.swp391.dto.request.CategoryRequestDTO;
import org.example.swp391.dto.response.CategoryResponseDTO;
import org.example.swp391.entity.Category;
import org.example.swp391.mapper.CategoryMapper;
import org.example.swp391.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryService.createCategory(categoryRequestDTO);
        return new ResponseEntity<>(categoryMapper.toCategoryResponseDTO(category), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Integer categoryId,
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryService.updateCategory(categoryId, categoryRequestDTO);
        return ResponseEntity.ok(categoryMapper.toCategoryResponseDTO(category));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Integer categoryId) {
        CategoryResponseDTO category = categoryService.findById(categoryId);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponseDTO> getCategoryByName(@PathVariable String name) {
        CategoryResponseDTO category = categoryService.findByName(name);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.findAll().stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoriesByStatus(@PathVariable Boolean status) {
        List<CategoryResponseDTO> categories = categoryService.findByStatus(status).stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponseDTO>> searchCategoriesByName(@RequestParam String name) {
        List<CategoryResponseDTO> categories = categoryService.searchByName(name).stream()
                .map(categoryMapper::toCategoryResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }
}
