package com.rest.bank.repository;

import com.rest.bank.model.Account;
import com.rest.bank.service.AccountBD;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class AccountDao implements AccountBD {

    private AccountRepository accountRepository;
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account selectAccount(int id) {
        return accountRepository.selectAccount(id);
    }

    @Override
    public void updateAccount(int money, int id) {
        accountRepository.updateAccount(money,id);
    }

    @Override
    public int getBalance(int id) {
        return accountRepository.getBalance(id);
    }
}
