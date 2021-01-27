package com.dlimana.bookstoremanager.publishers.service;

import com.dlimana.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.dlimana.bookstoremanager.publishers.dto.PublisherDTO;
import com.dlimana.bookstoremanager.publishers.entity.Publisher;
import com.dlimana.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.dlimana.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.dlimana.bookstoremanager.publishers.mapper.PublisherMapper;
import com.dlimana.bookstoremanager.publishers.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    private final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    }

    @Test
    void whenNewPublisherIsInformedThenItShouldBeCreated() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherCreated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherCreated.getCode()))
                .thenReturn(Optional.empty());
        when(publisherRepository.save(expectedPublisherCreated)).thenReturn(expectedPublisherCreated);

        PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);

        assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreateDTO)));
    }

    @Test
    void whenExistingPublisherIsInformedThenAnExceptionShouldBeThrown() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherDuplicated = publisherMapper.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
                .thenReturn(Optional.of(expectedPublisherDuplicated));

        assertThrows(PublisherAlreadyExistsException.class, () -> publisherService.create(expectedPublisherToCreateDTO));
    }

    @Test
    void whenValidIdIsGivenThenAPublisherShouldBeReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);
        var expectedPublisherFoundId = expectedPublisherFoundDTO.getId();

        when(publisherRepository.findById(expectedPublisherFoundId)).thenReturn(Optional.of(expectedPublisherFound));

        PublisherDTO foundPublisherDTO = publisherService.findById(expectedPublisherFoundId);

        assertThat(foundPublisherDTO, is(equalTo(foundPublisherDTO)));
    }

    @Test
    void whenInvalidIdIsGivenThenAExceptionShouldBeThrown() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        var expectedPublisherFoundId = expectedPublisherFoundDTO.getId();

        when(publisherRepository.findById(expectedPublisherFoundId)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> publisherService.findById(expectedPublisherFoundId));
    }
    //quando uma lista cheia é retornada
    @Test
    void whenListPublishersIsCalledThenItShouldBeReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = publisherMapper.toModel(expectedPublisherFoundDTO);

        when(publisherRepository.findAll()).thenReturn(Collections.singletonList(expectedPublisherFound));

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(1));
        assertThat(foundPublishersDTO.get(0), is(equalTo(expectedPublisherFoundDTO)));
    }
    //quando uma lista é retornada vazia
    @Test
    void whenListPublishersIsCalledThenAnEmptyListShouldBeReturned() {

        when(publisherRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(0));
    }

    @Test
    void whenValidPublisherIdIsGivenThenItShouldBeDeleted() {
        PublisherDTO expectedPublisherDeletedDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherDeleted = publisherMapper.toModel(expectedPublisherDeletedDTO);
        var expectedDeletedPublisherId = expectedPublisherDeletedDTO.getId();

        doNothing().when(publisherRepository).deleteById(expectedDeletedPublisherId);
        when(publisherRepository.findById(expectedDeletedPublisherId)).thenReturn(Optional.of(expectedPublisherDeleted));

        publisherService.delete(expectedDeletedPublisherId);

        verify(publisherRepository, times(1)).deleteById(expectedDeletedPublisherId);
    }

    @Test
    void whenInvalidPublisherIdIsGivenThenItShouldNotBeDeleted() {
        var expectedInvalidPublisherId = 2L;

        when(publisherRepository.findById(expectedInvalidPublisherId)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> publisherService.delete(expectedInvalidPublisherId));
    }
}
