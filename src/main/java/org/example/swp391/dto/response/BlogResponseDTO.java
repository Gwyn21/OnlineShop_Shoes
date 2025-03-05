package org.example.swp391.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.swp391.entity.Blog.BlogStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogResponseDTO {
    private Integer blogId;
    
    private String title;
    
    private String content;
    
    private Integer authorId;
    
    private BlogStatus status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 