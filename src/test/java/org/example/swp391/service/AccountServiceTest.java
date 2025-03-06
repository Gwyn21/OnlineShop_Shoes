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


}