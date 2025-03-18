package com.example.be.service.blog.impl;

import com.example.be.model.Blog;
import com.example.be.repository.BlogRepository;
import com.example.be.service.blog.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements IBlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Optional<Blog> getBlogById(Integer id) {
        return blogRepository.findById(id);
    }

    @Override
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Integer id, Blog blog) {
        if (blogRepository.existsById(id)) {
            blog.setId(id);
            return blogRepository.save(blog);
        }
        return null;
    }

    @Override
    public void deleteBlog(Integer id) {
        blogRepository.deleteById(id);
    }

//    @Override
//    public Page<Blog> searchBlog(String title, Pageable pageable) {
//        return blogRepository.searchBlog(title,pageable);
//    }


}

