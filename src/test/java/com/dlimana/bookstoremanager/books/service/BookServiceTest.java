package com.dlimana.bookstoremanager.books.service;

import com.dlimana.bookstoremanager.author.service.AuthorService;
import com.dlimana.bookstoremanager.books.builder.BookRequestDTOBuilder;
import com.dlimana.bookstoremanager.books.builder.BookResponseDTOBuilder;
import com.dlimana.bookstoremanager.books.mapper.BookMapper;
import com.dlimana.bookstoremanager.books.repository.BookRepository;
import com.dlimana.bookstoremanager.publishers.service.PublisherService;
import com.dlimana.bookstoremanager.users.dto.AuthenticatedUser;
import com.dlimana.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorService authorService;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private BookService bookService;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        authenticatedUser = new AuthenticatedUser("daniel", "123456", "ADMIN");
    }
}
