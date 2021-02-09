package com.dlimana.bookstoremanager.books.mapper;

import com.dlimana.bookstoremanager.books.dto.BookRequestDTO;
import com.dlimana.bookstoremanager.books.dto.BookResponseDTO;
import com.dlimana.bookstoremanager.books.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookRequestDTO bookRequestDTO);

    Book toModel(BookResponseDTO bookResponseDTO);

    BookResponseDTO toDTO(Book book);
}
