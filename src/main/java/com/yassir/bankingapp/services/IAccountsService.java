package com.yassir.bankingapp.services;

import com.yassir.bankingapp.dtos.AccountRequestDTO;
import com.yassir.bankingapp.dtos.AccountResponseDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.dtos.TransferRequestDTO;
import com.yassir.bankingapp.dtos.TransferResponseDTO;
import com.yassir.bankingapp.entities.Account;

import java.util.Optional;

public interface IAccountsService {
    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);

    BalanceDTO getBalance(Long accountId);

    Optional<Account> getAccount(Long fromId);

    boolean submitTransfer(TransferRequestDTO transferRequestDTO);
}
