package org.example.swp391.service.impl;

import org.example.swp391.dto.request.ProductRequestDTO;
import org.example.swp391.dto.response.ProductResponseDTO;
import org.example.swp391.entity.Product;
import org.example.swp391.exception.AppException;
import org.example.swp391.exception.ErrorCode;
import org.example.swp391.mapper.ProductMapper;
import org.example.swp391.repository.CategoryRepository;
import org.example.swp391.repository.ProductRepository;
import org.example.swp391.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product createProduct(ProductRequestDTO productDTO) {
        // Validate required fields
        if (productDTO.getName() == null) {
            throw new AppException(ErrorCode.PRODUCT_NAME_NULL_ERROR);
        }
        if (productDTO.getName().isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NAME_EMPTY_ERROR);
        }
        if (productDTO.getName().trim().isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NAME_BLANK_ERROR);
        }
        if (productDTO.getCategoryId() == null) {
            throw new AppException(ErrorCode.PRODUCT_CATEGORY_ID_NULL_ERROR);
        }
        if (productDTO.getPrice() == null) {
            throw new AppException(ErrorCode.PRODUCT_PRICE_NULL_ERROR);
        }
        if (productDTO.getStock() == null) {
            throw new AppException(ErrorCode.PRODUCT_STOCK_NULL_ERROR);
        }
        if (productDTO.getStatus() == null) {
            throw new AppException(ErrorCode.PRODUCT_STATUS_NULL_ERROR);
        }
        
        // Validate category exists
        if (!categoryRepository.existsById(productDTO.getCategoryId())) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXIST_ERROR);
        }
        
        // Validate price and stock
        if (productDTO.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new AppException(ErrorCode.PRODUCT_PRICE_POSITIVE_ERROR);
        }
        if (productDTO.getStock() < 0) {
            throw new AppException(ErrorCode.PRODUCT_STOCK_POSITIVE_ERROR);
        }

        // Convert DTO to Entity
        Product product = productMapper.toProduct(productDTO);

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer productId, ProductRequestDTO productDTO) {
        // Validate product ID
        if (productId == null) {
            throw new AppException(ErrorCode.PRODUCT_ID_NULL_ERROR);
        }
        if (productId <= 0) {
            throw new AppException(ErrorCode.PRODUCT_ID_POSITIVE_ERROR);
        }
        
        // Find product by ID
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_ERROR));
        
        // Validate category exists if categoryId is changed
        if (productDTO.getCategoryId() != null && 
            !productDTO.getCategoryId().equals(existingProduct.getCategoryId())) {
            if (!categoryRepository.existsById(productDTO.getCategoryId())) {
                throw new AppException(ErrorCode.CATEGORY_NOT_EXIST_ERROR);
            }
        }
        
        // Validate price and stock if provided
        if (productDTO.getPrice() != null && productDTO.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new AppException(ErrorCode.PRODUCT_PRICE_POSITIVE_ERROR);
        }
        if (productDTO.getStock() != null && productDTO.getStock() < 0) {
            throw new AppException(ErrorCode.PRODUCT_STOCK_POSITIVE_ERROR);
        }

        // Update entity using mapper
        productMapper.updateProduct(existingProduct, productDTO);

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Integer productId) {
        // Validate product ID
        if (productId == null) {
            throw new AppException(ErrorCode.PRODUCT_ID_NULL_ERROR);
        }
        if (productId <= 0) {
            throw new AppException(ErrorCode.PRODUCT_ID_POSITIVE_ERROR);
        }
        
        // Find product by ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_ERROR));
        
        // Delete product
        productRepository.delete(product);
    }

    @Override
    public ProductResponseDTO findById(Integer productId) {
        // Validate product ID
        if (productId == null) {
            throw new AppException(ErrorCode.PRODUCT_ID_NULL_ERROR);
        }
        if (productId <= 0) {
            throw new AppException(ErrorCode.PRODUCT_ID_POSITIVE_ERROR);
        }
        
        return productMapper.toProductResponseDTO(productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST_ERROR)));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByCategoryId(Integer categoryId) {
        // Validate category ID
        if (categoryId == null) {
            throw new AppException(ErrorCode.PRODUCT_CATEGORY_ID_NULL_ERROR);
        }
        if (categoryId <= 0) {
            throw new AppException(ErrorCode.PRODUCT_CATEGORY_ID_POSITIVE_ERROR);
        }
        
        // Validate category exists
        if (!categoryRepository.existsById(categoryId)) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXIST_ERROR);
        }
        
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> findByStatus(Boolean status) {
        if (status == null) {
            throw new AppException(ErrorCode.PRODUCT_STATUS_NULL_ERROR);
        }
        return productRepository.findByStatus(status);
    }
    
    @Override
    public List<Product> searchByName(String name) {
        if (name == null) {
            throw new AppException(ErrorCode.PRODUCT_NAME_NULL_ERROR);
        }
        return productRepository.findByNameContaining(name);
    }
    
    @Override
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null || maxPrice == null) {
            throw new AppException(ErrorCode.PRODUCT_PRICE_NULL_ERROR);
        }
        if (minPrice.compareTo(BigDecimal.ZERO) < 0 || maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new AppException(ErrorCode.PRODUCT_PRICE_POSITIVE_ERROR);
        }
        if (minPrice.compareTo(maxPrice) > 0) {
            throw new AppException(ErrorCode.PRODUCT_PRICE_POSITIVE_ERROR);
        }
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    @Override
    public List<Product> findByCategoryIdAndStatus(Integer categoryId, Boolean status) {
        // Validate category ID
        if (categoryId == null) {
            throw new AppException(ErrorCode.PRODUCT_CATEGORY_ID_NULL_ERROR);
        }
        if (categoryId <= 0) {
            throw new AppException(ErrorCode.PRODUCT_CATEGORY_ID_POSITIVE_ERROR);
        }
        
        // Validate status
        if (status == null) {
            throw new AppException(ErrorCode.PRODUCT_STATUS_NULL_ERROR);
        }
        
        // Validate category exists
        if (!categoryRepository.existsById(categoryId)) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXIST_ERROR);
        }
        
        return productRepository.findByCategoryIdAndStatus(categoryId, status);
    }
    
    @Override
    public List<Product> findRecentProducts(int limit) {
        if (limit <= 0) {
            limit = 10; // Default to 10 if invalid limit
        }
        return productRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .limit(limit)
                .toList();
    }
    
    @Override
    public List<Product> findAvailableProducts(Integer minStock) {
        if (minStock == null) {
            minStock = 0;
        }
        if (minStock < 0) {
            throw new AppException(ErrorCode.PRODUCT_STOCK_POSITIVE_ERROR);
        }
        return productRepository.findByStockGreaterThanAndStatusTrue(minStock);
    }
} 