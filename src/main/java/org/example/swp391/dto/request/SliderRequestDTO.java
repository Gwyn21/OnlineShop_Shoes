package org.example.swp391.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SliderRequestDTO {
    
    private String imageUrl;
    
    private String link;
    
    private String caption;
    
    @NotNull(message = "STATUS_REQUIRED")
    private Boolean status = true;
} 