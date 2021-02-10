package com.dlimana.bookstoremanager.books.exception;

import com.dlimana.bookstoremanager.users.entity.User;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.EntityExistsException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookAlreadyExistsException extends EntityExistsException {
    public BookAlreadyExistsException(String name, String isbn, String username) {
        super(String.format("Book with name %s, ISBN %s for user " +
                "%s already registered!", name, isbn, username));
    }
}
