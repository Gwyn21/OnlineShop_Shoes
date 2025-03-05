package org.example.swp391.service;

import org.example.swp391.dto.request.AccountRequestDTO;
import org.example.swp391.dto.response.AccountResponseDTO;
import org.example.swp391.entity.Account;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;
import org.example.swp391.exception.AppException;
import org.example.swp391.exception.ErrorCode;
import org.example.swp391.mapper.AccountMapper;
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

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private AccountRequestDTO accountDTO;
    private Account account;
    private AccountResponseDTO accountResponseDTO;

    @BeforeEach
    void setUp() {
        accountDTO = new AccountRequestDTO();
        accountDTO.setUsername("testuser");
        accountDTO.setEmail("test@example.com");
        accountDTO.setPassword("password");

        account = new Account();
        account.setUserId(1);
        account.setUsername("testuser");
        account.setEmail("test@example.com");
        account.setPassword("password");
        account.setRole(Role.USER);
        account.setStatus(Status.ACTIVE);

        accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setUserId(1);
        accountResponseDTO.setUsername("testuser");
        accountResponseDTO.setEmail("test@example.com");
        accountResponseDTO.setRole(Role.USER);
        accountResponseDTO.setStatus(Status.ACTIVE);

        when(accountMapper.toAccount(any(AccountRequestDTO.class))).thenReturn(account);
        when(accountMapper.toAccountResponseDTO(any(Account.class))).thenReturn(accountResponseDTO);
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

        Account createdAccount = accountService.createAccount(accountDTO);

        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.getRole()).isEqualTo(Role.USER);
        assertThat(createdAccount.getStatus()).isEqualTo(Status.ACTIVE);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    /**
     * Tests account creation when the email already exists in the system.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void createAccount_EmailExists_ThrowsException() {
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.createAccount(accountDTO))
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

        assertThatThrownBy(() -> accountService.createAccount(accountDTO))
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

        accountDTO.setRole(null);
        accountDTO.setStatus(null);
        Account createdAccount = accountService.createAccount(accountDTO);

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

        accountDTO.setRole(Role.ADMIN);
        accountDTO.setStatus(Status.INACTIVE);
        Account createdAccount = accountService.createAccount(accountDTO);

        assertThat(createdAccount.getRole()).isEqualTo(Role.ADMIN);
        assertThat(createdAccount.getStatus()).isEqualTo(Status.INACTIVE);
    }

    /**
     * Tests account creation with a null username.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void createAccount_NullUsername_ThrowsException() {
        accountDTO.setUsername(null);

        assertThatThrownBy(() -> accountService.createAccount(accountDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Username cannot be null");
    }

    /**
     * Tests account creation with a null email.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void createAccount_NullEmail_ThrowsException() {
        accountDTO.setEmail(null);

        assertThatThrownBy(() -> accountService.createAccount(accountDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Email cannot be null");
    }

    /**
     * Tests account creation with a null password.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    @Test
    void createAccount_NullPassword_ThrowsException() {
        accountDTO.setPassword(null);

        assertThatThrownBy(() -> accountService.createAccount(accountDTO))
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

        Account updatedAccount = accountService.updateAccount(1, accountDTO);
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

        accountDTO.setFirstName("New");
        accountDTO.setLastName("Name");
        accountDTO.setEmail("new@example.com");
        accountDTO.setRole(null);
        accountDTO.setStatus(null);

        Account updatedAccount = accountService.updateAccount(1, accountDTO);
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

        accountDTO.setRole(Role.ADMIN);
        accountDTO.setStatus(Status.INACTIVE);

        Account updatedAccount = accountService.updateAccount(1, accountDTO);
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

        assertThatThrownBy(() -> accountService.updateAccount(1, accountDTO))
            .isInstanceOf(AppException.class)
            .hasMessageContaining(ErrorCode.ACCOUNT_NOT_EXIST_ERROR.getMessage());
    }

    /**
     * Tests account update when the new email already exists for another account.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    // @Test
    // void updateAccount_EmailExists() {
    //     Account existingAccount = new Account();
    //     existingAccount.setUserId(2);
    //     existingAccount.setEmail("existing@example.com");
    
    //     accountDTO.setUserId(1);
    //     accountDTO.setEmail("existing@example.com");
    
    //     when(accountRepository.findById(1)).thenReturn(Optional.of(account));
    //     when(accountRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(existingAccount));
    
    //     assertThatThrownBy(() -> accountService.updateAccount(1, accountDTO))
    //         .isInstanceOf(IllegalArgumentException.class)
    //         .hasMessageContaining("Email already exists");
    
    //     verify(accountRepository, never()).save(any(Account.class));
    // }
    

    /**
     * Tests account update when the new username already exists for another account.
     * Verifies that an IllegalArgumentException is thrown with the appropriate message.
     */
    // @Test
    // void updateAccount_UsernameExists() {
    //     Account existingAccount = new Account();
    //     existingAccount.setUserId(2);
    //     existingAccount.setUsername("existingUser");
    
    //     accountDTO.setUserId(1);
    //     accountDTO.setUsername("existingUser");
    
    //     when(accountRepository.findById(1)).thenReturn(Optional.of(account));
    //     when(accountRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingAccount));
    
    //     assertThatThrownBy(() -> accountService.updateAccount(1, accountDTO))
    //         .isInstanceOf(IllegalArgumentException.class)
    //         .hasMessageContaining("Username already exists");
    
    //     verify(accountRepository, never()).save(any(Account.class));
    // }

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

        accountDTO.setRole(null);
        accountDTO.setStatus(null);

        Account updatedAccount = accountService.updateAccount(1, accountDTO);
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

        accountDTO.setFirstName(null);
        accountDTO.setLastName(null);
        accountDTO.setEmail(null);
        accountDTO.setPhone(null);
        accountDTO.setAddress(null);
        accountDTO.setAvatar(null);
        accountDTO.setRole(null);
        accountDTO.setStatus(null);

        Account updatedAccount = accountService.updateAccount(1, accountDTO);
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
        accountDTO.setRole(Role.USER);
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
            .isInstanceOf(AppException.class)
            .hasMessageContaining(ErrorCode.ACCOUNT_NOT_EXIST_ERROR.getMessage());
        verify(accountRepository, never()).delete(any());
    }

    /**
     * Tests attempt to delete an admin account.
     * Verifies that an IllegalArgumentException is thrown and no delete operation is performed.
     */
    @Test
    void deleteAccount_CannotDeleteAdminAccount() {
        accountDTO.setRole(Role.ADMIN);
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
        accountDTO.setRole(Role.USER);
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
        accountDTO.setRole(Role.USER);
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
        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.of(account));
        
        AccountResponseDTO result = accountService.findByUsername("testuser");
        
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(accountMapper).toAccountResponseDTO(account);
    }

    /**
     * Tests account lookup for a non-existent username.
     * Verifies that an empty Optional is returned.
     */
    @Test
    void findByUsername_UsernameNotExist() {
        when(accountRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.findByUsername("nonExistingUser"))
            .isInstanceOf(AppException.class)
            .hasMessageContaining(ErrorCode.ACCOUNT_NOT_EXIST_ERROR.getMessage());
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
        when(accountRepository.findByUsername(anyString()))
            .thenAnswer(invocation -> {
                String username = invocation.getArgument(0);
                if (username.equalsIgnoreCase("existinguser")) {
                    return Optional.of(account);
                }
                return Optional.empty();
            });
        
        AccountResponseDTO result = accountService.findByUsername("EXISTINGUSER");
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        verify(accountMapper).toAccountResponseDTO(account);
    }

    /**
     * Tests proper Optional handling during account lookup.
     * Verifies that the returned Optional contains a non-null account.
     */
    @Test
    void findByUsername_ValidAccount() {
        when(accountRepository.findByUsername("existingUser")).thenReturn(Optional.of(account));
        
        AccountResponseDTO result = accountService.findByUsername("existingUser");
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
    }

    /**
     * Tests successful account lookup by email.
     * Verifies that the correct account is returned when the email exists.
     */
    @Test
    void findByEmail_Success() {
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.of(account));
        
        AccountResponseDTO result = accountService.findByEmail("test@example.com");
        
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(accountMapper).toAccountResponseDTO(account);
    }

    /**
     * Tests account lookup for a non-existent email.
     * Verifies that an empty Optional is returned.
     */
    @Test
    void findByEmail_EmailNotExist() {
        when(accountRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.findByEmail("nonExisting@example.com"))
            .isInstanceOf(AppException.class)
            .hasMessageContaining(ErrorCode.ACCOUNT_NOT_EXIST_ERROR.getMessage());
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
        
        AccountResponseDTO result = accountService.findByEmail("EXISTING@EXAMPLE.COM");
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        verify(accountMapper).toAccountResponseDTO(account);
    }

    /**
     * Tests proper Optional handling during account lookup.
     * Verifies that the returned Optional contains a non-null account.
     */
    @Test
    void findByEmail_ValidAccount() {
        when(accountRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(account));
        
        AccountResponseDTO result = accountService.findByEmail("existing@example.com");
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
    }

    /**
     * Tests successful account lookup by ID.
     * Verifies that the correct account is returned when the ID exists.
     */
    @Test
    void findById_Success() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        
        AccountResponseDTO result = accountService.findById(1);
        
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1);
        verify(accountMapper).toAccountResponseDTO(account);
    }

    /**
     * Tests account lookup for a non-existent ID.
     * Verifies that an empty Optional is returned.
     */
    @Test
    void findById_UserIdNotExist() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.findById(999))
            .isInstanceOf(AppException.class)
            .hasMessageContaining(ErrorCode.ACCOUNT_NOT_EXIST_ERROR.getMessage());
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
    void findById_ValidAccount() {
        when(accountRepository.findById(1)).thenReturn(Optional.of(account));
        
        AccountResponseDTO result = accountService.findById(1);
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1);
    }
}