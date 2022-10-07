package ru.kh.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.kh.chat.dto.usersDto.UserDto;
import ru.kh.chat.dto.usersDto.UserIdDto;
import ru.kh.chat.services.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public UserIdDto addUser(@RequestBody @Valid UserDto userDto) {
        if (userService.isUserAlreadyExist(userDto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Account with name '" + userDto.getUsername() + "' already exist");
        }
        long generatedId = userService.saveUser(userDto);
        return new UserIdDto(generatedId);
    }
}
