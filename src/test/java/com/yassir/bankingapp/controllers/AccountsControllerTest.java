package com.yassir.bankingapp.controllers;
import com.yassir.bankingapp.dtos.AccountDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.services.IAccountsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountsControllerTest {

    @Mock
    private IAccountsService accountsService;

    @InjectMocks
    private AccountsController accountsController;

    @Test
    public void testGetAccountBalance_accountExists() {
        // Arrange
        long accountId = 1L;
        BalanceDTO expectedBalance = new BalanceDTO();
        expectedBalance.setAccId(accountId);
        expectedBalance.setBalance(1000L);

        when(accountsService.getBalance(accountId)).thenReturn(expectedBalance);

        // Act
        BalanceDTO actualBalance = accountsController.getAccountBalance(accountId);

        // Assert
        assertEquals(expectedBalance, actualBalance);
        verify(accountsService, times(1)).getBalance(accountId);
    }

    @Test
    public void testCreateNewAccount_() {
        // Arrange
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setHolderName("John Doe");
        accountDTO.setBalance(1000L);

        AccountDTO expectedAccountDTO = new AccountDTO();
        expectedAccountDTO.setId(1L);
        expectedAccountDTO.setHolderName("John Doe");
        expectedAccountDTO.setBalance(1000L);

        when(accountsService.createAccount(accountDTO)).thenReturn(expectedAccountDTO);

        // Act
        AccountDTO actualAccountDTO = accountsController.createNewAccount(accountDTO);

        // Assert
        assertEquals(expectedAccountDTO, actualAccountDTO);
        verify(accountsService, times(1)).createAccount(accountDTO);
    }
}
