package com.rest.bank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.bank.model.Account;
import com.rest.bank.model.User;
import com.rest.bank.model.enums.AccountType;
import com.rest.bank.service.AccountService;
import com.rest.bank.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AccountService accountService;

    private static String jsonToString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void given_a_post_request_when_invoke_createUser_then_return_the_user_created() throws Exception {

        User user = User.builder()
                .document(123456789)
                .name("John")
                .lastName("Doe")
                .accounts(List.of())
                .dateCreated(new Date())
                .password("messi")
                .build();

        given(userService.createUser(anyInt(), anyString(), anyString(),anyString())).willReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.document", is(123456789)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.password", is("messi")))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    public void given_a_post_request_when_invoke_createAccount_then_return_the_success_message() throws Exception {
        User user = new User();
        user.setDocument(123456789);
        user.setName("John");
        user.setLastName("Doe");

        given(userService.findById(user.getDocument())).willReturn(Optional.of(user));

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        user.setAccounts(accounts);
        given(accountService.createAccount(user, AccountType.AHORROS)).willReturn(accounts.get(0));

        mockMvc.perform(post("/users/{userId}/{type}", user.getDocument(), AccountType.AHORROS))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Se creo correctamente la cuenta con número: " + accounts.get(0).getId())));
    }

    @Test
    public void given_a_post_request_when_invoke_createAccount_more_than_tree_times_with_the_same_account_then_return_the_error_message() throws Exception {
        User user = new User();
        user.setDocument(123456789);
        user.setName("John");
        user.setLastName("Doe");

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());
        accounts.add(new Account());

        user.setAccounts(accounts);

        given(userService.findById(user.getDocument())).willReturn(Optional.of(user));

        mockMvc.perform(post("/users/{userId}/{type}", user.getDocument(), AccountType.AHORROS))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No puedes tener mas de tres cuentas asosciadas")));
    }

    @Test
    public void given_a_get_request_when_invoke_getAccounts_then_return_the_accounts_of_the_user() throws Exception {
        User user = new User();
        user.setDocument(123456789);
        user.setName("John");
        user.setLastName("Doe");
        user.setPassword("Messi");

        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        accounts.add(new Account());
        accounts.add(new Account());

        user.setAccounts(accounts);

        given(userService.findById(user.getDocument())).willReturn(Optional.of(user));

        mockMvc.perform(get("/accounts")
                        .param("userId", user.getDocument()+""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].user", is("John")))
                .andExpect(jsonPath("$[1].user", is("John")))
                .andExpect(jsonPath("$[2].user", is("John")));
    }

    @Test
    public void given_a_get_request_when_invoke_login_then_return_the_user() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.createObjectNode()
                .put("document", 123456789)
                .put("password", "messi");
        String jsonString = mapper.writeValueAsString(json);

        User user = new User();
        user.setDocument(123456789);
        user.setName("John");
        user.setLastName("Doe");
        user.setPassword("messi");

        given(userService.validateCredentials(123456789,"messi")).willReturn(user);

        mockMvc.perform(get("/login")
                    .param("document", "123456789")
                    .param("password", "messi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.document", is(123456789)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.password", is("messi")))
                .andExpect(jsonPath("$.lastName", is("Doe")));

        verify(userService,times(1)).validateCredentials(123456789,"messi");

    }
}
