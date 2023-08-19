package com.yassir.bankingapp.controllers;

import com.yassir.bankingapp.dtos.TransferDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransfersController {

    @GetMapping("/history/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransferDTO> getTransferHistoryForAccount(){

        return new ArrayList<>();
    }

    @PostMapping("/{from}/to/{to}")
    @ResponseStatus(HttpStatus.CREATED)
    public TransferDTO transferFromAccount(){

        return new TransferDTO();
    }


}
