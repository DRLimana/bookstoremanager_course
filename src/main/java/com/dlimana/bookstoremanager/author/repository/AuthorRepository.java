package com.dlimana.bookstoremanager.author.repository;

import com.dlimana.bookstoremanager.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository irá receber a entidade autor e o id do autor no caso do tipo Long
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
