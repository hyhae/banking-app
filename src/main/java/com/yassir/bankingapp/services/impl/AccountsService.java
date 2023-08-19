package com.yassir.bankingapp.services.impl;

import com.yassir.bankingapp.dtos.AccountDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.entities.Account;
import com.yassir.bankingapp.entities.Customer;
import com.yassir.bankingapp.mappers.AccountsMapper;
import com.yassir.bankingapp.repos.AccountsRepository;
import com.yassir.bankingapp.services.IAccountsService;
import com.yassir.bankingapp.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AccountsService implements IAccountsService {
    AccountsRepository accountsRepository;
    AccountsMapper accountsMapper;
    ICustomerService customerService;

    public AccountsService(AccountsRepository accountsRepository, AccountsMapper accountsMapper, ICustomerService customerService){
        this.accountsRepository = accountsRepository;
        this.accountsMapper = accountsMapper;
        this.customerService = customerService;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountRequestDTO) {
        Assert.notNull(accountRequestDTO);
        Assert.notNull(accountRequestDTO.getHolderName());
        Assert.notNull(accountRequestDTO.getBalance());

        Account account = accountsMapper.fromDtoToEntity(accountRequestDTO);

        if(accountRequestDTO.getHolderName() != null){
            Customer customer = customerService.getCustomerByName(accountRequestDTO.getHolderName());
            if(customer != null){
                account.setCustomer(customer);
            }
            else{
                Customer newCustomer = customerService.createCustomer(accountRequestDTO.getHolderName());
                account.setCustomer(newCustomer);
            }
            account = accountsRepository.save(account);
        }
        return accountsMapper.fromEntityToDto(account);
    }

    @Override
    public BalanceDTO getBalance(Long accountId) {
        try{
            return accountsMapper.fromEntityToBalanceDTO(accountsRepository.getReferenceById(accountId));
        }
        catch (Exception e){
            return new BalanceDTO();
        }
    }
}
