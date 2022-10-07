package ru.kh.chat.dto.chatsDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ChatDto {

    private String name;

    @NotNull(message = "List of users can't be null")
    private List<Long> users;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }
}
