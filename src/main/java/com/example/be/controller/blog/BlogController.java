package com.example.be.controller.blog;

import com.example.be.model.Blog;
import com.example.be.service.blog.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blog")
@CrossOrigin("*")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    // Lấy danh sách tất cả blog
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    // Lấy blog theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBlogById(@PathVariable Integer id) {
        Optional<Blog> blog = blogService.getBlogById(id);
        return blog.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Không tìm thấy blog với ID: " + id));
    }

    // API tìm kiếm blog theo tiêu đề + phân trang
//    @GetMapping("/search")
//    public ResponseEntity<Page<Blog>> searchBlog(
//            @RequestParam String title,
//            Pageable pageable
//    ) {
//        Page<Blog> blogs = blogService.searchBlog(title, pageable);
//        return ResponseEntity.ok(blogs);
//    }

    // Tạo blog mới
    @PostMapping
    public ResponseEntity<Object> createBlog(@RequestBody Blog blog) {
        try {
            Blog savedBlog = blogService.createBlog(blog);
            return ResponseEntity.ok("Tạo mới Blog thành công! ID: " + savedBlog.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi tạo mới Blog: " + e.getMessage());
        }
    }

    // Cập nhật blog theo ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Integer id, @RequestBody Blog blog) {
        try {
            Blog updatedBlog = blogService.updateBlog(id, blog);
            return ResponseEntity.ok(updatedBlog);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi cập nhật blog: " + e.getMessage());
        }
    }

    // Xóa blog theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Integer id) {
        try {
            blogService.deleteBlog(id);
            return ResponseEntity.ok("Xóa blog thành công với ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi xóa blog: " + e.getMessage());
        }
    }
}
