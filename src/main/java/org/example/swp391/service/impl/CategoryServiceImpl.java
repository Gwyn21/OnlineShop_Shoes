package org.example.swp391.service.impl;

import org.example.swp391.dto.request.CategoryRequestDTO;
import org.example.swp391.dto.response.CategoryResponseDTO;
import org.example.swp391.entity.Category;
import org.example.swp391.exception.AppException;
import org.example.swp391.exception.ErrorCode;
import org.example.swp391.mapper.CategoryMapper;
import org.example.swp391.repository.CategoryRepository;
import org.example.swp391.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category createCategory(CategoryRequestDTO categoryDTO) {
        // Validate required fields
        if (categoryDTO.getName() == null) {
            throw new AppException(ErrorCode.NAME_NULL_ERROR);
        }
        if (categoryDTO.getName().isEmpty()) {
            throw new AppException(ErrorCode.NAME_EMPTY_ERROR);
        }
        if (categoryDTO.getName().trim().isEmpty()) {
            throw new AppException(ErrorCode.NAME_BLANK_ERROR);
        }
        if (categoryDTO.getStatus() == null) {
            throw new AppException(ErrorCode.CATEGORY_STATUS_NULL_ERROR);
        }

        // Check if name already exists
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new AppException(ErrorCode.NAME_EXISTED_ERROR);
        }

        // Convert DTO to Entity
        Category category = categoryMapper.toCategory(categoryDTO);

        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Integer categoryId, CategoryRequestDTO categoryDTO) {
        // Validate category ID
        if (categoryId == null) {
            throw new AppException(ErrorCode.CATEGORY_ID_NULL_ERROR);
        }
        if (categoryId <= 0) {
            throw new AppException(ErrorCode.CATEGORY_ID_POSITIVE_ERROR);
        }

        // Find category by ID
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_ERROR));

        // Check if new name already exists
        if (categoryDTO.getName() != null && !categoryDTO.getName().equals(existingCategory.getName())) {
            if (categoryRepository.existsByName(categoryDTO.getName())) {
                throw new AppException(ErrorCode.NAME_EXISTED_ERROR);
            }
        }

        // Update entity using mapper
        categoryMapper.updateCategory(existingCategory, categoryDTO);

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        // Validate category ID
        if (categoryId == null) {
            throw new AppException(ErrorCode.CATEGORY_ID_NULL_ERROR);
        }
        if (categoryId <= 0) {
            throw new AppException(ErrorCode.CATEGORY_ID_POSITIVE_ERROR);
        }

        // Find category by ID
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_ERROR));

        // Delete category
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponseDTO findById(Integer categoryId) {
        // Validate category ID
        if (categoryId == null) {
            throw new AppException(ErrorCode.CATEGORY_ID_NULL_ERROR);
        }
        if (categoryId <= 0) {
            throw new AppException(ErrorCode.CATEGORY_ID_POSITIVE_ERROR);
        }

        return categoryMapper.toCategoryResponseDTO(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_ERROR)));
    }

    @Override
    public CategoryResponseDTO findByName(String name) {
        // Validate name
        if (name == null) {
            throw new AppException(ErrorCode.NAME_NULL_ERROR);
        }
        if (name.isEmpty()) {
            throw new AppException(ErrorCode.NAME_EMPTY_ERROR);
        }
        if (name.trim().isEmpty()) {
            throw new AppException(ErrorCode.NAME_BLANK_ERROR);
        }

        return categoryMapper.toCategoryResponseDTO(categoryRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXIST_ERROR)));
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByStatus(Boolean status) {
        if (status == null) {
            throw new AppException(ErrorCode.CATEGORY_STATUS_NULL_ERROR);
        }
        return categoryRepository.findByStatus(status);
    }

    @Override
    public List<Category> searchByName(String name) {
        if (name == null) {
            throw new AppException(ErrorCode.NAME_NULL_ERROR);
        }
        return categoryRepository.findByNameContaining(name);
    }
}


