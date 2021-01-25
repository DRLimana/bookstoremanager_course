package com.dlimana.bookstoremanager.publishers.builder;

import com.dlimana.bookstoremanager.publishers.dto.PublisherDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    private final String name = "Peleias Editora";

    private final String code = "PELE1234";

    private final LocalDate foundationDate = LocalDate.of(2020,6,1);

    public PublisherDTO buildPublisherDTO(){
        return new PublisherDTO(
                id,
                name,
                code,
                foundationDate);
    }
}
