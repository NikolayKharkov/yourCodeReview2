package ru.kh.chat.repositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    private User defaultUser = new User("Tom");

    private String defaultChatName = "Test chat";

    private Chat defaultChat = new Chat(defaultChatName);

    @Test
    void testWhenGetChatByNameExist() {
        chatRepository.save(defaultChat);
        Optional<Chat> expectedChat = chatRepository.getChatByName(defaultChatName);
        assertTrue(expectedChat.get().getName().equals(defaultChatName));
    }

    @Test
    void testWhenGetChatByNameAndNotExist() {
        Optional<Chat> expectedChat = chatRepository.getChatByName(defaultChatName);
        assertTrue(expectedChat.isEmpty());
    }

    @Test
    void testWhenGetChatsByUser() {
        defaultUser = userRepository.save(defaultUser);
        Chat chat1 = new Chat("chat1");
        Chat chat2 = new Chat("chat2");
        chat1.setUsers(Set.of(defaultUser));
        chat2.setUsers(Set.of(defaultUser));
        chat1 = chatRepository.save(chat1);
        chat2 = chatRepository.save(chat2);
        List<Chat> chats = chatRepository.getChatsByUsersOrderByMessagesCreatedAt(defaultUser);
        assertEquals(List.of(chat1, chat2), chats);
    }
}