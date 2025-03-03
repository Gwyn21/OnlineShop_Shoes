package org.example.swp391.service.impl;

import org.example.swp391.dto.request.AccountRequestDTO;
import org.example.swp391.dto.response.AccountResponseDTO;
import org.example.swp391.entity.Account;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;
import org.example.swp391.exception.AppException;
import org.example.swp391.exception.ErrorCode;
import org.example.swp391.mapper.AccountMapper;
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
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account createAccount(AccountRequestDTO accountDTO) {
        // Validate required fields
        if (accountDTO.getUsername() == null) {
            throw new AppException(ErrorCode.USERNAME_NULL_ERROR);
        }
        if (accountDTO.getEmail() == null) {
            throw new AppException(ErrorCode.EMAIL_NULL_ERROR);
        }
        if (accountDTO.getPassword() == null) {
            throw new AppException(ErrorCode.PASSWORD_NULL_ERROR);
        }

        // Ensure email and username are unique
        if (accountRepository.findByEmail(accountDTO.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED_ERROR);
        }
        if (accountRepository.findByUsername(accountDTO.getUsername()).isPresent()) {
            throw new AppException(ErrorCode.USERNAME_EXISTED_ERROR);
        }

        // Convert DTO to Entity
        Account account = accountMapper.toAccount(accountDTO);


        // Set default role as USER if not specified
        account.setRole(accountDTO.getRole() != null ? accountDTO.getRole() : Role.USER);

        // Set default status as ACTIVE if not specified
        account.setStatus(accountDTO.getStatus() != null ? accountDTO.getStatus() : Status.ACTIVE);

        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Integer userId, AccountRequestDTO accountDTO) {
        // Find account by ID
        Account existingAccount = accountRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST_ERROR));

        // Check if new username already exists
        if (accountDTO.getUsername() != null) {
            Optional<Account> accountWithSameUsername = accountRepository.findByUsername(accountDTO.getUsername());
            if (accountWithSameUsername.isPresent() &&
                    !accountWithSameUsername.get().getUserId().equals(existingAccount.getUserId())) {
                throw new AppException(ErrorCode.USERNAME_EXISTED_ERROR);
            }
        }

        // Check if new email already exists
        if (accountDTO.getEmail() != null) {
            Optional<Account> accountWithSameEmail = accountRepository.findByEmail(accountDTO.getEmail());
            if (accountWithSameEmail.isPresent() &&
                    !accountWithSameEmail.get().getUserId().equals(existingAccount.getUserId())) {
                throw new AppException(ErrorCode.EMAIL_EXISTED_ERROR);
            }
        }

        // Update entity using mapper
        accountMapper.updateAccount(existingAccount, accountDTO);

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
    public AccountResponseDTO findByUsername(String username) {
        if (username == null) {
            throw new AppException(ErrorCode.USERNAME_NULL_ERROR);
        }
        if (username.isEmpty()) {
            throw new AppException(ErrorCode.USERNAME_EMPTY_ERROR);
        }
        if (username.trim().isEmpty()) {
            throw new AppException(ErrorCode.USERNAME_BLANK_ERROR);
        }
        return accountMapper.toAccountResponseDTO(accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST_ERROR)));
    }

    @Override
    public AccountResponseDTO findByEmail(String email) {
        if (email == null) {
            throw new AppException(ErrorCode.EMAIL_NULL_ERROR);
        }
        if (email.isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_EMPTY_ERROR);
        }
        if (email.trim().isEmpty()) {
            throw new AppException(ErrorCode.EMAIL_BLANK_ERROR);
        }
        return accountMapper.toAccountResponseDTO(accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST_ERROR)));
    }

    @Override
    public AccountResponseDTO findById(Integer userId) {
        if (userId == null) {
            throw new AppException(ErrorCode.USER_ID_NULL_ERROR);
        }
        if (userId <= 0) {
            throw new AppException(ErrorCode.USER_ID_POSITIVE_ERROR);
        }
        return accountMapper.toAccountResponseDTO(accountRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXIST_ERROR)));
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
