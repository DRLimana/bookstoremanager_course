package com.dlimana.bookstoremanager.books.repository;

import com.dlimana.bookstoremanager.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
