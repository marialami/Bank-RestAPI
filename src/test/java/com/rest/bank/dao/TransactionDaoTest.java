package com.rest.bank.dao;

import com.rest.bank.model.Transaction;
import com.rest.bank.repository.TransactionRepository;
import com.rest.bank.repository.TransactionDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class TransactionDaoTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionDao transactionDao;

    @Test
    public void given_transaction_when_save_then_return_saved_transaction() {
        Transaction transaction = Transaction.builder()
                .origen(1)
                .destination(2)
                .amount(100)
                .build();

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction savedTransaction = transactionDao.save(transaction);
        assertEquals(transaction, savedTransaction);
        verify(transactionRepository, times(1)).save(transaction);
    }
}
