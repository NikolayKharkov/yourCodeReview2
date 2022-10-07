package ru.kh.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kh.chat.models.Chat;
import ru.kh.chat.models.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message>  findAllByChatOrderByCreatedAtAsc(Chat chat);

}
