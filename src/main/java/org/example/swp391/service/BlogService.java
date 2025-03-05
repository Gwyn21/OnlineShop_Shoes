package org.example.swp391.service;

import org.example.swp391.dto.request.BlogRequestDTO;
import org.example.swp391.dto.response.BlogResponseDTO;
import org.example.swp391.entity.Blog;
import org.example.swp391.entity.Blog.BlogStatus;

import java.util.List;

public interface BlogService {
    Blog createBlog(BlogRequestDTO blogDTO);

    Blog updateBlog(Integer blogId, BlogRequestDTO blogDTO);

    void deleteBlog(Integer blogId);

    BlogResponseDTO findById(Integer blogId);

    List<Blog> findAll();

    List<Blog> findByAuthorId(Integer authorId);

    List<Blog> findByStatus(BlogStatus status);
    
    List<Blog> findByTitle(String title);
} 