package ru.kh.chat.controllers;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kh.chat.dto.chatsDto.ChatIdDto;
import ru.kh.chat.dto.messagesDto.MessageDto;
import ru.kh.chat.dto.messagesDto.MessageIdDto;
import ru.kh.chat.models.Message;
import ru.kh.chat.services.MessageService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
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

    @PostMapping("/get")
    public List<Message> getChatMessages(@RequestBody @Valid ChatIdDto chatIdDto) {
        List<Message> result = messageService.getMessagesByChat(chatIdDto);
        return result;
    }

    @ExceptionHandler({EntityNotFoundException.class, ConstraintViolationException.class})
    public ResponseEntity<Map<String, String>> handleEntityNotFoundException() {
        return new ResponseEntity<>(Map.of("id", "Input chat or user id not exist."),
                HttpStatus.BAD_REQUEST);
    }
}
