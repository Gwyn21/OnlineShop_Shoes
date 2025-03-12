package org.example.swp391.mapper;

import org.example.swp391.dto.request.AccountRequestDTO;
import org.example.swp391.dto.response.AccountResponseDTO;
import org.example.swp391.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(AccountRequestDTO user);

    void updateAccount(@MappingTarget  Account account, AccountRequestDTO request);

    AccountResponseDTO toAccountResponseDTO(Account account);
}
