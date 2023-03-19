package com.rest.bank.controller;

import com.rest.bank.controller.dto.AccountDTO;
import com.rest.bank.controller.dto.UserDTO;
import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.repository.AccountRepository;
import com.rest.bank.repository.UserRepository;
import com.rest.bank.service.AccountService;
import com.rest.bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private AccountService accountService;

    @PostMapping("/users")
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO.getDocumentId(),userDTO.getName());
    }

    @PostMapping("/users/{userId}/accounts")
    public String createAccount(@PathVariable int userId) {
        User user = userService.findById(userId).get();
        if (user.getAccounts().size() >= 3) {
            return "No puedes tener mas de tres cuentas asosciadas";
        }
        else{
            return "Se creo correctamente la cuenta con número: "+accountService.createAccount(user).getAccountNum().toString();
        }


    }
}
