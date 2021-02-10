package com.dlimana.bookstoremanager.books.service;

import com.dlimana.bookstoremanager.author.entity.Author;
import com.dlimana.bookstoremanager.author.service.AuthorService;
import com.dlimana.bookstoremanager.books.dto.BookRequestDTO;
import com.dlimana.bookstoremanager.books.dto.BookResponseDTO;
import com.dlimana.bookstoremanager.books.entity.Book;
import com.dlimana.bookstoremanager.books.exception.BookAlreadyExistsException;
import com.dlimana.bookstoremanager.books.exception.BookNotFoundException;
import com.dlimana.bookstoremanager.books.mapper.BookMapper;
import com.dlimana.bookstoremanager.books.repository.BookRepository;
import com.dlimana.bookstoremanager.publishers.entity.Publisher;
import com.dlimana.bookstoremanager.publishers.service.PublisherService;
import com.dlimana.bookstoremanager.users.dto.AuthenticatedUser;
import com.dlimana.bookstoremanager.users.entity.User;
import com.dlimana.bookstoremanager.users.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    private UserService userService;

    private AuthorService authorService;

    private PublisherService publisherService;

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO){
        //verifica se o usuário existe no sistema
        User foundAuthenticationUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        verifyIfBookIsAlreadyRegistered(foundAuthenticationUser, bookRequestDTO);

        Author foundAuthor = authorService.veriftAndGetIfExists(bookRequestDTO.getAuthorId());
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        Book bookToSave = bookMapper.toModel(bookRequestDTO);
        bookToSave.setUser(foundAuthenticationUser);
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);
        Book saveBook = bookRepository.save(bookToSave);
        return bookMapper.toDTO(saveBook);
    }

    public BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId){
        User foundAuthenticationUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findByIdAndUser(bookId, foundAuthenticationUser)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser){
        User foundAuthenticationUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findAllByUser(foundAuthenticationUser)
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId){
        User foundAuthenticationUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        Book foundBookToDelete = verifyAndGetIfExists(bookId, foundAuthenticationUser);
        bookRepository.deleteByIdAndUser(foundBookToDelete.getId(), foundAuthenticationUser);
    }

    private Book verifyAndGetIfExists(Long bookId, User foundAuthenticationUser) {
        return bookRepository.findByIdAndUser(bookId, foundAuthenticationUser)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private void verifyIfBookIsAlreadyRegistered(User foundUser, BookRequestDTO bookRequestDTO) {
        bookRepository.findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser.getUsername());
                });
    }

}
