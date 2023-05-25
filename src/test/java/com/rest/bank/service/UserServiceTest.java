package com.rest.bank.service;

import com.rest.bank.model.User;
import com.rest.bank.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @Test
    public void given_the_parameters_of_user_when_invoked_createUser_then_should_return_the_created_user() {
        int document = 123456789;
        String name = "John";
        String lastName = "Doe";
        String password = "messi";
        User user = User.builder()
                .document(document)
                .name(name)
                .lastName(lastName)
                .accounts(List.of())
                .password(password)
                .dateCreated(new Date())
                .build();
        when(userDao.save(Mockito.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User createdUser = userService.createUser(document, name, lastName,password);
        assertNotNull(createdUser);
        assertEquals(createdUser.getDocument(), document);
        assertEquals(createdUser.getName(), name);
        assertEquals(createdUser.getLastName(), lastName);
        assertEquals(createdUser.getPassword(), password);
        assertEquals(createdUser.getAccounts(), List.of());
    }

    @Test
    public void given_user_id_when_invoked_finById_them_return_the_user_of_given_id() {

        int id = 1;
        User user = User.builder()
                .document(123456789)
                .name("John")
                .lastName("Doe")
                .accounts(List.of())
                .dateCreated(new Date())
                .build();
        when(userDao.findById(id)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findById(id);
        Mockito.verify(userDao, times(1)).findById(id);
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get(), user);
    }

    @Test
    public void given_the_correct_user_document_and_password_when_invoked_validateCredentials_them_return_true(){
        User user = User.builder()
                .document(123456789)
                .name("John")
                .lastName("Doe")
                .accounts(List.of())
                .password("messi")
                .dateCreated(new Date())
                .build();

        when(userDao.validateCredentials(123456789,"messi")).thenReturn(user);

        boolean result = userService.validateCredentials(user.getDocument(),user.getPassword());

        assertTrue(result);
        verify(userDao,times(1)).validateCredentials(user.getDocument(),user.getPassword());
    }

    @Test
    public void given_the_incorrect_user_document_and_password_when_invoked_validateCredentials_them_return_true(){
        User user = User.builder()
                .document(123456789)
                .name("John")
                .lastName("Doe")
                .accounts(List.of())
                .password("messi")
                .dateCreated(new Date())
                .build();

        when(userDao.validateCredentials(1234567895,"messi")).thenReturn(null);

        boolean result = userService.validateCredentials(1234567895,user.getPassword());

        assertFalse(result);
        verify(userDao,times(1)).validateCredentials(1234567895,user.getPassword());
    }
}
