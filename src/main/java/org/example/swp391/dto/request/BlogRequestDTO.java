package org.example.swp391.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.swp391.entity.Blog.BlogStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequestDTO {
    
    @NotBlank(message = "TITLE_REQUIRED")
    @Size(min = 3, max = 150, message = "TITLE_INVALID")
    private String title;

    private String content;

    private Integer authorId;

    private BlogStatus status;
} 