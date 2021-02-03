package com.dlimana.bookstoremanager.users.service;

import com.dlimana.bookstoremanager.users.builder.UserDTOBuilder;
import com.dlimana.bookstoremanager.users.dto.MessageDTO;
import com.dlimana.bookstoremanager.users.dto.UserDTO;
import com.dlimana.bookstoremanager.users.entity.User;
import com.dlimana.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.dlimana.bookstoremanager.users.exception.UserNotFoundException;
import com.dlimana.bookstoremanager.users.mapper.UserMapper;
import com.dlimana.bookstoremanager.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenNewUserIsInformedThenItShouldBeCreated() {
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        String expectedCreationMessage = "User danielteste with ID 1 successfully created";
        String expectedUserEmail = expectedCreatedUserDTO.getEmail();
        String expectedUsername = expectedCreatedUserDTO.getUsername();

        when(userRepository.findByEmailOrUsername(expectedUserEmail, expectedUsername)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(expectedCreatedUser.getPassword())).thenReturn(expectedCreatedUser.getPassword());
        when(userRepository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);

        MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

        assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
    }

    @Test
    void whenExistingUserIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedDuplicatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDuplicatedUser = userMapper.toModel(expectedDuplicatedUserDTO);
        String expectedUserEmail = expectedDuplicatedUserDTO.getEmail();
        String expectedUsername = expectedDuplicatedUserDTO.getUsername();

        when(userRepository.findByEmailOrUsername(expectedUserEmail, expectedUsername)).thenReturn(Optional.of(expectedDuplicatedUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(expectedDuplicatedUserDTO));
    }

    @Test
    void whenValidUserIsInformedThenItShouldBeDeleted() {
        UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDeletedUser = userMapper.toModel(expectedDeletedUserDTO);
        var expectedDeletedUserId = expectedDeletedUserDTO.getId();

        when(userRepository.findById(expectedDeletedUserId)).thenReturn(Optional.of(expectedDeletedUser));
        doNothing().when(userRepository).deleteById(expectedDeletedUserId);

        userService.delete(expectedDeletedUserId);

        verify(userRepository, times(1)).deleteById(expectedDeletedUserId);
    }

    @Test
    void whenInvalidUserIdIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
        var expectedDeletedUserId = expectedDeletedUserDTO.getId();

        when(userRepository.findById(expectedDeletedUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(expectedDeletedUserId));
    }

    @Test
    void whenExistingUserIsInformedThenItShouldBeUpdated() {
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUpdatedUserDTO.setUsername("danielupdate");
        User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);
        String expectedUpdatedMessage = "User danielupdate with ID 1 successfully updated";

        when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.of(expectedUpdatedUser));
        when(passwordEncoder.encode(expectedUpdatedUser.getPassword())).thenReturn(expectedUpdatedUser.getPassword());
        when(userRepository.save(expectedUpdatedUser)).thenReturn(expectedUpdatedUser);

        MessageDTO successUpdatedMessage = userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO);

        assertThat(successUpdatedMessage.getMessage(), is(equalToObject(expectedUpdatedMessage)));
    }

    @Test
    void whenNotExistingUserIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUpdatedUserDTO.setUsername("danielupdate");

        when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO));
    }
}
