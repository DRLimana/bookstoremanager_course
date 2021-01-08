package com.dlimana.bookstoremanager.author.builder;

import com.dlimana.bookstoremanager.author.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Teste";

    @Builder.Default
    private final Integer age = 25;

    public AuthorDTO buildAuthorDTO(){
        return new AuthorDTO(id,name, age);
    }

}
