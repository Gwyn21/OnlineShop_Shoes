package com.kickzHub.kickzHub.repository;

import com.kickzHub.kickzHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByUsernameContaining(String username);
    User findByEmail(String email);
}
