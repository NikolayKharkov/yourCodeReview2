package ru.kh.chat.dto.usersDto;

public class UserIdDto {

    private long userId;

    public UserIdDto() {
    }

    public UserIdDto(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
