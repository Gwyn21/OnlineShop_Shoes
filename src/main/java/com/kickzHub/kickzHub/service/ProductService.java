package com.kickzHub.kickzHub.service;

import com.kickzHub.kickzHub.model.Product;
import com.kickzHub.kickzHub.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private OrderService orderService;

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        product.setId(id);
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public List<Product> searchProductsByName(String name) {
        return repository.findByNameContaining(name);
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return repository.findByCategoriesId(categoryId);
    }

    public List<Product> getProductsByUserId(Long userId) {
        return orderService.getProductsByUserId(userId);
    }
}
