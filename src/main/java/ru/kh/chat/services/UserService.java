package ru.kh.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kh.chat.dto.usersDto.UserDto;
import ru.kh.chat.models.User;
import ru.kh.chat.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public long saveUser(UserDto userDto) {
        return userRepository.save(new User(userDto.getUsername())).getId();
    }

    public boolean isUserAlreadyExist(UserDto userDto) {
        return userRepository.existsUserByUsername(userDto.getUsername());
    }
}
