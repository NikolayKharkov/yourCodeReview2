package ru.kh.chat.dto.chatsDto;

import javax.validation.constraints.NotNull;

public class ChatIdDto {

    @NotNull(message = "Chat id can't be null")
    private Long id;

    public ChatIdDto() {
    }

    public ChatIdDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
