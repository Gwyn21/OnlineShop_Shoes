package org.example.swp391.mapper;

import org.example.swp391.dto.request.ProductRequestDTO;
import org.example.swp391.dto.response.ProductResponseDTO;
import org.example.swp391.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toProduct(ProductRequestDTO productRequestDTO);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateProduct(@MappingTarget Product product, ProductRequestDTO request);

    ProductResponseDTO toProductResponseDTO(Product product);
} 