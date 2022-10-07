package ru.kh.chat.services;

import com.sun.xml.bind.marshaller.Messages;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import ru.kh.chat.dto.chatsDto.ChatIdDto;
import ru.kh.chat.dto.messagesDto.MessageDto;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.Message;
import ru.kh.chat.models.User;
import ru.kh.chat.repositories.ChatRepository;
import ru.kh.chat.repositories.MessageRepository;
import ru.kh.chat.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private ChatRepository chatRepository;

    private User defaultUser = new User(1L, "Tom");

    private String defaultChatName = "Test chat";

    private String messageText = "Message Text";

    private Chat defaultChat = new Chat(1L, defaultChatName, Set.of(defaultUser));

    private Message defaultMessage = new Message(1L, messageText , defaultUser, defaultChat);

    @Test
    void testSaveMessages() {
        defaultChat.setMessages(new ArrayList<>());
        when(chatRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(defaultChat));
        when(messageRepository.save(Mockito.any(Message.class)))
                .thenReturn(defaultMessage);
        long result = messageService.saveMessage(new MessageDto(defaultChat.getId(), defaultUser.getId(), messageText));
        assertEquals(1L, result);
    }

    @Test
    void testSaveMessagesToNotExistChat() {
        assertThrows(ResponseStatusException.class, () -> {
            when(chatRepository.findById(Mockito.any(Long.class)))
                    .thenReturn(Optional.empty());
            when(messageRepository.save(Mockito.any(Message.class)))
                    .thenReturn(defaultMessage);
            messageService.saveMessage(new MessageDto(defaultChat.getId(), defaultUser.getId(), messageText));
        });
    }

    @Test
    void testGetMessagesFromChat() {
        List<Message> expected = List.of(
                new Message(1L, messageText , defaultUser, defaultChat),
                new Message(2L, messageText , defaultUser, defaultChat));
        when(messageRepository.findAllByChatOrderByCreatedAtAsc(Mockito.any(Chat.class)))
                .thenReturn(expected);
        List<Message> result = messageService.getMessagesByChat(new ChatIdDto(1L));
        assertEquals(expected, result);
    }

}