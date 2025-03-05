package org.example.swp391.controller;

import jakarta.validation.Valid;
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
    ApiResponse<Account> createUser(@RequestBody @Valid Account account) {
        ApiResponse<Account> response = new ApiResponse<>();
        response.setResult(accountService.createAccount(account));
        return response;
    }

    @GetMapping("/users/{id}")
    ResponseEntity<Account> getUserById(@PathVariable Integer id) {
        return ResponseEntity.of(accountService.findById(id));
    }

    @PutMapping("/users/{id}")
    public Account updateUser(@PathVariable Integer id, @RequestBody Account entity) {
        return accountService.updateAccount(id, entity);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        accountService.deleteAccount(id);
    }
}
