package com.yassir.bankingapp.controllers;

import com.yassir.bankingapp.dtos.GenericResponseDTO;
import com.yassir.bankingapp.dtos.TransferResponseDTO;
import com.yassir.bankingapp.services.ITransfersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransfersControllerTest {

    @Mock
    private ITransfersService transfersService;

    @InjectMocks
    private TransfersController transfersController;

    @Test
    public void testGetTransferHistoryForAccount_Successful() {
        // Create a list of TransferDTO objects
        List<TransferResponseDTO> transferResponseDTOS = new ArrayList<>();

        // Mock the behavior of transfersService.getTransferHistoryForAccount()
        when(transfersService.getTransferHistoryForAccount(1L)).thenReturn(transferResponseDTOS);

        // Call the getTransferHistoryForAccount() method
        List<TransferResponseDTO> result = transfersController.getTransferHistoryForAccount(1L);

        // Verify the result
        assertEquals(transferResponseDTOS, result);

        // Verify that transfersService.getTransferHistoryForAccount() was called once with the correct parameter
        verify(transfersService, times(1)).getTransferHistoryForAccount(1L);
    }

    @Test
    public void testGetTransferHistoryForAccount_ThrowsException() {
        // Mock the behavior of transfersService.getTransferHistoryForAccount() to throw an exception
        when(transfersService.getTransferHistoryForAccount(1L)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Call the getTransferHistoryForAccount() method and expect an exception
        assertThrows(ResponseStatusException.class, () -> transfersController.getTransferHistoryForAccount(1L));

        // Verify that transfersService.getTransferHistoryForAccount() was called once with the correct parameter
        verify(transfersService, times(1)).getTransferHistoryForAccount(1L);
    }

    @Test
    public void testTransferFromAccount_Successful() {
        // Create a TransferDTO object

        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        responseDTO.setStatus(200L);
        responseDTO.setMsg("OK");

        // Mock the behavior of transfersService.doTransfer()
        when(transfersService.doTransfer(transferResponseDTO)).thenReturn(responseDTO);

        // Call the transferFromAccount() method
        GenericResponseDTO result = transfersController.transferFromAccount(transferResponseDTO);

        // Verify the result
        assertEquals(responseDTO, result);

        // Verify that transfersService.doTransfer() was called once with the correct parameter6
        verify(transfersService, times(1)).doTransfer(transferResponseDTO);
    }

    @Test
    public void testTransferFromAccount_ThrowsException() {
        // Create a TransferDTO object
        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();

        // Mock the behavior of transfersService.doTransfer() to throw an exception
        when(transfersService.doTransfer(transferResponseDTO)).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        // Call the transferFromAccount() method and expect an exception
        assertThrows(ResponseStatusException.class, () -> transfersController.transferFromAccount(transferResponseDTO));

        // Verify that transfersService.doTransfer() was called once with the correct parameter
        verify(transfersService, times(1)).doTransfer(transferResponseDTO);
    }
}
