package org.example.swp391.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDTO {
    private Integer categoryId;

    private String name;

    private String description;

    private Boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}