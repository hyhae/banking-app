package com.yassir.bankingapp.repos;

import com.yassir.bankingapp.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findById(Long id);
}
