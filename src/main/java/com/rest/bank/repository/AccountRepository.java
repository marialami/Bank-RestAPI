package com.rest.bank.repository;

import com.rest.bank.controller.dto.AccountDTO;
import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.accountNum = :accountNumber")
    Account selectAccount(@Param("accountNumber") Long accountNumber);

    @Modifying()
    @Transactional
    @Query("UPDATE Account a SET a.balance = :balance WHERE a.accountNum = :accountNumber")
    int updateAccount(@Param("balance") BigDecimal balance, @Param("accountNumber") Long accountNumber);

    @Query("SELECT a.balance FROM Account a WHERE a.accountNum = :accountNumber ")
    BigDecimal getBalance(@Param("accountNumber") Long accountNumber);
}
