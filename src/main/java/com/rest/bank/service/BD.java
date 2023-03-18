package com.rest.bank.service;

import com.rest.bank.model.Account;

import java.math.BigDecimal;

public interface BD {

    Account save(Account account);

    Account selectAccount(Long accountNumber);

    void updateAccount(BigDecimal balance, Long accountNumber);

    BigDecimal getBalance(Long accountNumber);




}
