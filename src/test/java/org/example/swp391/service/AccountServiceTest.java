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

    /**
     * Tests successful account update with valid information.
     * Verifies that the account is updated and returned successfully.
     */
    @Test
    void updateAccount_Success() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.updateAccount(1, account);
        assertThat(updatedAccount).isNotNull();
    }

    /**
     * Tests partial account update where only some fields are provided.
     * Verifies that only the provided fields are updated while others remain unchanged.
     */
    @Test
    void updateAccount_PartialInformation() {
        Account existingAccount = new Account();
        existingAccount.setFirstName("Old");
        existingAccount.setLastName("Name");
        existingAccount.setEmail("old@example.com");
        existingAccount.setRole(Role.ADMIN);
        existingAccount.setStatus(Status.INACTIVE);

        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(existingAccount);

        account.setFirstName("New");
        account.setLastName("Name");
        account.setEmail("new@example.com");
        account.setRole(null);
        account.setStatus(null);

        Account updatedAccount = accountService.updateAccount(1, account);
        assertThat(updatedAccount.getFirstName()).isEqualTo("New");
        assertThat(updatedAccount.getLastName()).isEqualTo("Name");
        assertThat(updatedAccount.getEmail()).isEqualTo("new@example.com");
        assertThat(updatedAccount.getRole()).isEqualTo(Role.ADMIN);
        assertThat(updatedAccount.getStatus()).isEqualTo(Status.INACTIVE);
    }

    /**
     * Tests account update with specific role and status values.
     * Verifies that the role and status are updated correctly.
     */
    @Test
    void updateAccount_RoleAndStatus() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        account.setRole(Role.ADMIN);
        account.setStatus(Status.INACTIVE);

        Account updatedAccount = accountService.updateAccount(1, account);
        assertThat(updatedAccount.getRole()).isEqualTo(Role.ADMIN);
        assertThat(updatedAccount.getStatus()).isEqualTo(Status.INACTIVE);
    }

    /**
     * Tests account update for a non-existent user ID.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void updateAccount_UserIdNotExist() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.updateAccount(1, account))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Account does not exist");
    }

    /**
     * Tests account update when the new email already exists for another account.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void updateAccount_EmailExists() {
        // Create an existing account with the same email but different ID
        Account existingAccount = new Account();
        existingAccount.setUserId(2);  // Different ID
        existingAccount.setEmail("existing@example.com");
    
        // Mock the account being updated with ID 1
        account.setUserId(1);
        account.setEmail("existing@example.com");
    
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        when(accountRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingAccount));
    
        // Try to update the account with the existing email
        assertThatThrownBy(() -> accountService.updateAccount(1, account))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email already exists");
    
        // Ensure that save() is never called because of the exception
        verify(accountRepository, never()).save(any(Account.class));
    }
    

    /**
     * Tests account update when the new username already exists for another account.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void updateAccount_UsernameExists() {
        // Create an existing account with the same username but different ID
        Account existingAccount = new Account();
        existingAccount.setUserId(2);  // Different ID
        existingAccount.setUsername("existingUser");
    
        // Mock the account being updated with ID 1
        account.setUserId(1);
        account.setUsername("existingUser");
    
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        when(accountRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingAccount));
    
        // Try to update the account with the existing username
        assertThatThrownBy(() -> accountService.updateAccount(1, account))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Username already exists");
    
        // Ensure that save() is never called because of the exception
        verify(accountRepository, never()).save(any(Account.class));
    }

    /**
     * Tests account update when user has no permission to change role and status.
     * Verifies that role and status remain unchanged.
     */
    @Test
    void updateAccount_NoPermissionChangeRoleStatus() {
        Account existingAccount = new Account();
        existingAccount.setRole(Role.USER);
        existingAccount.setStatus(Status.ACTIVE);

        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(existingAccount);

        account.setRole(null);
        account.setStatus(null);

        Account updatedAccount = accountService.updateAccount(1, account);
        assertThat(updatedAccount.getRole()).isEqualTo(Role.USER);
        assertThat(updatedAccount.getStatus()).isEqualTo(Status.ACTIVE);
    }

    /**
     * Tests account update with null fields.
     * Verifies that existing values are preserved when null values are provided.
     */
    @Test
    void updateAccount_NullFields() {
        Account existingAccount = new Account();
        existingAccount.setFirstName("Old");
        existingAccount.setLastName("Name");
        existingAccount.setEmail("old@example.com");
        existingAccount.setPhone("123456789");
        existingAccount.setAddress("Old Address");
        existingAccount.setAvatar("old.jpg");
        existingAccount.setRole(Role.USER);
        existingAccount.setStatus(Status.ACTIVE);

        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(existingAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(existingAccount);

        account.setFirstName(null);
        account.setLastName(null);
        account.setEmail(null);
        account.setPhone(null);
        account.setAddress(null);
        account.setAvatar(null);
        account.setRole(null);
        account.setStatus(null);

        Account updatedAccount = accountService.updateAccount(1, account);
        assertThat(updatedAccount.getFirstName()).isEqualTo("Old");
        assertThat(updatedAccount.getLastName()).isEqualTo("Name");
        assertThat(updatedAccount.getEmail()).isEqualTo("old@example.com");
        assertThat(updatedAccount.getPhone()).isEqualTo("123456789");
        assertThat(updatedAccount.getAddress()).isEqualTo("Old Address");
        assertThat(updatedAccount.getAvatar()).isEqualTo("old.jpg");
        assertThat(updatedAccount.getRole()).isEqualTo(Role.USER);
        assertThat(updatedAccount.getStatus()).isEqualTo(Status.ACTIVE);
    }

    /**
     * Tests successful account deletion for a user account.
     * Verifies that the delete operation is called exactly once.
     */
    @Test
    void deleteAccount_Success() {
        account.setRole(Role.USER);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        
        accountService.deleteAccount(1);
        verify(accountRepository, times(1)).delete(account);
    }


    /**
     * Tests account deletion for a non-existent user ID.
     * Verifies that an IllegalArgumentException is thrown and no delete operation is performed.
     */
    @Test
    void deleteAccount_UserIdNotExist() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.deleteAccount(1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Account does not exist");
        verify(accountRepository, never()).delete(any());
    }

    /**
     * Tests attempt to delete an admin account.
     * Verifies that an IllegalArgumentException is thrown and no delete operation is performed.
     */
    @Test
    void deleteAccount_CannotDeleteAdminAccount() {
        account.setRole(Role.ADMIN);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.deleteAccount(1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Cannot delete admin account");
        verify(accountRepository, never()).delete(any());
    }

    /**
     * Tests account deletion when user has no permission.
     * Verifies that a SecurityException is thrown.
     */
    @Test
    void deleteAccount_NoPermission() {
        account.setRole(Role.USER);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        
        // Simulate no permission scenario
        doThrow(new SecurityException("No permission")).when(accountRepository).delete(account);
        
        assertThatThrownBy(() -> accountService.deleteAccount(1))
            .isInstanceOf(SecurityException.class)
            .hasMessageContaining("No permission");
    }

    /**
     * Tests that delete operation is called exactly once during successful account deletion.
     */
    @Test
    void deleteAccount_DeleteCalledOnce() {
        account.setRole(Role.USER);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        
        accountService.deleteAccount(1);
        verify(accountRepository, times(1)).delete(account);
    }

    /**
     * Tests that no delete operation is performed when an exception occurs during account lookup.
     */
    @Test
    void deleteAccount_NoDeleteOnException() {
        when(accountRepository.findById(anyInt())).thenThrow(new IllegalArgumentException("Test exception"));
        
        assertThatThrownBy(() -> accountService.deleteAccount(1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Test exception");
        verify(accountRepository, never()).delete(any());
    }

    /**
     * Tests successful account lookup by username.
     * Verifies that the correct account is returned when the username exists.
     */
    @Test
    void findByUsername_Success() {
        when(accountRepository.findByUsername("existingUser")).thenReturn(Optional.of(account));
        
        Optional<Account> result = accountService.findByUsername("existingUser");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(account);
    }

    /**
     * Tests account lookup for a non-existent username.
     * Verifies that an empty Optional is returned.
     */
    @Test
    void findByUsername_UsernameNotExist() {
        when(accountRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());
        
        Optional<Account> result = accountService.findByUsername("nonExistingUser");
        assertThat(result).isEmpty();
    }

    /**
     * Tests account lookup with a null username.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void findByUsername_NullUsername() {
        assertThatThrownBy(() -> accountService.findByUsername(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Username cannot be null");
    }

    /**
     * Tests account lookup with an empty username.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void findByUsername_EmptyUsername() {
        assertThatThrownBy(() -> accountService.findByUsername(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Username cannot be empty");
    }

    /**
     * Tests account lookup with a blank username.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void findByUsername_BlankUsername() {
        assertThatThrownBy(() -> accountService.findByUsername("   "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Username cannot be blank");
    }

    /**
     * Tests case-insensitive username lookup.
     * Verifies that the correct account is returned regardless of username case.
     */
    @Test
    void findByUsername_CaseInsensitive() {
        // Mock repository to return account regardless of case
        when(accountRepository.findByUsername(anyString()))
            .thenAnswer(invocation -> {
                String username = invocation.getArgument(0);
                if (username.equalsIgnoreCase("existinguser")) {
                    return Optional.of(account);
                }
                return Optional.empty();
            });
        
        Optional<Account> result = accountService.findByUsername("EXISTINGUSER");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(account);
    }

    /**
     * Tests proper Optional handling during account lookup.
     * Verifies that the returned Optional contains a non-null account.
     */
    @Test
    void findByUsername_OptionalHandling() {
        when(accountRepository.findByUsername("existingUser")).thenReturn(Optional.of(account));
        
        Optional<Account> result = accountService.findByUsername("existingUser");
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isNotNull();
    }

    /**
     * Tests successful account lookup by email.
     * Verifies that the correct account is returned when the email exists.
     */
    @Test
    void findByEmail_Success() {
        when(accountRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(account));
        
        Optional<Account> result = accountService.findByEmail("existing@example.com");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(account);
    }

    /**
     * Tests account lookup for a non-existent email.
     * Verifies that an empty Optional is returned.
     */
    @Test
    void findByEmail_EmailNotExist() {
        when(accountRepository.findByEmail("nonExisting@example.com")).thenReturn(Optional.empty());
        
        Optional<Account> result = accountService.findByEmail("nonExisting@example.com");
        assertThat(result).isEmpty();
    }

    /**
     * Tests account lookup with a null email.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void findByEmail_NullEmail() {
        assertThatThrownBy(() -> accountService.findByEmail(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email cannot be null");
    }

    /**
     * Tests account lookup with an empty email.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void findByEmail_EmptyEmail() {
        assertThatThrownBy(() -> accountService.findByEmail(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email cannot be empty");
    }

    /**
     * Tests account lookup with a blank email.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void findByEmail_BlankEmail() {
        assertThatThrownBy(() -> accountService.findByEmail("   "))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email cannot be blank");
    }

    /**
     * Tests case-insensitive email lookup.
     * Verifies that the correct account is returned regardless of email case.
     */
    @Test
    void findByEmail_CaseInsensitive() {
        when(accountRepository.findByEmail(anyString()))
            .thenAnswer(invocation -> {
                String email = invocation.getArgument(0);
                if (email.equalsIgnoreCase("existing@example.com")) {
                    return Optional.of(account);
                }
                return Optional.empty();
            });
        
        Optional<Account> result = accountService.findByEmail("EXISTING@EXAMPLE.COM");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(account);
    }

    /**
     * Tests proper Optional handling during account lookup.
     * Verifies that the returned Optional contains a non-null account.
     */
    @Test
    void findByEmail_OptionalHandling() {
        when(accountRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(account));
        
        Optional<Account> result = accountService.findByEmail("existing@example.com");
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isNotNull();
    }

    /**
     * Tests successful account lookup by ID.
     * Verifies that the correct account is returned when the ID exists.
     */
    @Test
    void findById_Success() {
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        
        Optional<Account> result = accountService.findById(1);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(account);
    }

    /**
     * Tests account lookup for a non-existent ID.
     * Verifies that an empty Optional is returned.
     */
    @Test
    void findById_UserIdNotExist() {
        when(accountRepository.findById(999)).thenReturn(Optional.empty());
        
        Optional<Account> result = accountService.findById(999);
        assertThat(result).isEmpty();
    }

    /**
     * Tests account lookup with a null ID.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void findById_NullUserId() {
        assertThatThrownBy(() -> accountService.findById(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("User ID cannot be null");
    }

    /**
     * Tests account lookup with a negative ID.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void findById_NegativeUserId() {
        assertThatThrownBy(() -> accountService.findById(-1))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("User ID must be positive");
    }

    /**
     * Tests account lookup with a zero ID.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void findById_ZeroUserId() {
        assertThatThrownBy(() -> accountService.findById(0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("User ID must be positive");
    }

    /**
     * Tests proper Optional handling during account lookup.
     * Verifies that the returned Optional contains a non-null account.
     */
    @Test
    void findById_OptionalHandling() {
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        
        Optional<Account> result = accountService.findById(1);
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isNotNull();
    }
}