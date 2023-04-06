package com.rest.bank.controller;

import com.rest.bank.controller.dto.AccountDTO;
import com.rest.bank.controller.dto.TransferDTO;
import com.rest.bank.service.AccountService;
import com.rest.bank.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

@RestController
public class AccountController {

    private final AccountService accountService;

    private final TransactionService transactionService;


    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }


    @PutMapping(path = "/deposit")
    public String depositFunds(@RequestBody AccountDTO accountDTO) {
            accountService.makeDeposit(accountDTO.getAccountNumber(), accountDTO.getDepositAmount());
            return "Deposit completed successfully";
    }

    @GetMapping(path = "/myBalance/{accountNumber}")
    public int getBalance(@PathVariable int accountNumber){

        return accountService.getBalance(accountNumber);
    }

    @PutMapping(path = "/transfer")
    public String transfer(@RequestBody TransferDTO transferDTO) throws Exception {

        Future<String> futureTransfer = accountService.transfer(transferDTO.getOrigin(),transferDTO.getDestination(),transferDTO.getAmount());
        String futureResult = futureTransfer.get();
        if(futureResult.equals("Transaction completed successfully"))
            transactionService.createTransaction(transferDTO.getOrigin(),transferDTO.getDestination(),transferDTO.getAmount());
        return futureTransfer.get();
    }
}
