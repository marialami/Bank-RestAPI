package com.rest.bank.repository;

import com.rest.bank.model.Transaction;
import com.rest.bank.service.TransactionBD;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionDao implements TransactionBD {

    private TransactionRepository transactionRepository;
    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
