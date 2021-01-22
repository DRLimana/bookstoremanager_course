package com.dlimana.bookstoremanager.author.service;


import com.dlimana.bookstoremanager.author.dto.AuthorDTO;
import com.dlimana.bookstoremanager.author.entity.Author;
import com.dlimana.bookstoremanager.author.exception.AuthorNotFoundException;
import com.dlimana.bookstoremanager.author.mapper.AuthorMapper;
import com.dlimana.bookstoremanager.author.repository.AuthorRepository;
import com.dlimana.bookstoremanager.author.service.exception.AuthorAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {

        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO){
        verifyIfExists(authorDTO.getName());

        Author authorToCreate = authorMapper.toModel(authorDTO);
        Author createdAuthor = authorRepository.save(authorToCreate);
        return authorMapper.toDTO(createdAuthor);
    }

    public AuthorDTO findById(Long id){
        Author foundAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        return authorMapper.toDTO(foundAuthor);
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> {throw new AuthorAlreadyExistsException(authorName);});
    }
}
