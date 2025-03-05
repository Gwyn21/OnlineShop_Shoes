package org.example.swp391.controller;

import jakarta.validation.Valid;
import org.example.swp391.dto.request.BlogRequestDTO;
import org.example.swp391.dto.response.BlogResponseDTO;
import org.example.swp391.entity.Blog;
import org.example.swp391.entity.Blog.BlogStatus;
import org.example.swp391.mapper.BlogMapper;
import org.example.swp391.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;
    
    @Autowired
    private BlogMapper blogMapper;

    @PostMapping
    public ResponseEntity<BlogResponseDTO> createBlog(@Valid @RequestBody BlogRequestDTO blogRequestDTO) {
        Blog blog = blogService.createBlog(blogRequestDTO);
        return new ResponseEntity<>(blogMapper.toBlogResponseDTO(blog), HttpStatus.CREATED);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<BlogResponseDTO> updateBlog(
            @PathVariable Integer blogId,
            @Valid @RequestBody BlogRequestDTO blogRequestDTO) {
        Blog blog = blogService.updateBlog(blogId, blogRequestDTO);
        return ResponseEntity.ok(blogMapper.toBlogResponseDTO(blog));
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Integer blogId) {
        blogService.deleteBlog(blogId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogResponseDTO> getBlogById(@PathVariable Integer blogId) {
        BlogResponseDTO blog = blogService.findById(blogId);
        return ResponseEntity.ok(blog);
    }

    @GetMapping
    public ResponseEntity<List<BlogResponseDTO>> getAllBlogs() {
        List<BlogResponseDTO> blogs = blogService.findAll().stream()
                .map(blogMapper::toBlogResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BlogResponseDTO>> getBlogsByAuthorId(@PathVariable Integer authorId) {
        List<BlogResponseDTO> blogs = blogService.findByAuthorId(authorId).stream()
                .map(blogMapper::toBlogResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BlogResponseDTO>> getBlogsByStatus(@PathVariable BlogStatus status) {
        List<BlogResponseDTO> blogs = blogService.findByStatus(status).stream()
                .map(blogMapper::toBlogResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BlogResponseDTO>> searchBlogsByTitle(@RequestParam String title) {
        List<BlogResponseDTO> blogs = blogService.findByTitle(title).stream()
                .map(blogMapper::toBlogResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(blogs);
    }
} 