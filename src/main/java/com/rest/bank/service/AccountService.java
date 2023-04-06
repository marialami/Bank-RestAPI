package com.rest.bank.service;

import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.repository.AccountDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountDao bd;

    public Account createAccount(User user, String type){

        Account account = Account.builder()
                .money(0)
                .date_created(new Date())
                .user(user)
                .type(type)
                .build();

        return bd.save(account);
    }

    public void makeDeposit(int id, int depositAmount) {

        bd.updateAccount(getBalance(id) + depositAmount , id);

    }

    public int getBalance(int id) {
        return bd.getBalance(id);
    }

    public Future<String> transfer(int originAccountNumber, int destinationAccountNumber, int transferAmount) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<String> future = executor
                .submit(() -> {
                    int originBalance = getBalance(originAccountNumber);
                    if(originBalance >= transferAmount) {
                        makeDeposit(originAccountNumber, -transferAmount);
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

