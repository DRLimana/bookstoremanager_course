package com.dlimana.bookstoremanager.books.builder;

import com.dlimana.bookstoremanager.author.dto.AuthorDTO;
import com.dlimana.bookstoremanager.books.dto.BookRequestDTO;
import com.dlimana.bookstoremanager.publishers.dto.PublisherDTO;
import com.dlimana.bookstoremanager.users.builder.UserDTOBuilder;
import com.dlimana.bookstoremanager.users.dto.UserDTO;
import lombok.Builder;

@Builder
public class BookRequestDTOBuilder {

    @Builder.Default
    private final Long id= 1L;

    @Builder.Default
    private final String name = "Spring Boot Pro";

    @Builder.Default
    private final String isbn = "978-3-16-148410-0";

    @Builder.Default
    private final Long publisherId = 2L;

    @Builder.Default
    private final Long authorId = 3L;

    @Builder.Default
    private final Integer pages = 200;

    @Builder.Default
    private final Integer chapters = 10;

    private final UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookRequestDTO buildRequestBookDTO(){
        return new BookRequestDTO(id,
                name,
                isbn,
                pages,
                chapters,
                authorId,
                publisherId);
    }
}
