package com.rest.bank.controller;

import com.rest.bank.controller.dto.UserDTO;
import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.repository.TransactionRepository;
import com.rest.bank.service.AccountService;
import com.rest.bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private AccountService accountService;

    private TransactionRepository transactionRepository;


    @PostMapping("/users")
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO.getDocument(),userDTO.getName(), userDTO.getLastName());
    }

    @PostMapping("/users/{userId}/{type}")
    public String createAccount(@PathVariable int userId, @PathVariable String type) {
        User user = userService.findById(userId).get();
        if (user.getAccounts().size() >= 3) {
            return "No puedes tener mas de tres cuentas asosciadas";
        }
        else{
            return "Se creo correctamente la cuenta con número: "+accountService.createAccount(user,type).getId();
        }


    }

    @GetMapping("/{userId}/accounts")
    public List<String> getAccountsForUser(@PathVariable int userId) {
        List<String> accounts = new ArrayList<>();
        User user = userService.findById(userId).get();
        for(Account a: user.getAccounts())
        {
            accounts.add("Numero de Cuenta : "+a.getId());
        }
        return accounts;
    }
}
