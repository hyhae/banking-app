package com.yassir.bankingapp.services;

import com.yassir.bankingapp.dtos.AccountDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.dtos.TransferDTO;
import com.yassir.bankingapp.entities.Account;

import java.util.Optional;

public interface IAccountsService {
    AccountDTO createAccount(AccountDTO accountRequestDTO);

    BalanceDTO getBalance(Long accountId);

    Optional<Account> getAccount(Long fromId);

    boolean submitTransfer(TransferDTO transferDTO);
}
