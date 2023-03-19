package com.rest.bank.controller;

import com.rest.bank.controller.dto.AccountDTO;
import com.rest.bank.controller.dto.UserDTO;
import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping(path="/createUser", consumes="application/json")
    public User createUser(@RequestBody UserDTO userDTO) {

        Account account = Account.builder()
                .balance(new BigDecimal(0))
                .accountNum(System.nanoTime())
                .creationDate(new Date())
                .build();
        return userService.createUser(userDTO.getDocumentId(),userDTO.getName(),account);
    }
}
