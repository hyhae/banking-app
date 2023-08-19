package com.yassir.bankingapp.controllers;

import com.yassir.bankingapp.dtos.GenericResponseDTO;
import com.yassir.bankingapp.dtos.TransferDTO;
import com.yassir.bankingapp.services.ITransfersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        List<TransferDTO> transferDTOs = new ArrayList<>();

        // Mock the behavior of transfersService.getTransferHistoryForAccount()
        when(transfersService.getTransferHistoryForAccount(1L)).thenReturn(transferDTOs);

        // Call the getTransferHistoryForAccount() method
        List<TransferDTO> result = transfersController.getTransferHistoryForAccount(1L);

        // Verify the result
        assertEquals(transferDTOs, result);

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

        TransferDTO transferDTO = new TransferDTO();
        GenericResponseDTO responseDTO = new GenericResponseDTO();
        responseDTO.setStatus(200L);
        responseDTO.setMsg("OK");

        // Mock the behavior of transfersService.doTransfer()
        when(transfersService.doTransfer(transferDTO)).thenReturn(responseDTO);

        // Call the transferFromAccount() method
        GenericResponseDTO result = transfersController.transferFromAccount(transferDTO);

        // Verify the result
        assertEquals(responseDTO, result);

        // Verify that transfersService.doTransfer() was called once with the correct parameter6
        verify(transfersService, times(1)).doTransfer(transferDTO);
    }

    @Test
    public void testTransferFromAccount_ThrowsException() {
        // Create a TransferDTO object
        TransferDTO transferDTO = new TransferDTO();

        // Mock the behavior of transfersService.doTransfer() to throw an exception
        when(transfersService.doTransfer(transferDTO)).thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        // Call the transferFromAccount() method and expect an exception
        assertThrows(ResponseStatusException.class, () -> transfersController.transferFromAccount(transferDTO));

        // Verify that transfersService.doTransfer() was called once with the correct parameter
        verify(transfersService, times(1)).doTransfer(transferDTO);
    }
}
