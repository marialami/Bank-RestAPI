package com.rest.bank.service;

import com.rest.bank.model.Account;
import com.rest.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Async
    public Account createAccount(String name, int id){
        Account account = Account.builder()
                .documentId(id)
                .name(name)
                .balance(new BigDecimal(0))
                .accountNum(System.nanoTime())
                .creationDate(new Date())
                .build();
        accountRepository.save(account);
        return account;
    }

    @Async
    public void makeDeposit(Long accountNumber, BigDecimal depositAmount) {

        Account account = accountRepository.selectAccount(accountNumber);
        accountRepository.updateAccount(account.getBalance().add(depositAmount), accountNumber);

    }

    @Async
    public BigDecimal getBalance(Long accountNumber) {
        return accountRepository.getBalance(accountNumber);
    }

    @Async
    public Future<String> transfer(Long originAccountNumber, Long destinationAccountNumber, BigDecimal transferAmount) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<String> future = executor
                .submit(() -> {
                    BigDecimal originBalance = getBalance(originAccountNumber);
                    if(originBalance.compareTo(transferAmount) >= 1) {
                        makeDeposit(originAccountNumber, transferAmount.negate());
                        makeDeposit(destinationAccountNumber, transferAmount);
                        return "Transaction completed successfully";
                    }
                    else {
                        return "There's not enough money in your account";
                    }
                });

        executor.shutdown();

        return future;
    }
}

