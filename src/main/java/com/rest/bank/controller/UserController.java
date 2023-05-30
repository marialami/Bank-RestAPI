package com.rest.bank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.bank.controller.dto.LoginDTO;
import com.rest.bank.controller.dto.UserDTO;
import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.model.enums.AccountType;
import com.rest.bank.service.AccountService;
import com.rest.bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private AccountService accountService;

    @PostMapping("/users")
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO.getDocument(),userDTO.getName(), userDTO.getLastName(),userDTO.getPassword());
    }

    @GetMapping("/login")
    public User loginUser(@RequestParam int document, @RequestParam String password) {
        return userService.validateCredentials(document, password);
    }

    @PostMapping("/users/{userId}/{type}")
    public String createAccount(@PathVariable int userId, @PathVariable AccountType type) {
        User user = userService.findById(userId).get();
        if (user.getAccounts().size() >= 3) {
            return "No puedes tener mas de tres cuentas asosciadas";
        }
        else{
            return "Se creo correctamente la cuenta con número: "+accountService.createAccount(user,type).getId();
        }


    }

    @GetMapping("/accounts")
    public List<JsonNode> getAccountsForUser(@RequestParam int userId) throws JsonProcessingException {
        List<JsonNode> accounts = new ArrayList<>();
        User user = userService.findById(userId).get();
        ObjectMapper mapper = new ObjectMapper();

        for(Account a: user.getAccounts())
        {
            JsonNode json = mapper.createObjectNode()
                    .put("id", a.getId())
                    .put("user", user.getName())
                    .put("balance",a.getMoney());
            accounts.add(json);
        }
        return accounts;
    }
}
