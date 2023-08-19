package com.yassir.bankingapp.services;
import com.yassir.bankingapp.dtos.AccountResponseDTO;
import com.yassir.bankingapp.dtos.BalanceDTO;
import com.yassir.bankingapp.dtos.TransferResponseDTO;
import com.yassir.bankingapp.entities.Account;
import com.yassir.bankingapp.entities.Customer;
import com.yassir.bankingapp.mappers.AccountsMapper;
import com.yassir.bankingapp.repos.AccountsRepository;
import com.yassir.bankingapp.services.impl.AccountsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountsServiceTest {

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private AccountsMapper accountsMapper;

    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private AccountsService accountsService;

    @Test
    public void testCreateAccount_customerExists() {
        // Arrange
        AccountResponseDTO accountRequestDTO = new AccountResponseDTO();
        accountRequestDTO.setHolderName("John Doe");
        accountRequestDTO.setBalance(100L);

        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);

        when(customerService.getCustomerByName("John Doe")).thenReturn(existingCustomer);

        Account account = new Account();
        account.setId(1L);
        account.setCustomer(existingCustomer);
        account.setBalance(100L);

        when(accountsMapper.fromDtoToEntity(accountRequestDTO)).thenReturn(account);
        when(accountsRepository.save(account)).thenReturn(account);
        when(accountsMapper.fromEntityToDto(account)).thenReturn(accountRequestDTO);

        // Act
        AccountResponseDTO createdAccount = accountsService.createAccount(accountRequestDTO);

        // Assert
        assertNotNull(createdAccount);
        assertEquals("John Doe", createdAccount.getHolderName());
        assertEquals(100L, createdAccount.getBalance());
        verify(accountsMapper, times(1)).fromEntityToDto(account);
        verify(accountsRepository, times(1)).save(account);
    }

    @Test
    public void testCreateAccount_newCustomer() {
        // Arrange
        AccountResponseDTO accountRequestDTO = new AccountResponseDTO();
        accountRequestDTO.setHolderName("John Doe");
        accountRequestDTO.setBalance(100L);

        Customer newCustomer = new Customer();
        newCustomer.setId(1L);

        when(customerService.getCustomerByName("John Doe")).thenReturn(null);

        when(customerService.createCustomer("John Doe")).thenReturn(newCustomer);

        Account account = new Account();
        account.setId(1L);
        account.setCustomer(newCustomer);
        account.setBalance(100L);

        when(accountsMapper.fromDtoToEntity(accountRequestDTO)).thenReturn(account);
        when(accountsRepository.save(account)).thenReturn(account);
        when(accountsMapper.fromEntityToDto(account)).thenReturn(accountRequestDTO);

        // Act
        AccountResponseDTO createdAccount = accountsService.createAccount(accountRequestDTO);

        // Assert
        assertNotNull(createdAccount);
        assertEquals("John Doe", createdAccount.getHolderName());
        assertEquals(100L, createdAccount.getBalance());
        verify(accountsMapper, times(1)).fromEntityToDto(account);
        verify(accountsRepository, times(1)).save(account);
    }

    @Test
    public void testGetBalance_accountExists() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(1000L);

        when(accountsRepository.getReferenceById(accountId)).thenReturn(account);
        BalanceDTO expectedBalanceDTO = new BalanceDTO();
        expectedBalanceDTO.setAccId(accountId);
        expectedBalanceDTO.setBalance(1000L);

        when(accountsMapper.fromEntityToBalanceDTO(account)).thenReturn(expectedBalanceDTO);

        // Act
        BalanceDTO balanceDTO = accountsService.getBalance(accountId);

        // Assert
        assertNotNull(balanceDTO);
        assertEquals(accountId, balanceDTO.getAccId());
        assertEquals(1000L, balanceDTO.getBalance());
        verify(accountsRepository, times(1)).getReferenceById(accountId);
    }

    @Test
    public void testGetBalance_accountDoesntExist() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(1000L);

        when(accountsRepository.getReferenceById(accountId)).thenReturn(null);

        when(accountsMapper.fromEntityToBalanceDTO(account)).thenThrow(NullPointerException.class);

        // Act
        BalanceDTO balanceDTO = accountsService.getBalance(accountId);

        // Assert
        assertNotNull(balanceDTO);
        assertNull(balanceDTO.getBalance());
        assertNull(balanceDTO.getAccId());
        assertNull(balanceDTO.getHolderName());
        verify(accountsRepository, times(1)).getReferenceById(accountId);
    }

    @Test
    public void testGetAccount() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);

        when(accountsRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        Optional<Account> fetchedAccount = accountsService.getAccount(accountId);

        // Assert
        assertTrue(fetchedAccount.isPresent());
        assertEquals(accountId, fetchedAccount.get().getId());
        verify(accountsRepository, times(1)).findById(accountId);
    }

    @Test
    public void testSubmitTransfer_AccountsExist() {
        // Arrange
        Long fromId = 1L;
        Long toId = 2L;
        Long transferAmount = 100L;

        Account sourceAccount = new Account();
        sourceAccount.setId(fromId);
        sourceAccount.setBalance(1000L);

        Account targetAccount = new Account();
        targetAccount.setId(toId);
        targetAccount.setBalance(500L);

        when(accountsRepository.findById(fromId)).thenReturn(Optional.of(sourceAccount));
        when(accountsRepository.findById(toId)).thenReturn(Optional.of(targetAccount));

        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        transferResponseDTO.setFromId(fromId);
        transferResponseDTO.setToId(toId);
        transferResponseDTO.setTransferAmount(transferAmount);

        // Act
        boolean transferSuccess = accountsService.submitTransfer(transferResponseDTO);

        // Assert
        assertTrue(transferSuccess);
        assertEquals(900L, sourceAccount.getBalance());
        assertEquals(600L, targetAccount.getBalance());
        verify(accountsRepository, times(1)).findById(fromId);
        verify(accountsRepository, times(1)).findById(toId);
        verify(accountsRepository, times(1)).save(sourceAccount);
        verify(accountsRepository, times(1)).save(targetAccount);
    }

    @Test
    public void testSubmitTransfer_notEnoughBalance() {
        // Arrange
        Long fromId = 1L;
        Long toId = 2L;
        Long transferAmount = 1100L;

        Account sourceAccount = new Account();
        sourceAccount.setId(fromId);
        sourceAccount.setBalance(1000L);

        Account targetAccount = new Account();
        targetAccount.setId(toId);
        targetAccount.setBalance(500L);

        when(accountsRepository.findById(fromId)).thenReturn(Optional.of(sourceAccount));
        when(accountsRepository.findById(toId)).thenReturn(Optional.of(targetAccount));

        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        transferResponseDTO.setFromId(fromId);
        transferResponseDTO.setToId(toId);
        transferResponseDTO.setTransferAmount(transferAmount);

        // Act
        boolean transferSuccess = accountsService.submitTransfer(transferResponseDTO);

        // Assert
        assertFalse(transferSuccess);
        assertEquals(1000L, sourceAccount.getBalance());
        assertEquals(500L, targetAccount.getBalance());
        verify(accountsRepository, times(1)).findById(fromId);
        verify(accountsRepository, times(1)).findById(toId);
    }

    @Test
    public void testSubmitTransfer_accountDoesntExist() {
        // Arrange
        Long fromId = 1L;
        Long toId = 2L;
        Long transferAmount = 1100L;

        Account sourceAccount = new Account();
        sourceAccount.setId(fromId);
        sourceAccount.setBalance(1000L);

        when(accountsRepository.findById(fromId)).thenReturn(Optional.of(sourceAccount));
        when(accountsRepository.findById(toId)).thenReturn(Optional.empty());

        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        transferResponseDTO.setFromId(fromId);
        transferResponseDTO.setToId(toId);
        transferResponseDTO.setTransferAmount(transferAmount);

        // Act
        boolean transferSuccess = accountsService.submitTransfer(transferResponseDTO);

        // Assert
        assertFalse(transferSuccess);
        assertEquals(1000L, sourceAccount.getBalance());
        verify(accountsRepository, times(1)).findById(fromId);
        verify(accountsRepository, times(1)).findById(toId);
    }
}
