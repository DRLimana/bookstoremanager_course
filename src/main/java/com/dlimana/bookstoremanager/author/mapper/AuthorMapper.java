package com.dlimana.bookstoremanager.author.mapper;

import com.dlimana.bookstoremanager.author.dto.AuthorDTO;
import com.dlimana.bookstoremanager.author.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);

}
