package com.rest.bank.dao;


import com.rest.bank.model.Account;
import com.rest.bank.repository.AccountRepository;
import com.rest.bank.repository.AccountDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountDaoTest {
    @Mock
    private AccountRepository accountRepositoryMock;

    @InjectMocks
    private AccountDao accountDao;

    @Test
    public void given_account_when_save_then_return_saved_account() {
        Account account = Account.builder()
                .money(0)
                .type("Ahorros")
                .date_created(new Date())
                .build();
        when(accountRepositoryMock.save(account)).thenReturn(account);

        Account savedAccount = accountDao.save(account);
        verify(accountRepositoryMock).save(account);
        assertEquals(account, savedAccount);
    }

    @Test
    public void given_account_id_when_selectAccount_then_return_account() {
        Account account = Account.builder()
                .money(0)
                .type("Ahorros")
                .date_created(new Date())
                .build();
        when(accountRepositoryMock.selectAccount(account.getId())).thenReturn(account);

        Account selectedAccount = accountDao.selectAccount(account.getId());

        verify(accountRepositoryMock).selectAccount(account.getId());
        assertEquals(account, selectedAccount);
    }

    @Test
    public void given_account_id_and_money_when_updateAccount_then_call_repository() {
        int accountId = 1;
        int newMoney = 100;
        accountDao.updateAccount(newMoney, accountId);
        verify(accountRepositoryMock).updateAccount(newMoney, accountId);
    }

    @Test
    public void given_account_id_when_getBalance_then_return_balance() {
        int accountId = 1;
        int balance = 100;
        when(accountRepositoryMock.getBalance(accountId)).thenReturn(balance);

        int result = accountDao.getBalance(accountId);

        verify(accountRepositoryMock).getBalance(accountId);
        assertEquals(balance, result);
    }
}
