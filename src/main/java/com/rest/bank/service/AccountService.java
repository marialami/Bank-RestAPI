package com.rest.bank.service;

import com.rest.bank.controller.dto.DepositDTO;
import com.rest.bank.controller.dto.TransferDTO;
import com.rest.bank.feign.TransactionFeign;
import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.model.enums.AccountType;
import com.rest.bank.repository.AccountDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.AccessType;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountDao bd;
    private TransactionFeign transactionFeign;

    public Account createAccount(User user, AccountType type){

        Account account = Account.builder()
                .money(0)
                .date_created(new Date())
                .user(user)
                .type(type)
                .build();

        return bd.save(account);
    }

    public String makeDeposit(DepositDTO depositDTO) {
        return  transactionFeign.depositFunds(depositDTO);
    }

    public int getBalance(int id) {
        return bd.getBalance(id);
    }

    public String transfer(TransferDTO transferDTO) throws Exception {
        return  transactionFeign.transfer(transferDTO);
    }
}

