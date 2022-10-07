package ru.kh.chat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kh.chat.dto.usersDto.UserDto;
import ru.kh.chat.dto.usersDto.UserIdDto;
import ru.kh.chat.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    ObjectMapper mapper = new ObjectMapper();

    UserDto userDto = new UserDto("Tom");

    @Test
    void testWhenAddNewUserSuccess() throws Exception {
       when(userService.isUserAlreadyExist(Mockito.any(UserDto.class))).thenReturn(false);
       when(userService.saveUser(Mockito.any(UserDto.class))).thenReturn(1L);
       RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(new UserIdDto(1L)), response.getContentAsString());
    }

    @Test
    void testWhenAddNewUserButUserNameAlreadyExist() throws Exception {
        when(userService.isUserAlreadyExist(Mockito.any(UserDto.class))).thenReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testWhenJsonUserNameIsNull() throws Exception {
        userDto.setUsername(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("[{\"username\":\"Username can't be empty.\"},{\"username\":\"Username can't be null.\"}]",
                response.getContentAsString());
    }

    @Test
    public void testWhenJsonUserNameIsEmpty() throws Exception {
        userDto.setUsername("");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("[{\"username\":\"Username can't be empty.\"}]",
                response.getContentAsString());
    }
}