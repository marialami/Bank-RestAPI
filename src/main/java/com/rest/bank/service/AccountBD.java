package com.rest.bank.service;

import com.rest.bank.model.Account;

public interface AccountBD {

    Account save(Account account);

    Account selectAccount(int id);

    void updateAccount(int money, int id);

    int getBalance(int id);




}
