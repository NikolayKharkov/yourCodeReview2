package ru.kh.chat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kh.chat.dto.chatsDto.ChatDto;
import ru.kh.chat.dto.chatsDto.ChatIdDto;
import ru.kh.chat.dto.usersDto.UserIdDto;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.User;
import ru.kh.chat.services.ChatService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ChatService chatService;

    ObjectMapper mapper = new ObjectMapper();

    private User defaultUser = new User(1L, "Tom");

    ChatDto defaultChatDto = new ChatDto("test chat", List.of(1L, 2L));

    UserIdDto defaultUsedIdDto = new UserIdDto(1L);

    @Test
    void testWhenAddNewCHat() throws Exception {
        when(chatService.createOrAddNewUsersInChat(Mockito.any(ChatDto.class))).thenReturn(1L);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/chats/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(defaultChatDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(new ChatIdDto(1L)), response.getContentAsString());
    }

    @Test
    public void testWhenAddNewChatAndJsonChatNameIsNull() throws Exception {
        defaultChatDto.setName(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/chats/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(defaultChatDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("[{\"name\":\"Chat name can't be null.\"}]", response.getContentAsString());
    }

    @Test
    public void testWhenAddNewChatAndJsonUsersListIsNull() throws Exception {
        defaultChatDto.setUsers(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/chats/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(defaultChatDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("[{\"users\":\"List of users can't be null.\"}]", response.getContentAsString());
    }

    @Test
    public void testWhenAddNewChatWithNotExistUser() throws Exception {
        when(chatService.createOrAddNewUsersInChat(Mockito.any(ChatDto.class)))
                .thenThrow(EntityNotFoundException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/chats/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(defaultChatDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"id\":\"List of users contains not exists Users id\"}", response.getContentAsString());
    }

    @Test
    public void testWhenGetUsersChats() throws Exception {
        Chat chat1 = new Chat("chat1");
        Chat chat2 = new Chat("chat2");
        chat1.setUsers(Set.of(defaultUser));
        chat2.setUsers(Set.of(defaultUser));
        List<Chat> expected = List.of(chat1, chat2);
        when(chatService.getChatsByUser(Mockito.any(UserIdDto.class))).thenReturn(expected);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/chats/get")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(defaultUsedIdDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(expected), response.getContentAsString());
    }
}