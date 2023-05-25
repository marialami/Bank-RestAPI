package com.rest.bank.controller;

import com.rest.bank.controller.dto.DepositDTO;
import com.rest.bank.controller.dto.TransferDTO;
import com.rest.bank.service.AccountService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PutMapping(path = "/deposit")
    public String depositFunds(@RequestBody DepositDTO depositDTO) {
        return accountService.makeDeposit(depositDTO);
    }

    @GetMapping(path = "/myBalance/{accountNumber}")
    public int getBalance(@PathVariable int accountNumber){

        return accountService.getBalance(accountNumber);
    }

    @PutMapping(path = "/transfer")
    public String transfer(@RequestBody TransferDTO transferDTO) throws Exception {

        return accountService.transfer(transferDTO);
    }
}
