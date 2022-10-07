package ru.kh.chat.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Chat name can't be null")
    @NotBlank(message = "Chat name can't be empty")
    @Column(name = "chat_name")
    private String name;

    @OneToMany(mappedBy = "chat")
    @Fetch(FetchMode.SUBSELECT)
    private List<Message> messages;

    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "users_chats",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    public Chat() {
    }

    public Chat(String name) {
        this.name = name;
    }

    public Chat(long id) {
        this.id = id;
    }

    public Chat(long id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Chat(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUsers(Set<User> users) {
        this.users.addAll(users);
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
        Chat chat = (Chat) o;
        return Objects.equals(name, chat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
