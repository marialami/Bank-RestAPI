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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

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
        User user = User.builder()
                .document(document)
                .name(name)
                .lastName(lastName)
                .accounts(List.of())
                .dateCreated(new Date())
                .build();
        when(userDao.save(Mockito.any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        User createdUser = userService.createUser(document, name, lastName);
        assertNotNull(createdUser);
        assertEquals(createdUser.getDocument(), document);
        assertEquals(createdUser.getName(), name);
        assertEquals(createdUser.getLastName(), lastName);
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
}
