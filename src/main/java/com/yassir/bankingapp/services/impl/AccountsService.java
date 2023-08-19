package com.yassir.bankingapp.services.impl;

import com.yassir.bankingapp.dtos.AccountRequestDTO;
import com.yassir.bankingapp.dtos.AccountResponseDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.dtos.TransferRequestDTO;
import com.yassir.bankingapp.dtos.TransferResponseDTO;
import com.yassir.bankingapp.entities.Account;
import com.yassir.bankingapp.entities.Customer;
import com.yassir.bankingapp.mappers.AccountsMapper;
import com.yassir.bankingapp.repos.AccountsRepository;
import com.yassir.bankingapp.services.IAccountsService;
import com.yassir.bankingapp.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class AccountsService implements IAccountsService {
    @Autowired
    AccountsRepository accountsRepository;
    @Autowired
    AccountsMapper accountsMapper;
    @Autowired
    ICustomerService customerService;

    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        Assert.notNull(accountRequestDTO);
        Assert.notNull(accountRequestDTO.getHolderName());
        Assert.notNull(accountRequestDTO.getBalance());

        Account account = accountsMapper.fromDtoToEntity(accountRequestDTO);

        if(accountRequestDTO.getHolderName() != null && accountRequestDTO.getBalance()>0){
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

    @Override
    public Optional<Account> getAccount(Long fromId) {
        return accountsRepository.findById(fromId);
    }

    @Override
    @Transactional
    public boolean submitTransfer(TransferRequestDTO transferRequestDTO){
        Optional<Account> sourceAccount = accountsRepository.findById(transferRequestDTO.getFromId());
        Optional<Account> targetAccount = accountsRepository.findById(transferRequestDTO.getToId());

        if(sourceAccount.isPresent() && targetAccount.isPresent() && sourceAccount.get().getBalance()>= transferRequestDTO.getTransferAmount()){
            sourceAccount.get().setBalance(sourceAccount.get().getBalance()- transferRequestDTO.getTransferAmount());
            targetAccount.get().setBalance(targetAccount.get().getBalance()+ transferRequestDTO.getTransferAmount());
            accountsRepository.save(sourceAccount.get());
            accountsRepository.save(targetAccount.get());
            return true;
        }
        return false;
    }
}
