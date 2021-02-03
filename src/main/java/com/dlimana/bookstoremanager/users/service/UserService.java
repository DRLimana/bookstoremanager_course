package com.dlimana.bookstoremanager.users.service;

import com.dlimana.bookstoremanager.users.dto.MessageDTO;
import com.dlimana.bookstoremanager.users.dto.UserDTO;
import com.dlimana.bookstoremanager.users.entity.User;
import com.dlimana.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.dlimana.bookstoremanager.users.exception.UserNotFoundException;
import com.dlimana.bookstoremanager.users.mapper.UserMapper;
import com.dlimana.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.dlimana.bookstoremanager.users.utils.MessageDTOUtils.creationMessage;
import static com.dlimana.bookstoremanager.users.utils.MessageDTOUtils.updateMessage;

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

    public MessageDTO update(Long id, UserDTO userToUpdateDTO){
        User foundUser = verifyAndGetIfExists(id);

        userToUpdateDTO.setId(foundUser.getId());
        User userToUpdate = userMapper.toModel(userToUpdateDTO);
        userToUpdate.setCreatedDate(foundUser.getCreatedDate());

        User updateUser = userRepository.save(userToUpdate);
        return updateMessage(updateUser);
    }

    public void delete(Long id){
        verifyAndGetIfExists(id);
        userRepository.deleteById(id);
    }

    private User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void verifyIfExsits(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);
        if(foundUser.isPresent()){
            throw new UserAlreadyExistsException(email, username);
        }
    }
}
