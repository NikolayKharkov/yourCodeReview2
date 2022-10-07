package ru.kh.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kh.chat.dto.chatsDto.ChatDto;
import ru.kh.chat.dto.usersDto.UserIdDto;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.User;
import ru.kh.chat.repositories.ChatRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Transactional
    public long createOrAddNewUsersInChat(ChatDto chatDto) {
        Optional<Chat> chatOptional = chatRepository.getChatByName(chatDto.getName());
        if (chatOptional.isEmpty()) {
            return createChatWithUsers(chatDto);
        }
        Chat chat = chatOptional.get();
        chat.getUsers().addAll(listUsersIdsToUsers(chatDto.getUsers()));
        return chat.getId();
    }

    public List<Chat> getChatsByUser(UserIdDto userIdDto) {
        return chatRepository.getChatsByUsersOrderByMessagesCreatedAt(new User(userIdDto.getUserId()));
    }


    private long createChatWithUsers(ChatDto chatDto) {
        Chat chat = new Chat();
        chat.setName(chatDto.getName());
        chat.setUsers(listUsersIdsToUsers(chatDto.getUsers()));
        return chatRepository.save(chat).getId();
    }

    private Set<User> listUsersIdsToUsers(List<Long> usersId) {
        return usersId.stream()
                .map(userId -> new User(userId))
                .collect(Collectors.toSet());
    }
}
