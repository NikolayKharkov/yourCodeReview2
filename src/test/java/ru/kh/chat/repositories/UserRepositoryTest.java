package ru.kh.chat.repositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kh.chat.models.User;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User defaultUser = new User("Tom");

    @Test
    void testWhenSaveOne() {
        userRepository.save(defaultUser);
        assertTrue(defaultUser.equals(userRepository.findById(1L).get()));
    }

    @Test
    void testIsExistAndExpectTrue() {
        userRepository.save(defaultUser);
        assertTrue(userRepository.existsUserByUsername(defaultUser.getUsername()));
    }

    @Test
    void testIsExistAndExpectFalse() {
        assertFalse(userRepository.existsUserByUsername(defaultUser.getUsername()));
    }

}