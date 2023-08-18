package com.yassir.bankingapp.services.impl;

import com.yassir.bankingapp.dtos.AccountDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.entities.Account;
import com.yassir.bankingapp.mappers.AccountsMapper;
import com.yassir.bankingapp.repos.AccountsRepository;
import com.yassir.bankingapp.services.IAccountsService;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountsService implements IAccountsService {

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    AccountsMapper accountsMapper;

    @Override
    public AccountDTO createAccount(AccountDTO accountRequestDTO) {
        Account account = accountsMapper.fromDtoToEntity(accountRequestDTO);
        account = accountsRepository.save(account);
        return accountsMapper.fromEntityToDto(account);
    }

    @Override
    public BalanceDTO getBalance(Long accountId) {
        return accountsMapper.fromEntityToBalanceDTO(accountsRepository.getReferenceById(accountId));
    }
}
