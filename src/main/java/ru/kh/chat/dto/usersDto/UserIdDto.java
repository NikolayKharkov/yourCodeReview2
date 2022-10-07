package ru.kh.chat.dto.usersDto;

import javax.validation.constraints.NotNull;

public class UserIdDto {

    @NotNull(message = "User id not specified")
    private Long userId;

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
