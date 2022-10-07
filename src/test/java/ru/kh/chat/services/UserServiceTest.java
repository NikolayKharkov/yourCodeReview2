package ru.kh.chat.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kh.chat.dto.usersDto.UserDto;
import ru.kh.chat.models.User;
import ru.kh.chat.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserDto defaultUser = new UserDto("Tom");

    @Test
    void testWhenSaveUser() {
        when(userRepository.save(new User(defaultUser.getUsername())))
                .thenReturn(new User(1L, defaultUser.getUsername()));
        long result = userService.saveUser(defaultUser);
        assertEquals(1L, result);
    }

    @Test
    void testWhenUserIsExistExpectFalse() {
        when(userRepository.existsUserByUsername(Mockito.any(String.class)))
                .thenReturn(false);
        boolean result = userService.isUserAlreadyExist(defaultUser);
        assertFalse(result);
    }

    @Test
    void testWhenUserIsExistExpectTrue() {
        when(userRepository.existsUserByUsername(Mockito.any(String.class)))
                .thenReturn(true);
        boolean result = userService.isUserAlreadyExist(defaultUser);
        assertTrue(result);
    }
}