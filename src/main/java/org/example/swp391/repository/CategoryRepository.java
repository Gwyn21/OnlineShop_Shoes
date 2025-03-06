package org.example.swp391.repository;

import org.example.swp391.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Tìm kiếm category theo name (unique)
    Optional<Category> findByName(String name);

    // Tìm kiếm category theo status
    List<Category> findByStatus(Boolean status);

    // Tìm kiếm category theo status với phân trang
    Page<Category> findByStatus(Boolean status, Pageable pageable);

    // Tìm kiếm category theo name chứa từ khóa
    List<Category> findByNameContaining(String name);

    // Kiểm tra tên category đã tồn tại chưa
    boolean existsByName(String name);

    // Lấy danh sách category tạo gần đây nhất
    List<Category> findTop5ByOrderByCreatedAtDesc();
}

