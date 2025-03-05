package org.example.swp391.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Size(min = 3, max = 50, message = "USERNAME_INVALID")
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Size(min = 6, max = 100, message = "EMAIL_INVALID")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Size(min = 6, max = 255, message = "PASSWORD_INVALID")
    @Column(nullable = false, length = 255)
    private String password;

    @Size(max = 50, message = "FIRSTNAME_INVALID")
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50, message = "LASTNAME_INVALID")
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(columnDefinition = "TEXT")
    private String avatar;

    @Size(min = 10, max = 10, message = "PHONE_INVALID")
    @Column(length = 20)
    private String phone;

    @Size(max = 255, message = "ADDRESS_INVALID")
    @Column(length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('user', 'admin') default 'user'")
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('active', 'inactive', 'banned') default 'active'")
    private Status status = Status.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
