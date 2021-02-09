package com.dlimana.bookstoremanager.books.service;

import com.dlimana.bookstoremanager.author.service.AuthorService;
import com.dlimana.bookstoremanager.books.mapper.BookMapper;
import com.dlimana.bookstoremanager.books.repository.BookRepository;
import com.dlimana.bookstoremanager.publishers.service.PublisherService;
import com.dlimana.bookstoremanager.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    private UserService userService;

    private AuthorService authorService;

    private PublisherService publisherService;

}
