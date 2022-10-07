package ru.kh.chat.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kh.chat.dto.chatsDto.ChatDto;
import ru.kh.chat.dto.usersDto.UserIdDto;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.User;
import ru.kh.chat.repositories.ChatRepository;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
class ChatServiceTest {

    @Autowired
    private ChatService chatService;

    @MockBean
    private ChatRepository chatRepository;

    private User defaultUser = new User(1L, "Tom");

    private String defaultChatName = "Test chat";

    private Chat defaultChat = new Chat(defaultChatName);

    private ChatDto chatDto = new ChatDto(defaultChatName, List.of(defaultUser.getId()));

    @Test
    void testWhenAddNewChat() {
        Chat chatFromDb = new Chat(1L, defaultChatName, Set.of(defaultUser));
        when(chatRepository.save(Mockito.any(Chat.class)))
                .thenReturn(chatFromDb);
        long result = chatService.createOrAddNewUsersInChat(chatDto);
        assertEquals(1L, result);
    }

    @Test
    void testGetChatsByUser() {
        Chat chat1 = new Chat("chat1");
        Chat chat2 = new Chat("chat2");
        chat1.setUsers(Set.of(defaultUser));
        chat2.setUsers(Set.of(defaultUser));
        List<Chat> expected = List.of(chat1, chat2);
        when(chatRepository.getChatsByUsersOrderByMessagesCreatedAt(Mockito.any(User.class)))
                .thenReturn(expected);
        List<Chat> results = chatService.getChatsByUser(new UserIdDto(1L));
        assertEquals(results, expected);
    }
}