package org.example.swp391.controller;

import jakarta.validation.Valid;
import org.example.swp391.dto.request.ProductRequestDTO;
import org.example.swp391.dto.response.ProductResponseDTO;
import org.example.swp391.entity.Product;
import org.example.swp391.mapper.ProductMapper;
import org.example.swp391.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        Product product = productService.createProduct(productRequestDTO);
        return new ResponseEntity<>(productMapper.toProductResponseDTO(product), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Integer productId,
            @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        Product product = productService.updateProduct(productId, productRequestDTO);
        return ResponseEntity.ok(productMapper.toProductResponseDTO(product));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Integer productId) {
        ProductResponseDTO product = productService.findById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.findAll().stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategoryId(@PathVariable Integer categoryId) {
        List<ProductResponseDTO> products = productService.findByCategoryId(categoryId).stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByStatus(@PathVariable Boolean status) {
        List<ProductResponseDTO> products = productService.findByStatus(status).stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProductsByName(@RequestParam String name) {
        List<ProductResponseDTO> products = productService.searchByName(name).stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice, 
            @RequestParam BigDecimal maxPrice) {
        List<ProductResponseDTO> products = productService.findByPriceRange(minPrice, maxPrice).stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/category/{categoryId}/status/{status}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategoryIdAndStatus(
            @PathVariable Integer categoryId,
            @PathVariable Boolean status) {
        List<ProductResponseDTO> products = productService.findByCategoryIdAndStatus(categoryId, status).stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/recent")
    public ResponseEntity<List<ProductResponseDTO>> getRecentProducts(
            @RequestParam(defaultValue = "10") Integer limit) {
        List<ProductResponseDTO> products = productService.findRecentProducts(limit).stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<ProductResponseDTO>> getAvailableProducts(
            @RequestParam(defaultValue = "0") Integer minStock) {
        List<ProductResponseDTO> products = productService.findAvailableProducts(minStock).stream()
                .map(productMapper::toProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }
} 