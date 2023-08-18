package com.yassir.bankingapp.controllers;

import com.yassir.bankingapp.dtos.TransferDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransfersController {

    @GetMapping("/history/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TransferHistoryDTO getTransferHistoryForAccount(){

        return new TransferHistoryDTO();
    }

    @PostMapping("/{from}/to/{to}")
    @ResponseStatus(HttpStatus.CREATED)
    public TransferDTO transferFromAccount(){

        return new TransferDTO();
    }


}
