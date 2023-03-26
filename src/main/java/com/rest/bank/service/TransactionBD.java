package com.rest.bank.service;

import com.rest.bank.model.Transaction;

public interface TransactionBD {

    Transaction save(Transaction transaction);
}
