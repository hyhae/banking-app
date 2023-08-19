package com.yassir.bankingapp.controllers;

import com.yassir.bankingapp.dtos.GenericResponseDTO;
import com.yassir.bankingapp.dtos.TransferResponseDTO;
import com.yassir.bankingapp.dtos.TransferRequestDTO;
import com.yassir.bankingapp.services.ITransfersService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransfersController {

    @Autowired
    ITransfersService transfersService;

    @GetMapping("/history/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransferResponseDTO> getTransferHistoryForAccount(@PathVariable(name="accountId")
                                                              @Parameter(description = "Account id to review transaction history", required = true) Long accountId){
        return transfersService.getTransferHistoryForAccount(accountId);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponseDTO transferFromAccount(@RequestBody @Parameter(description = "Transfer information: From account id, target account id, amount", required = true) @NonNull
                                                  TransferRequestDTO transferDTO){

        return transfersService.doTransfer(transferDTO);
    }


}
