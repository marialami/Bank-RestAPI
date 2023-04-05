package com.rest.bank.controller;

import com.rest.bank.controller.dto.AccountDTO;
import com.rest.bank.controller.dto.TransferDTO;
import com.rest.bank.model.Account;
import com.rest.bank.service.AccountService;
import com.rest.bank.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountControllerTest {
    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void depositFunds_shouldCallAccountServiceMakeDeposit() {
        AccountDTO accountDTO = new AccountDTO();

        String result = accountController.depositFunds(accountDTO);

        verify(accountService).makeDeposit(123456, 100);
        assertEquals("Deposit completed successfully", result);
    }

    @Test
    public void getBalance_shouldReturnAccountServiceGetBalance() {
        int accountNumber = 123456;
        when(accountService.getBalance(accountNumber)).thenReturn(100);

        int result = accountController.getBalance(accountNumber);

        verify(accountService).getBalance(accountNumber);
        assertEquals(100, result);
    }

    @Test
    public void transfer_shouldCallAccountServiceTransferAndTransactionServiceCreateTransaction() throws Exception {
        TransferDTO transferDTO = null;
        transferDTO.setOrigin(123456);
        transferDTO.setDestination(789012);
        transferDTO.setAmount(100);
        Future<String> futureTransfer = CompletableFuture.completedFuture("Transaction completed successfully");
        when(accountService.transfer(transferDTO.getOrigin(), transferDTO.getDestination(), transferDTO.getAmount()))
                .thenReturn(futureTransfer);

        String result = accountController.transfer(transferDTO);

        verify(accountService).transfer(transferDTO.getOrigin(), transferDTO.getDestination(), transferDTO.getAmount());
        verify(transactionService).createTransaction(transferDTO.getOrigin(), transferDTO.getDestination(), transferDTO.getAmount());
        assertEquals("Transaction completed successfully", result);
    }
}
