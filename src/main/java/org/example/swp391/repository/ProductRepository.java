package org.example.swp391.repository;

import org.example.swp391.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Tìm kiếm product theo categoryId
    List<Product> findByCategoryId(Integer categoryId);
    
    // Tìm kiếm product theo status
    List<Product> findByStatus(Boolean status);
    
    // Tìm kiếm product theo status với phân trang
    Page<Product> findByStatus(Boolean status, Pageable pageable);
    
    // Tìm kiếm product theo name chứa từ khóa
    List<Product> findByNameContaining(String name);
    
    // Tìm kiếm product theo khoảng giá
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    // Tìm kiếm product theo categoryId và status
    List<Product> findByCategoryIdAndStatus(Integer categoryId, Boolean status);
    
    // Tìm kiếm product theo categoryId và khoảng giá
    List<Product> findByCategoryIdAndPriceBetween(Integer categoryId, BigDecimal minPrice, BigDecimal maxPrice);
    
    // Lấy danh sách product tạo gần đây nhất
    List<Product> findTop10ByOrderByCreatedAtDesc();
    
    // Lấy danh sách product có stock > 0 và status = true
    List<Product> findByStockGreaterThanAndStatusTrue(Integer minStock);
} 