package org.example.swp391.service;

import org.example.swp391.entity.Account;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(Account account);

    Account updateAccount(Integer userId, Account account);

    void deleteAccount(Integer userId);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findById(Integer userId);

    List<Account> findAll();

    List<Account> findByRole(Role role);

    List<Account> findByStatus(Status status);
}
