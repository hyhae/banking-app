package com.yassir.bankingapp.services;

import com.yassir.bankingapp.dtos.AccountDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;

public interface IAccountsService {
    AccountDTO createAccount(AccountDTO accountRequestDTO);

    BalanceDTO getBalance(Long accountId);
}
