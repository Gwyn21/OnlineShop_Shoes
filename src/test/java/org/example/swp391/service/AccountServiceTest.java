package org.example.swp391.service;

import org.example.swp391.entity.Account;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;
import org.example.swp391.repository.AccountRepository;
import org.example.swp391.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setUsername("testuser");
        account.setEmail("test@example.com");
        account.setPassword("password");
    }

    /**
     * Tests successful account creation with unique username and email.
     * Verifies that the account is created with default Role.USER and Status.ACTIVE.
     */
    @Test
    void createAccount_Success() {
        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccount(account);

        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.getRole()).isEqualTo(Role.USER);
        assertThat(createdAccount.getStatus()).isEqualTo(Status.ACTIVE);
        verify(accountRepository, times(1)).save(account);
    }

    /**
     * Tests account creation when the email already exists in the system.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void createAccount_EmailExists_ThrowsException() {
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.createAccount(account))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email already exists");
    }

    /**
     * Tests account creation when the username already exists in the system.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void createAccount_UsernameExists_ThrowsException() {
        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.createAccount(account))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Username already exists");
    }

    /**
     * Tests default role and status assignment during account creation.
     * Verifies that an account is created with Role.USER and Status.ACTIVE when not specified.
     */
    @Test
    void createAccount_DefaultRoleAndStatus() {
        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        account.setRole(null);
        account.setStatus(null);
        Account createdAccount = accountService.createAccount(account);

        assertThat(createdAccount.getRole()).isEqualTo(Role.USER);
        assertThat(createdAccount.getStatus()).isEqualTo(Status.ACTIVE);
    }

    /**
     * Tests account creation with specific role and status values.
     * Verifies that the account is created with the specified Role and Status.
     */
    @Test
    void createAccount_WithSpecificRoleAndStatus() {
        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        account.setRole(Role.ADMIN);
        account.setStatus(Status.INACTIVE);
        Account createdAccount = accountService.createAccount(account);

        assertThat(createdAccount.getRole()).isEqualTo(Role.ADMIN);
        assertThat(createdAccount.getStatus()).isEqualTo(Status.INACTIVE);
    }

    /**
     * Tests account creation with a null username.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void createAccount_NullUsername_ThrowsException() {
        account.setUsername(null);

        assertThatThrownBy(() -> accountService.createAccount(account))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Username cannot be null");
    }

    /**
     * Tests account creation with a null email.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void createAccount_NullEmail_ThrowsException() {
        account.setEmail(null);

        assertThatThrownBy(() -> accountService.createAccount(account))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email cannot be null");
    }

    /**
     * Tests account creation with a null password.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void createAccount_NullPassword_ThrowsException() {
        account.setPassword(null);

        assertThatThrownBy(() -> accountService.createAccount(account))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Password cannot be null");
    }

}