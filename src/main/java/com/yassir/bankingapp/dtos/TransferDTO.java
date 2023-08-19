package com.yassir.bankingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    Long id;
    Long fromId;
    Long toId;
    LocalDate transferDate;
    Long transferAmount;
}
