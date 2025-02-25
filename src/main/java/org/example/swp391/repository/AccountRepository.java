package org.example.swp391.repository;


import org.example.swp391.entity.Account;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    // Tìm kiếm bằng Username (Unique)
    Optional<Account> findByUsername(String username);

    // Tìm kiếm bằng Email (Unique)
    Optional<Account> findByEmail(String email);

    // Lấy danh sách tài khoản theo Role
    List<Account> findByRole(Role role);

    // Tìm kiếm theo Status và phân trang
    Page<Account> findByStatus(Status status, Pageable pageable);

    // Tìm kiếm theo FirstName hoặc LastName
    List<Account> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    // Lấy danh sách tài khoản tạo gần đây nhất
    List<Account> findTop5ByOrderByCreatedAtDesc();
}
