package com.rest.bank.repository;

import com.rest.bank.model.User;
import com.rest.bank.service.UserBD;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserDao implements UserBD {

    private UserRepository userRepository;
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
