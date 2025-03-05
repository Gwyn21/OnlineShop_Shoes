package org.example.swp391.controller;

import jakarta.validation.Valid;
import org.example.swp391.dto.request.AccountRequestDTO;
import org.example.swp391.dto.response.AccountResponseDTO;
import org.example.swp391.dto.response.ApiResponse;
import org.example.swp391.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.example.swp391.entity.Account;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {
    
    @Autowired
    private AccountService accountService;

    @GetMapping("/users")
    List<Account> getAllUsers() {
        return accountService.findAll();
    }

    @PostMapping("/users")
    ApiResponse<Account> createUser(@RequestBody @Valid AccountRequestDTO accountDTO) {
        ApiResponse<Account> response = new ApiResponse<>();
        response.setResult(accountService.createAccount(accountDTO));
        return response;
    }

    @GetMapping("/users/{id}")
    public AccountResponseDTO getUserById(@PathVariable Integer id) {
        return accountService.findById(id);
    }

    @PutMapping("/users/{id}")
    public Account updateUser(@PathVariable Integer id, @RequestBody @Valid AccountRequestDTO accountDTO) {
        return accountService.updateAccount(id, accountDTO);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        accountService.deleteAccount(id);
    }
}
