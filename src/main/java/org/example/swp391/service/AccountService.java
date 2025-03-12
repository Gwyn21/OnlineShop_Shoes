package org.example.swp391.service;

import org.example.swp391.dto.request.AccountRequestDTO;
import org.example.swp391.dto.response.AccountResponseDTO;
import org.example.swp391.entity.Account;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account createAccount(AccountRequestDTO accountDTO);

    Account updateAccount(Integer userId, AccountRequestDTO accountDTO);

    void deleteAccount(Integer userId);

    AccountResponseDTO findByUsername(String username);

    AccountResponseDTO findByEmail(String email);

    AccountResponseDTO findById(Integer userId);

    List<Account> findAll();

    List<Account> findByRole(Role role);

    List<Account> findByStatus(Status status);
}
