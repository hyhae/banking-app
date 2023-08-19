package com.yassir.bankingapp.services;

import com.yassir.bankingapp.dtos.GenericResponseDTO;
import com.yassir.bankingapp.dtos.TransferDTO;

import java.util.List;

public interface ITransfersService {
    GenericResponseDTO doTransfer(TransferDTO transferDTO);

    List<TransferDTO> getTransferHistoryForAccount(Long accountId);
}
