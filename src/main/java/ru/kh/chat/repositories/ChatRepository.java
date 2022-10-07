package ru.kh.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> getChatByName (String chatName);

    List<Chat> getChatsByUsersOrderByMessagesCreatedAt(User user);
}
