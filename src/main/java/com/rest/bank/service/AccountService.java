package com.rest.bank.service;

import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountServiceBD bd;

    public Account createAccount(User user){
        Account account = Account.builder()
                .balance(new BigDecimal(0))
                .accountNum(System.nanoTime())
                .creationDate(new Date())
                .user(user)
                .build();

        return bd.save(account);
    }

    public void makeDeposit(Long accountNumber, BigDecimal depositAmount) {

        bd.updateAccount(getBalance(accountNumber).add(depositAmount), accountNumber);

    }

    public BigDecimal getBalance(Long accountNumber) {
        return bd.getBalance(accountNumber);
    }

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

