package org.example.swp391.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDTO {

    @NotBlank(message = "NAME_REQUIRED")
    @Size(min = 2, max = 100, message = "NAME_INVALID")
    private String name;

    private String description;

    @NotNull(message = "STATUS_REQUIRED")
    private Boolean status = true;
}