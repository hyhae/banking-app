package com.yassir.bankingapp.mappers;

import com.yassir.bankingapp.dtos.AccountDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.entities.Account;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AccountsMapper {
    Account fromDtoToEntity(AccountDTO accountDto);

    AccountDTO fromEntityToDto(Account account);

    BalanceDTO fromEntityToBalanceDTO(Account account);
}
