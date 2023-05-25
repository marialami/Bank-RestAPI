package com.rest.bank.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.bank.controller.dto.DepositDTO;
import com.rest.bank.controller.dto.TransferDTO;
import com.rest.bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void given_a_put_request_when_invoke_deposit_then_return_the_success_message() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.createObjectNode()
                .put("accountNumber", 1)
                .put("depositAmount", 100);
        String jsonString = mapper.writeValueAsString(json);

        DepositDTO depositDTO = DepositDTO.builder().depositAmount(100).accountNumber(1).build();
        when(accountService.makeDeposit(depositDTO)).thenReturn("Deposit completed successfully");

        mockMvc.perform(put("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposit completed successfully"));
        verify(accountService,times(1)).makeDeposit(depositDTO);

    }
    @Test
    public void given_a_get_request_when_invoke_getBalance_then_return_the_account_balance() throws Exception {
        int accountNumber = 123456;
        int expectedBalance = 100;
        when(accountService.getBalance(accountNumber)).thenReturn(expectedBalance);

        mockMvc.perform(get("/myBalance/{accountNumber}", accountNumber))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedBalance)));
    }

    @Test
    public void given_a_put_request_when_invoke_transfer_and_it_is_possible_to_do_then_return_the_success_message() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.createObjectNode()
                .put("origin", 12345)
                .put("destination", 54321)
                .put("amount",100);
        String jsonString = mapper.writeValueAsString(json);
        String successMessage = "Transaction completed successfully";
        TransferDTO transferDTO = TransferDTO.builder().amount(100).destination(54321).origin(12345).build();
        when(accountService.transfer(transferDTO)).thenReturn("Transaction completed successfully");

        mockMvc.perform(put("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
        verify(accountService,times(1)).transfer(transferDTO);

    }

    @Test
    public void given_a_put_request_when_invoke_transfer_and_it_is_not_possible_to_do_then_return_the_success_message() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.createObjectNode()
                .put("origin", 12345)
                .put("destination", 54321)
                .put("amount",100);
        String jsonString = mapper.writeValueAsString(json);
        String successMessage = "Transaction completed successfully";
        TransferDTO transferDTO = TransferDTO.builder().amount(100).destination(54321).origin(12345).build();
        when(accountService.transfer(transferDTO)).thenReturn("Insufficient funds in the source account");

        mockMvc.perform(put("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string("Insufficient funds in the source account"));
        verify(accountService,times(1)).transfer(transferDTO);

    }
}
