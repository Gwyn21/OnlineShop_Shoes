package com.kickzHub.kickzHub.repository;

import com.kickzHub.kickzHub.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);
    List<Product> findByCategoriesId(Long categoryId);
}
