package com.rest.bank.service;

import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository rp;

    public User createUser(int id, String name){
        User user = User.builder()
                .id(id)
                .name(name)
                .accounts(List.of())
                .build();
        return rp.save(user);
    }

    public Optional<User> findById(int id){
        return rp.findById(id);
    }
    public int getNumAccounts(int id){
        return findById(id).get().getAccounts().size();
    }

}
