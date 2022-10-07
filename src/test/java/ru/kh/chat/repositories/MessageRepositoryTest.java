package ru.kh.chat.repositories;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.Message;
import ru.kh.chat.models.User;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    private User defaultUser = new User("Tom");

    private String defaultChatName = "Test chat";

    private Chat defaultChat = new Chat(defaultChatName);

    @Test
    void testGetMessagesByChat() {
        defaultUser = userRepository.save(defaultUser);
        defaultChat.setUsers(Set.of(defaultUser));
        defaultChat = chatRepository.save(defaultChat);
        Message message = new Message("test_text", defaultUser, defaultChat);
        message = messageRepository.save(message);
        defaultChat.setMessages(List.of(message));
        List<Message> expected = List.of(message);
        assertEquals(expected, messageRepository.findAllByChatOrderByCreatedAtAsc(defaultChat));
    }

}