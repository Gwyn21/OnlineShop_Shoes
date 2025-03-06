package org.example.swp391.service;

import org.example.swp391.dto.request.ProductRequestDTO;
import org.example.swp391.dto.response.ProductResponseDTO;
import org.example.swp391.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequestDTO productDTO);

    Product updateProduct(Integer productId, ProductRequestDTO productDTO);

    void deleteProduct(Integer productId);

    ProductResponseDTO findById(Integer productId);

    List<Product> findAll();

    List<Product> findByCategoryId(Integer categoryId);

    List<Product> findByStatus(Boolean status);
    
    List<Product> searchByName(String name);
    
    List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Product> findByCategoryIdAndStatus(Integer categoryId, Boolean status);
    
    List<Product> findRecentProducts(int limit);
    
    List<Product> findAvailableProducts(Integer minStock);
} 