package com.rest.bank.dao;

import com.rest.bank.model.User;
import com.rest.bank.repository.UserRepository;
import com.rest.bank.repository.UserDao;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserDaoTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDao userDao;

    @Test
    public void given_user_when_save_then_return_saved_user() {
        User user = User.builder()
                .document(123456789)
                .name("John")
                .lastName("Doe")
                .build();

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userDao.save(user);

        assertEquals(user, savedUser);
    }

    @Test
    public void given_user_document_when_findById_then_return_optional_of_user() {
        User user = User.builder()
                .document(123456789)
                .name("John")
                .lastName("Doe")
                .build();

        when(userRepository.findById(user.getDocument())).thenReturn(Optional.of(user));

        Optional<User> optionalUser = userDao.findById(user.getDocument());

        assertEquals(user, optionalUser.orElse(null));
    }
}
