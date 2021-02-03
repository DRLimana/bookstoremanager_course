package com.dlimana.bookstoremanager.users.builder;

import com.dlimana.bookstoremanager.users.dto.UserDTO;
import com.dlimana.bookstoremanager.users.enums.Gender;
import com.dlimana.bookstoremanager.users.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Daniel Teste";

    @Builder.Default
    private Integer age = 25;

    @Builder.Default
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String email = "daniel@teste.com";

    @Builder.Default
    private String username = "danielteste";

    @Builder.Default
    private String password="123456";

    @Builder.Default
    private LocalDate birthDate= LocalDate.of(1995, 8,9);

    @Builder.Default
    private Role role = Role.USER;

    public UserDTO buildUserDTO(){
        return new UserDTO(id,
                name,
                age,
                gender,
                email,
                username,
                password,
                birthDate,
                role);
    }
}
