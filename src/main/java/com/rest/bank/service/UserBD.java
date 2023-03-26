package com.rest.bank.service;

import com.rest.bank.model.User;

import java.util.Optional;

public interface UserBD {

    User save(User user);

    Optional<User> findById(int id);
}
