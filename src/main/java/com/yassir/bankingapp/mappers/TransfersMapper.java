package com.yassir.bankingapp.mappers;

import com.yassir.bankingapp.dtos.TransferRequestDTO;
import com.yassir.bankingapp.dtos.TransferResponseDTO;
import com.yassir.bankingapp.entities.Transfer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface TransfersMapper {


    @Mapping(source = "fromId", target = "fromAccount.id")
    @Mapping(source = "toId", target = "toAccount.id")
    Transfer fromDtoToEntity(TransferRequestDTO transferRequestDTO);

    @InheritInverseConfiguration
    TransferResponseDTO fromEntityToDto(Transfer transfer);

    @Mapping(source = "fromId", target = "fromAccount.id")
    @Mapping(source = "toId", target = "toAccount.id")
    List<TransferResponseDTO> fromListEntityToListDto(List<Transfer> transfersList);
}
