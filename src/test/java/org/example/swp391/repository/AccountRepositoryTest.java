package org.example.swp391.repository;

import org.example.swp391.entity.Account;
import org.example.swp391.entity.Role;
import org.example.swp391.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test") // Sử dụng cấu hình trong application-test.properties
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        Account account1 = new Account();
        account1.setUsername("john_doe");
        account1.setEmail("john@example.com");
        account1.setPassword("password123");
        account1.setRole(Role.USER);
        account1.setStatus(Status.ACTIVE);

        Account account2 = new Account();
        account2.setUsername("admin01");
        account2.setEmail("admin@example.com");
        account2.setPassword("adminpassword");
        account2.setRole(Role.ADMIN);
        account2.setStatus(Status.ACTIVE);

        accountRepository.save(account1);
        accountRepository.save(account2);
    }

    @Test
    @DisplayName("Test findByUsername() - Success")
    void testFindByUsername_Success() {
        Optional<Account> account = accountRepository.findByUsername("john_doe");
        assertThat(account).isPresent();
        assertThat(account.get().getEmail()).isEqualTo("john@example.com");
    }
}
