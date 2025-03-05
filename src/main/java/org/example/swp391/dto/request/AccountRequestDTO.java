package org.example.swp391.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {
    
    @Size(min = 3, max = 50, message = "USERNAME_INVALID")
    private String username;

    @Size(min = 6, max = 100, message = "EMAIL_INVALID")
    private String email;

    @Size(min = 6, max = 255, message = "PASSWORD_INVALID")
    private String password;

    @Size(max = 50, message = "FIRSTNAME_INVALID")
    private String firstName;

    @Size(max = 50, message = "LASTNAME_INVALID")
    private String lastName;

    private String avatar;

    @Size(min = 10, max = 10, message = "PHONE_INVALID")
    private String phone;

    @Size(max = 255, message = "ADDRESS_INVALID")
    private String address;

    private Role role;
    
    private Status status;
} 