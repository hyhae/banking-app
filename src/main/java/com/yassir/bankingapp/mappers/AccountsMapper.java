package com.yassir.bankingapp.mappers;

import com.yassir.bankingapp.dtos.AccountDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AccountsMapper {
    @Mapping(source = "holderName", target = "customer.name")
    Account fromDtoToEntity(AccountDTO accountDto);

    @Mapping(source = "customer.name", target = "holderName")
    AccountDTO fromEntityToDto(Account account);

    @Mapping(source = "customer.name", target = "holderName")
    @Mapping(source = "id", target = "accId")
    BalanceDTO fromEntityToBalanceDTO(Account account);
}
