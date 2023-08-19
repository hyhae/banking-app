package com.yassir.bankingapp.services.impl;

import com.yassir.bankingapp.dtos.GenericResponseDTO;
import com.yassir.bankingapp.dtos.TransferDTO;
import com.yassir.bankingapp.entities.Account;
import com.yassir.bankingapp.entities.Transfer;
import com.yassir.bankingapp.mappers.TransfersMapper;
import com.yassir.bankingapp.repos.TransfersRepository;
import com.yassir.bankingapp.services.ITransfersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransfersService implements ITransfersService {

    @Autowired
    AccountsService accountsService;

    @Autowired
    TransfersMapper transfersMapper;

    @Autowired
    TransfersRepository transfersRepository;

    @Override
    @Transactional
    public GenericResponseDTO doTransfer(TransferDTO transferDTO) {
        Assert.notNull(transferDTO);
        Assert.notNull(transferDTO.getTransferAmount());
        Assert.notNull(transferDTO.getToId());
        Assert.notNull(transferDTO.getFromId());

        GenericResponseDTO response = new GenericResponseDTO();
        response.setMsg("NOK");
        response.setStatus(500L);

        boolean transferCompletedSuccessfully = accountsService.submitTransfer(transferDTO);

        if(transferCompletedSuccessfully){
            Transfer transfer = transfersMapper.fromDtoToEntity(transferDTO);
            transfer.setFromAccount(accountsService.getAccount(transferDTO.getFromId()).get());
            transfer.setToAccount(accountsService.getAccount(transferDTO.getToId()).get());
            transfer.setTransferDate(LocalDate.now());
            transfer = transfersRepository.save(transfer);
            if(transfer.getId() != null){
                response.setStatus(200L);
                response.setMsg("OK");
            }
        }
        return response;
    }

    @Override
    public List<TransferDTO> getTransferHistoryForAccount(Long accountId) {
        Optional<Account> account = accountsService.getAccount(accountId);
        if(account.isPresent()){
            List<Transfer> transfers = transfersRepository.findByFromAccountOrToAccount(account.get(), account.get());
            return transfersMapper.fromListEntityToListDto(transfers);
        }
        return new ArrayList<>();
    }
}
