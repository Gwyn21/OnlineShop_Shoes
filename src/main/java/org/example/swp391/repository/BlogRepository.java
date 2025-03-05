package org.example.swp391.repository;

import org.example.swp391.entity.Blog;
import org.example.swp391.entity.Blog.BlogStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    // Tìm kiếm bằng Title
    List<Blog> findByTitleContaining(String title);

    // Tìm kiếm bằng AuthorId
    List<Blog> findByAuthorId(Integer authorId);

    // Lấy danh sách blog theo Status
    List<Blog> findByStatus(BlogStatus status);

    // Tìm kiếm theo Status và phân trang
    Page<Blog> findByStatus(BlogStatus status, Pageable pageable);

    // Tìm kiếm theo AuthorId và Status
    List<Blog> findByAuthorIdAndStatus(Integer authorId, BlogStatus status);

    // Lấy danh sách blog tạo gần đây nhất
    List<Blog> findTop5ByOrderByCreatedAtDesc();
    
    // Lấy danh sách blog theo AuthorId và sắp xếp theo thời gian tạo giảm dần
    List<Blog> findByAuthorIdOrderByCreatedAtDesc(Integer authorId);
    
    // Đếm số lượng blog theo AuthorId
    Long countByAuthorId(Integer authorId);
} 