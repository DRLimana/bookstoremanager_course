package com.dlimana.bookstoremanager.books.controller;

import com.dlimana.bookstoremanager.books.builder.BookRequestDTOBuilder;
import com.dlimana.bookstoremanager.books.builder.BookResponseDTOBuilder;
import com.dlimana.bookstoremanager.books.dto.BookRequestDTO;
import com.dlimana.bookstoremanager.books.dto.BookResponseDTO;
import com.dlimana.bookstoremanager.books.service.BookService;
import com.dlimana.bookstoremanager.users.dto.AuthenticatedUser;
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

import java.util.Collections;

import static com.dlimana.bookstoremanager.utils.JsonConversionUtils.asJSonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    private static final String BOOKS_API_URL_PATH = "/api/v1/books";

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .addFilters()
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTCalledThenCreatedStatusShouldBeReturned() throws Exception {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();

        when(bookService.create(any(AuthenticatedUser.class), eq(expectedBookToCreateDTO))).thenReturn(expectedCreatedBookDTO);

        mockMvc.perform(post(BOOKS_API_URL_PATH)
                .contentType(APPLICATION_JSON)
                .content(asJSonString(expectedBookToCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCreatedBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedBookDTO.getName())))
                .andExpect(jsonPath("$.isbn", is(expectedCreatedBookDTO.getIsbn())));

    }

    @Test
    void whenPOSTCalledWithoutRequiredFieldThenBadRequestShouldBeReturned() throws Exception {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        expectedBookToCreateDTO.setIsbn(null);

        mockMvc.perform(post(BOOKS_API_URL_PATH)
                .contentType(APPLICATION_JSON)
                .content(asJSonString(expectedBookToCreateDTO)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenGETIsCalledWithValidBookIdThenStatusOKShouldBeInformed() throws Exception {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();

        when(bookService.findByIdAndUser(any(AuthenticatedUser.class), eq(expectedBookToFindDTO.getId()))).thenReturn(expectedFoundBookDTO);

        mockMvc.perform(get(BOOKS_API_URL_PATH + "/" + expectedBookToFindDTO.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedFoundBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedFoundBookDTO.getName())))
                .andExpect(jsonPath("$.isbn", is(expectedFoundBookDTO.getIsbn())));
    }

    @Test
    void whenGETListIsCalledThenStatusOKShouldBeInformed() throws Exception {
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();

        when(bookService.findAllByUser(any(AuthenticatedUser.class))).thenReturn(Collections.singletonList(expectedFoundBookDTO));

        mockMvc.perform(get(BOOKS_API_URL_PATH)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedFoundBookDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedFoundBookDTO.getName())))
                .andExpect(jsonPath("$[0].isbn", is(expectedFoundBookDTO.getIsbn())));
    }

    @Test
    void whenDELETEIsCalledWithValidBookIdThenNoContentOKShouldBeInformed() throws Exception {
        BookRequestDTO expectedBookToDeleteDTO = bookRequestDTOBuilder.buildRequestBookDTO();

        doNothing().when(bookService).deleteByIdAndUser(any(AuthenticatedUser.class), eq(expectedBookToDeleteDTO.getId()));

        mockMvc.perform(delete(BOOKS_API_URL_PATH + "/" + expectedBookToDeleteDTO.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenPUTIsCalledThenOkStatusShouldBeReturned() throws Exception {
        BookRequestDTO expectedBookToUpdatedDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedUpdatedBookDTO = bookResponseDTOBuilder.buildBookResponse();

        when(bookService.updateByIdAndUser(
                any(AuthenticatedUser.class),
                eq(expectedBookToUpdatedDTO.getId()),
                eq(expectedBookToUpdatedDTO))).thenReturn(expectedUpdatedBookDTO);

        mockMvc.perform(put(BOOKS_API_URL_PATH + "/" + expectedBookToUpdatedDTO.getId())
                .contentType(APPLICATION_JSON)
                .content(asJSonString(expectedBookToUpdatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedUpdatedBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedUpdatedBookDTO.getName())))
                .andExpect(jsonPath("$.isbn", is(expectedUpdatedBookDTO.getIsbn())));

    }

}
