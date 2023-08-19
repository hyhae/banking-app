package com.yassir.bankingapp.services;

import com.yassir.bankingapp.dtos.GenericResponseDTO;
import com.yassir.bankingapp.dtos.TransferResponseDTO;
import com.yassir.bankingapp.dtos.TransferRequestDTO;

import java.util.List;

public interface ITransfersService {
    GenericResponseDTO doTransfer(TransferRequestDTO transferDTO);

    List<TransferResponseDTO> getTransferHistoryForAccount(Long accountId);
}
