package org.example.swp391.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Integer productId;
    
    private Integer categoryId;
    
    private String name;
    
    private String description;
    
    private BigDecimal price;
    
    private Integer stock;
    
    private String image;
    
    private Boolean status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 