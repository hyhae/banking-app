package com.yassir.bankingapp.services;

import com.yassir.bankingapp.dtos.GenericResponseDTO;
import com.yassir.bankingapp.dtos.TransferDTO;
import com.yassir.bankingapp.entities.Account;
import com.yassir.bankingapp.entities.Transfer;
import com.yassir.bankingapp.mappers.TransfersMapper;
import com.yassir.bankingapp.repos.TransfersRepository;
import com.yassir.bankingapp.services.impl.AccountsService;
import com.yassir.bankingapp.services.impl.TransfersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TransfersServicesTest {
    @Mock
    private AccountsService accountsService;

    @Mock
    private TransfersRepository transfersRepository;

    @Mock
    private TransfersMapper transfersMapper;

    @InjectMocks
    private TransfersService transferService;

    @Test
    @Transactional
    public void testDoTransfer_Successful() {
        // Create a TransferDTO object
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setFromId(1L);
        transferDTO.setToId(2L);
        transferDTO.setTransferAmount(100L);

        // Create a mocked Account object
        Account account1 = new Account();
        account1.setId(1L);
        Account account2 = new Account();
        account2.setId(2L);

        // Mock the behavior of accountsService.getAccount()
        Mockito.when(accountsService.getAccount(1L)).thenReturn(Optional.of(account1));
        Mockito.when(accountsService.getAccount(2L)).thenReturn(Optional.of(account2));
        Mockito.when(accountsService.submitTransfer(transferDTO)).thenReturn(true);

        // Create a mocked Transfer object
        Transfer transfer = new Transfer();
        transfer.setId(1L);

        Mockito.when(transfersMapper.fromDtoToEntity(Mockito.any(TransferDTO.class))).thenReturn(transfer);

        // Mock the behavior of transfersRepository.save()
        Mockito.when(transfersRepository.save(Mockito.any(Transfer.class))).thenReturn(transfer);

        // Call the doTransfer() method
        GenericResponseDTO response = transferService.doTransfer(transferDTO);

        // Verify the response
        assertEquals("OK", response.getMsg());
        assertEquals(200L, response.getStatus());
    }

    @Test

    public void testGetTransferHistoryForAccount_ExistingAccount() {
        // Create a mocked Account object

        Account account = new Account();
        account.setId(1L);

        // Mock the behavior of accountsService.getAccount()
        Mockito.when(accountsService.getAccount(1L)).thenReturn(Optional.of(account));

        // Create a list of mocked Transfer objects
        List<Transfer> transfers = new ArrayList<>();
        Transfer transfer1 = new Transfer();
        transfer1.setId(1L);
        Transfer transfer2 = new Transfer();
        transfer2.setId(2L);
        transfers.add(transfer1);
        transfers.add(transfer2);

        // Mock the behavior of transfersRepository.findByFromAccountOrToAccount()
        Mockito.when(transfersRepository.findByFromAccountOrToAccount(account, account)).thenReturn(transfers);

        // Create a list of mocked TransferDTO objects
        List<TransferDTO> transferDTOs = new ArrayList<>();
        TransferDTO transferDTO1 = new TransferDTO();
        transferDTO1.setId(1L);
        TransferDTO transferDTO2 = new TransferDTO();
        transferDTO2.setId(2L);
        transferDTOs.add(transferDTO1);
        transferDTOs.add(transferDTO2);

        Mockito.when(transfersMapper.fromListEntityToListDto(transfers)).thenReturn(transferDTOs);

        // Call the getTransferHistoryForAccount() method
        List<TransferDTO> transferDTOResults = transferService.getTransferHistoryForAccount(1L);

        // Verify the result
        assertEquals(2, transferDTOResults.size());
        assertEquals(1L, transferDTOResults.get(0).getId());
        assertEquals(2L, transferDTOResults.get(1).getId());
    }

    @Test

    public void testGetTransferHistoryForAccount_NonExistingAccount() {
        // Mock the behavior of accountsService.getAccount()
        Mockito.when(accountsService.getAccount(1L)).thenReturn(Optional.empty());

        // Call the getTransferHistoryForAccount() method
        List<TransferDTO> transferDTOs = transferService.getTransferHistoryForAccount(1L);

        // Verify the result
        assertTrue(transferDTOs.isEmpty());
    }
}
