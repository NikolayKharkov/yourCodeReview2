package ru.kh.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.kh.chat.dto.messagesDto.MessageDto;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.Message;
import ru.kh.chat.models.User;
import ru.kh.chat.repositories.ChatRepository;
import ru.kh.chat.repositories.MessageRepository;

import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }

    @Transactional
    public long saveMessage(MessageDto messageDto) {
        Message message = new Message(messageDto.getText(),
                new User(messageDto.getAuthor()),
                new Chat(messageDto.getChatId()));
        Optional<Chat> chatOptional = chatRepository.findById(messageDto.getChatId());
        if (chatOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Test");
        }
        Chat chat = chatOptional.get();
        messageRepository.save(message);
        chat.getMessages().add(message);
        chatRepository.save(chat);
        return message.getId();
    }
}
