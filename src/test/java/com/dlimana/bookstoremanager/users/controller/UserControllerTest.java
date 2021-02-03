package com.dlimana.bookstoremanager.users.controller;

import com.dlimana.bookstoremanager.users.builder.UserDTOBuilder;
import com.dlimana.bookstoremanager.users.dto.MessageDTO;
import com.dlimana.bookstoremanager.users.dto.UserDTO;
import com.dlimana.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static com.dlimana.bookstoremanager.utils.JsonConversionUtils.asJSonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String USERS_API_URL_PATH = "/api/v1/users";
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreatedDTO = userDTOBuilder.buildUserDTO();
        String expectedCreationMessage = "User danielteste with ID 1 successfully created";
        MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(userService.create(expectedUserToCreatedDTO)).thenReturn(expectedCreationMessageDTO);

        mockMvc.perform(post(USERS_API_URL_PATH)
                .contentType(APPLICATION_JSON)
                .content(asJSonString(expectedUserToCreatedDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedCreationMessage)));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreatedDTO = userDTOBuilder.buildUserDTO();
        expectedUserToCreatedDTO.setUsername(null);

        mockMvc.perform(post(USERS_API_URL_PATH)
                .contentType(APPLICATION_JSON)
                .content(asJSonString(expectedUserToCreatedDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDELETEIsCalledThenNoContentShouldBeInformed() throws Exception {
        UserDTO expectedUserToDeleteDTO = userDTOBuilder.buildUserDTO();

        doNothing().when(userService).delete(expectedUserToDeleteDTO.getId());

        mockMvc.perform(delete(USERS_API_URL_PATH + "/" + expectedUserToDeleteDTO.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenPUTIsCalledThenOKStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToUpdatedDTO = userDTOBuilder.buildUserDTO();
        expectedUserToUpdatedDTO.setUsername("danielupdated");
        String expectedUpdatedMessage = "User danielupdated with ID 1 successfully updated";
        MessageDTO expectedUpdatedMessageDTO = MessageDTO.builder().message(expectedUpdatedMessage).build();

        var expectedUserToUpdatedId = expectedUserToUpdatedDTO.getId();
        when(userService.update(expectedUserToUpdatedId, expectedUserToUpdatedDTO)).thenReturn(expectedUpdatedMessageDTO);

        mockMvc.perform(put(USERS_API_URL_PATH + "/" + expectedUserToUpdatedId)
                .contentType(APPLICATION_JSON)
                .content(asJSonString(expectedUserToUpdatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedUpdatedMessage)));
    }
}
