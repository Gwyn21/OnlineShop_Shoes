package org.example.swp391.service.impl;

import org.example.swp391.constant.AccountConst;
import org.example.swp391.entity.Account;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;
import org.example.swp391.exception.AppException;
import org.example.swp391.exception.ErrorCode;
import org.example.swp391.repository.AccountRepository;
import org.example.swp391.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(Account account) {
        // Validate required fields
        if (account.getUsername() == null) {
            throw new AppException(ErrorCode.USERNAME_NULL_ERROR);
        }
        if (account.getEmail() == null) {
            throw new AppException(ErrorCode.EMAIL_NULL_ERROR);
        }
        if (account.getPassword() == null) {
            throw new AppException(ErrorCode.PASSWORD_NULL_ERROR);
        }

        // Ensure email and username are unique
        if (accountRepository.findByEmail(account.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED_ERROR);
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USERNAME_EXISTED_ERROR);
        }

        // Set default role as USER
        if (account.getRole() == null) {
            account.setRole(Role.USER);
        }

        // Set default status as ACTIVE
        if (account.getStatus() == null) {
            account.setStatus(Status.ACTIVE);
        }
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Integer userId, Account updatedAccount) {
        // Find account by ID
        Account existingAccount = accountRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST_ERROR));

        // Check if new username already exists
        if (updatedAccount.getUsername() != null) {
            Optional<Account> accountWithSameUsername = accountRepository.findByUsername(updatedAccount.getUsername());
            if (accountWithSameUsername.isPresent() &&
                    !accountWithSameUsername.get().getUserId().equals(existingAccount.getUserId())) {
                throw new AppException(ErrorCode.USERNAME_EXISTED_ERROR);
            }
            existingAccount.setUsername(updatedAccount.getUsername());
        }

        // Check if new email already exists
        if (updatedAccount.getEmail() != null) {
            Optional<Account> accountWithSameEmail = accountRepository.findByEmail(updatedAccount.getEmail());
            if (accountWithSameEmail.isPresent() &&
                    !accountWithSameEmail.get().getUserId().equals(existingAccount.getUserId())) {
                throw new AppException(ErrorCode.EMAIL_EXISTED_ERROR);
            }
            existingAccount.setEmail(updatedAccount.getEmail());
        }

        // Update information only if not null
        if (updatedAccount.getFirstName() != null) {
            existingAccount.setFirstName(updatedAccount.getFirstName());
        }
        if (updatedAccount.getLastName() != null) {
            existingAccount.setLastName(updatedAccount.getLastName());
        }
        if (updatedAccount.getPhone() != null) {
            existingAccount.setPhone(updatedAccount.getPhone());
        }
        if (updatedAccount.getAddress() != null) {
            existingAccount.setAddress(updatedAccount.getAddress());
        }
        if (updatedAccount.getAvatar() != null) {
            existingAccount.setAvatar(updatedAccount.getAvatar());
        }

        // Update role and status if authorized (e.g., admin)
        if (updatedAccount.getRole() != null) {
            existingAccount.setRole(updatedAccount.getRole());
        }
        if (updatedAccount.getStatus() != null) {
            existingAccount.setStatus(updatedAccount.getStatus());
        }

        return accountRepository.save(existingAccount);
    }

    @Override
    public void deleteAccount(Integer userId) {
        // Find account by ID
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST_ERROR));
    
        // Không cho phép xóa tài khoản ADMIN
        if (account.getRole() == Role.ADMIN) {
            throw new AppException(ErrorCode.CANNOT_DELETE_ADMIN_ERROR);
        }
    
        // Nếu không phải ADMIN, thực hiện xóa
        accountRepository.delete(account);
    }
    

    @Override
    public Optional<Account> findByUsername(String username) {
        if (username == null) {
            throw new AppException(ErrorCode.USERNAME_NULL_ERROR);
        }
        if (username.isEmpty()) {
            throw new AppException(ErrorCode.USERNAME_EMPTY_ERROR);
        }
        if (username.trim().isEmpty()) {
            throw new AppException(ErrorCode.USERNAME_BLANK_ERROR);
        }
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        if (email == null) {
            throw new AppException(ErrorCode.EMAIL_NULL_ERROR);
        }
        if (email.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_EMPTY_ERROR);
        }
        if (email.trim().isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_BLANK_ERROR);
        }
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> findById(Integer userId) {
        if (userId == null) {
            throw new AppException(ErrorCode.USER_ID_NULL_ERROR);
        }
        if (userId <= 0) {
            throw new AppException(ErrorCode.USER_ID_POSITIVE_ERROR);
        }
        return accountRepository.findById(userId);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findByRole(Role role) {
        return accountRepository.findByRole(role);
    }

    @Override
    public List<Account> findByStatus(Status status) {
        return accountRepository.findByStatus(status, null).getContent();
    }
}
