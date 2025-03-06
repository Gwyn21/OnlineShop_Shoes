package org.example.swp391.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    
    @NotNull(message = "CATEGORY_ID_REQUIRED")
    private Integer categoryId;
    
    @NotBlank(message = "NAME_REQUIRED")
    @Size(min = 2, max = 150, message = "NAME_INVALID")
    private String name;
    
    private String description;
    
    @NotNull(message = "PRICE_REQUIRED")
    @DecimalMin(value = "0.0", inclusive = true, message = "PRICE_POSITIVE")
    private BigDecimal price;
    
    @NotNull(message = "STOCK_REQUIRED")
    @Min(value = 0, message = "STOCK_POSITIVE")
    private Integer stock;
    
    private String image;
    
    @NotNull(message = "STATUS_REQUIRED")
    private Boolean status = true;
} 