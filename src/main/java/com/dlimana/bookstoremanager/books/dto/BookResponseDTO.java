package com.dlimana.bookstoremanager.books.dto;

import com.dlimana.bookstoremanager.author.dto.AuthorDTO;
import com.dlimana.bookstoremanager.publishers.dto.PublisherDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

    private Long id;

    private String name;

    private String isbn;

    private Long pages;

    private Long chapters;

    private AuthorDTO author;

    private PublisherDTO publisher;
}
