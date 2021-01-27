package com.dlimana.bookstoremanager.publishers.service;

import com.dlimana.bookstoremanager.publishers.dto.PublisherDTO;
import com.dlimana.bookstoremanager.publishers.entity.Publisher;
import com.dlimana.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.dlimana.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.dlimana.bookstoremanager.publishers.mapper.PublisherMapper;
import com.dlimana.bookstoremanager.publishers.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO){
        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());
        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);

        return publisherMapper.toDTO(createdPublisher);
    }

    public PublisherDTO findById(Long id){
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository
                .findByNameOrCode(name, code);
        if(duplicatedPublisher.isPresent()){
            throw new PublisherAlreadyExistsException(name, code);
        }
    }
}
