package ru.kh.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kh.chat.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
