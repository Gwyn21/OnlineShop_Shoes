package org.example.swp391.service.impl;

import org.example.swp391.dto.request.BlogRequestDTO;
import org.example.swp391.dto.response.BlogResponseDTO;
import org.example.swp391.entity.Blog;
import org.example.swp391.entity.Blog.BlogStatus;
import org.example.swp391.exception.AppException;
import org.example.swp391.exception.ErrorCode;
import org.example.swp391.mapper.BlogMapper;
import org.example.swp391.repository.AccountRepository;
import org.example.swp391.repository.BlogRepository;
import org.example.swp391.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Blog createBlog(BlogRequestDTO blogDTO) {
        // Validate required fields
        if (blogDTO.getTitle() == null) {
            throw new AppException(ErrorCode.TITLE_NULL_ERROR);
        }
        if (blogDTO.getTitle().isEmpty()) {
            throw new AppException(ErrorCode.TITLE_EMPTY_ERROR);
        }
        if (blogDTO.getTitle().trim().isEmpty()) {
            throw new AppException(ErrorCode.TITLE_BLANK_ERROR);
        }
        
        // Validate author exists if authorId is provided
        if (blogDTO.getAuthorId() != null) {
            accountRepository.findById(blogDTO.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_EXIST_ERROR));
        }

        // Convert DTO to Entity
        Blog blog = blogMapper.toBlog(blogDTO);
        
        // Set default status as PUBLISHED if not specified
        blog.setStatus(blogDTO.getStatus() != null ? blogDTO.getStatus() : BlogStatus.PUBLISHED);

        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Integer blogId, BlogRequestDTO blogDTO) {
        // Validate blog ID
        if (blogId == null) {
            throw new AppException(ErrorCode.BLOG_ID_NULL_ERROR);
        }
        if (blogId <= 0) {
            throw new AppException(ErrorCode.BLOG_ID_POSITIVE_ERROR);
        }
        
        // Find blog by ID
        Blog existingBlog = blogRepository.findById(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST_ERROR));
        
        // Validate author exists if authorId is changed
        if (blogDTO.getAuthorId() != null && 
            (existingBlog.getAuthorId() == null || !existingBlog.getAuthorId().equals(blogDTO.getAuthorId()))) {
            accountRepository.findById(blogDTO.getAuthorId())
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_EXIST_ERROR));
        }

        // Update entity using mapper
        blogMapper.updateBlog(existingBlog, blogDTO);

        return blogRepository.save(existingBlog);
    }

    @Override
    public void deleteBlog(Integer blogId) {
        // Validate blog ID
        if (blogId == null) {
            throw new AppException(ErrorCode.BLOG_ID_NULL_ERROR);
        }
        if (blogId <= 0) {
            throw new AppException(ErrorCode.BLOG_ID_POSITIVE_ERROR);
        }
        
        // Find blog by ID
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST_ERROR));
        
        // Delete blog
        blogRepository.delete(blog);
    }

    @Override
    public BlogResponseDTO findById(Integer blogId) {
        // Validate blog ID
        if (blogId == null) {
            throw new AppException(ErrorCode.BLOG_ID_NULL_ERROR);
        }
        if (blogId <= 0) {
            throw new AppException(ErrorCode.BLOG_ID_POSITIVE_ERROR);
        }
        
        return blogMapper.toBlogResponseDTO(blogRepository.findById(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST_ERROR)));
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public List<Blog> findByAuthorId(Integer authorId) {
        // Validate author ID
        if (authorId == null) {
            throw new AppException(ErrorCode.AUTHOR_ID_NULL_ERROR);
        }
        
        // Validate author exists
        accountRepository.findById(authorId)
            .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_EXIST_ERROR));
            
        return blogRepository.findByAuthorId(authorId);
    }

    @Override
    public List<Blog> findByStatus(BlogStatus status) {
        return blogRepository.findByStatus(status);
    }
    
    @Override
    public List<Blog> findByTitle(String title) {
        if (title == null) {
            throw new AppException(ErrorCode.TITLE_NULL_ERROR);
        }
        return blogRepository.findByTitleContaining(title);
    }
} 