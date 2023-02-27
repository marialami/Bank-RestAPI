package com.rest.bank.controller;

import com.rest.bank.controller.dto.AccountDTO;
import com.rest.bank.model.Account;
import com.rest.bank.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path="/createAccount", consumes="application/json")
    public Account generateAccount(@RequestBody AccountDTO account) {

        return accountService.createAccount(account.getName(), account.getDocumentId());
    }

    @PutMapping(path = "/deposit/{accountNumber}/{depositAmount}")
    public void depositFunds(@PathVariable Long accountNumber, @PathVariable BigDecimal depositAmount) {
        accountService.makeDeposit(accountNumber, depositAmount);
    }

    @GetMapping(path = "/myBalance/{accountNumber}")
    public BigDecimal getBalance(@PathVariable Long accountNumber){

        return accountService.getBalance(accountNumber);
    }

    @PutMapping(path = "/transfer/{originAccountNumber}/{destinationAccountNumber}/{transferAmount}")
    public String transfer(@PathVariable Long originAccountNumber, @PathVariable Long destinationAccountNumber, @PathVariable BigDecimal transferAmount) throws Exception {

        Future<String> futureTransfer = accountService.transfer(originAccountNumber, destinationAccountNumber, transferAmount);
        return futureTransfer.get();
    }
}
