package com.yassir.bankingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "acc_id")
    Long id;

    @Column(name = "holder_name")
    String holderName;

    @Column(name = "balance")
    Long balance;
}
