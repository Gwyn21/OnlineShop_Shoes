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
public class SliderResponseDTO {
    private Integer sliderId;
    
    private String imageUrl;
    
    private String link;
    
    private String caption;
    
    private Boolean status;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 