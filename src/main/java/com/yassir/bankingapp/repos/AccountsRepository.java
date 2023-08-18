package com.yassir.bankingapp.repos;

import com.yassir.bankingapp.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account, Long> {
}
