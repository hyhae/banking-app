package com.yassir.bankingapp.controllers;
import com.yassir.bankingapp.dtos.AccountResponseDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.services.IAccountsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setHolderName("John Doe");
        accountResponseDTO.setBalance(1000L);

        AccountResponseDTO expectedAccountResponseDTO = new AccountResponseDTO();
        expectedAccountResponseDTO.setId(1L);
        expectedAccountResponseDTO.setHolderName("John Doe");
        expectedAccountResponseDTO.setBalance(1000L);

        when(accountsService.createAccount(accountResponseDTO)).thenReturn(expectedAccountResponseDTO);

        // Act
        AccountResponseDTO actualAccountResponseDTO = accountsController.createNewAccount(accountResponseDTO);

        // Assert
        assertEquals(expectedAccountResponseDTO, actualAccountResponseDTO);
        verify(accountsService, times(1)).createAccount(accountResponseDTO);
    }
}
