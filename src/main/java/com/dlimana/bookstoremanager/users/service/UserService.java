package com.dlimana.bookstoremanager.users.service;

import com.dlimana.bookstoremanager.users.dto.MessageDTO;
import com.dlimana.bookstoremanager.users.dto.UserDTO;
import com.dlimana.bookstoremanager.users.entity.User;
import com.dlimana.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.dlimana.bookstoremanager.users.mapper.UserMapper;
import com.dlimana.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final static UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO){
        verifyIfExsits(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);
        return creationMessage(createdUser);
    }

    private void verifyIfExsits(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);
        if(foundUser.isPresent()){
            throw new UserAlreadyExistsException(email, username);
        }
    }

    private MessageDTO creationMessage(User createdUser) {
        String createdUserName = createdUser.getUsername();
        Long createdId = createdUser.getId();
        String createdUserMessage = String.format("User %s with ID %s successfully created", createdUserName, createdId);
        return MessageDTO.builder()
                .message(createdUserMessage)
                .build();
    }
}
