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
import ru.kh.chat.dto.chatsDto.ChatIdDto;
import ru.kh.chat.dto.messagesDto.MessageDto;
import ru.kh.chat.dto.messagesDto.MessageIdDto;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.Message;
import ru.kh.chat.models.User;
import ru.kh.chat.repositories.ChatRepository;
import ru.kh.chat.services.MessageService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private ChatRepository chatRepository;

    ObjectMapper mapper = new ObjectMapper();

    private String defaultMessageText = "text";

    private String defaultChatName = "chat";

    private MessageDto defaultMessageDto = new MessageDto(1L, 1L, defaultMessageText);

    private ChatIdDto defaultChatIdDto = new ChatIdDto(1L);

    private User defaultUser = new User(1L, "Tom");

    private Chat defaultChat = new Chat(1L, defaultChatName, Set.of(defaultUser));

    @Test
    void testAddMessageToChatWhenChatExist() throws Exception {
        when(messageService.saveMessage(Mockito.any(MessageDto.class))).thenReturn(defaultMessageDto.getChatId());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/messages/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(defaultMessageDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(new MessageIdDto(defaultMessageDto.getChatId())),
                response.getContentAsString());
    }

    @Test
    public void testWhenAddNewMessageAndJsonTextNull() throws Exception {
        defaultMessageDto.setText(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/messages/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(defaultMessageDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("[{\"text\":\"Text can't be empty.\"}]", response.getContentAsString());
    }

    @Test
    public void testGetChatMessagesWhenChatExist() throws Exception {
        List<Message> expected = List.of(
                new Message(1L, defaultMessageText , defaultUser, defaultChat),
                new Message(2L, defaultMessageText , defaultUser, defaultChat));
        when(messageService.getMessagesByChat(Mockito.any(ChatIdDto.class))).thenReturn(expected);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/messages/get")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(defaultChatIdDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(expected), response.getContentAsString());
    }

    @Test
    public void testGetChatMessagesWhenChatNotExist() throws Exception {
        when(messageService.getMessagesByChat(Mockito.any(ChatIdDto.class))).thenReturn(Collections.emptyList());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/messages/get")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(defaultChatIdDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(Collections.emptyList()), response.getContentAsString());
    }

}