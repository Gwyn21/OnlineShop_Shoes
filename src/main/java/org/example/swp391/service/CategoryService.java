package org.example.swp391.service;

import org.example.swp391.dto.request.CategoryRequestDTO;
import org.example.swp391.dto.response.CategoryResponseDTO;
import org.example.swp391.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryRequestDTO categoryDTO);

    Category updateCategory(Integer categoryId, CategoryRequestDTO categoryDTO);

    void deleteCategory(Integer categoryId);

    CategoryResponseDTO findById(Integer categoryId);

    CategoryResponseDTO findByName(String name);

    List<Category> findAll();

    List<Category> findByStatus(Boolean status);

    List<Category> searchByName(String name);
}