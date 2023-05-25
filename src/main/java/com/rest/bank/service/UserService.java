package com.rest.bank.service;

import com.rest.bank.model.User;
import com.rest.bank.repository.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserDao rp;

    public User createUser(int document, String name, String lastName, String password){
        User user = User.builder()
                .document(document)
                .name(name)
                .lastName(lastName)
                .accounts(List.of())
                .dateCreated(new Date())
                .password(password)
                .build();
        return rp.save(user);
    }

    public Optional<User> findById(int id){
        return rp.findById(id);
    }

    public User validateCredentials(int document, String password){

        if (rp.validateCredentials(document, password) != null)
            return rp.validateCredentials(document, password);
        else
            return User.builder().build();
    }

}
