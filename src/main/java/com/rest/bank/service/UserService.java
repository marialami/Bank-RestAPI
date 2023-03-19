package com.rest.bank.service;

import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository rp;

    public User createUser(int id, String name, Account account){
        User user = User.builder()
                .id(id)
                .name(name)
                .build();
        account.setUser(user);
        user.setAccounts(List.of(account));
        return rp.save(user);
    }
}
