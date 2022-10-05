package ru.kh.chat.dto;

public class UserIdDto {

    private long id;

    public UserIdDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
