package com.yassir.bankingapp.repos;

import com.yassir.bankingapp.entities.Account;
import com.yassir.bankingapp.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransfersRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findByFromAccountOrToAccount(Account fromAccount, Account toAccount);
}
