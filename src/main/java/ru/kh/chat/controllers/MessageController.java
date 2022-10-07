package ru.kh.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kh.chat.dto.chatsDto.ChatIdDto;
import ru.kh.chat.dto.messagesDto.MessageDto;
import ru.kh.chat.dto.messagesDto.MessageIdDto;
import ru.kh.chat.services.MessageService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping("/add")
    public MessageIdDto addMessageToChat(@RequestBody @Valid MessageDto messageDto) {
        long result = messageService.saveMessage(messageDto);
        return new MessageIdDto(result);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException() {
        return new ResponseEntity<>(Map.of("id", "Input chat or user id not exist."),
                HttpStatus.BAD_REQUEST);
    }
}
