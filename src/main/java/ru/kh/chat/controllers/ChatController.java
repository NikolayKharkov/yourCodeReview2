package ru.kh.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kh.chat.dto.chatsDto.ChatDto;
import ru.kh.chat.dto.chatsDto.ChatIdDto;
import ru.kh.chat.dto.usersDto.UserIdDto;
import ru.kh.chat.models.Chat;
import ru.kh.chat.services.ChatService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

   @PostMapping("/add")
   public ChatIdDto addChat(@RequestBody @Valid ChatDto chatDto) {
        long result = chatService.createOrAddNewUsersInChat(chatDto);
        return new ChatIdDto(result);
   }

    @PostMapping("/get")
    public List<Chat> getChatsByUser(@RequestBody @Valid UserIdDto userIdDto) {
        return chatService.getChatsByUser(userIdDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException() {
         return new ResponseEntity<>(Map.of("id", "List of users contains not exists Users id"),
                 HttpStatus.BAD_REQUEST);
    }
}
