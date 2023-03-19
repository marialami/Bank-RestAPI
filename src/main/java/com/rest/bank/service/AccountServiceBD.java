package com.rest.bank.service;

import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountServiceBD implements AccountBD {

    private AccountRepository accountRepository;
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account selectAccount(Long accountNumber) {
        return accountRepository.selectAccount(accountNumber);
    }

    @Override
    public void updateAccount(BigDecimal balance, Long accountNumber) {
        accountRepository.updateAccount(balance,accountNumber);
    }

    @Override
    public BigDecimal getBalance(Long accountNumber) {
        return accountRepository.getBalance(accountNumber);
    }
}
