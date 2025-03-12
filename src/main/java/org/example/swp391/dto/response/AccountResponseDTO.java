package org.example.swp391.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDTO {
    private Integer userId;

    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String avatar;

    private String phone;

    private String address;

    private Role role;

    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
