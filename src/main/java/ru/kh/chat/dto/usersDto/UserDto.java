package ru.kh.chat.dto.usersDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDto {

    @NotNull(message = "Username can't be null")
    @NotBlank(message = "Username can't be empty")
    private String username;

    public UserDto() {
    }

    public UserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
