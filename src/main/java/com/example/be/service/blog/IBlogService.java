package com.example.be.service.blog;

import com.example.be.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IBlogService {
    List<Blog> getAllBlogs();
    Optional<Blog> getBlogById(Integer id);
    Blog createBlog(Blog blog);
    Blog updateBlog(Integer id, Blog blog);
    void deleteBlog(Integer id);
//    Page<Blog> searchBlog(@Param("title") String title, Pageable pageable);
}
