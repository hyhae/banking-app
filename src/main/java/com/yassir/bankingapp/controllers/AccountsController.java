package com.yassir.bankingapp.controllers;

import com.yassir.bankingapp.dtos.AccountDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.services.IAccountsService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountsController {

    @Autowired
    IAccountsService accountsService;

    @GetMapping("/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public BalanceDTO getAccountBalance(@PathVariable(name="accountId") @Parameter(description = "Account id", required = true) Long accountId){
        return accountsService.getBalance(accountId);
    }

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AccountDTO createNewAccount(
            @NonNull @RequestBody @Parameter(description = "Name and initial deposit of the new account", required = true)AccountDTO accountDTO){
        return accountsService.createAccount(accountDTO);
    }

}
