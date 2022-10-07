package ru.kh.chat.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "message_text")
    @NotBlank(message = "Message can't be empty")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Message() {

    }

    public Message(String text, User author, Chat chat) {
        this.text = text;
        this.author = author;
        this.chat = chat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(text, message.text)
                && Objects.equals(author, message.author)
                && Objects.equals(chat, message.chat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, author, chat);
    }
}
