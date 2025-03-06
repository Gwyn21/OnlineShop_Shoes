package org.example.swp391.mapper;

import org.example.swp391.dto.request.CategoryRequestDTO;
import org.example.swp391.dto.response.CategoryResponseDTO;
import org.example.swp391.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Category toCategory(CategoryRequestDTO categoryRequestDTO);

    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryRequestDTO request);

    CategoryResponseDTO toCategoryResponseDTO(Category category);
}