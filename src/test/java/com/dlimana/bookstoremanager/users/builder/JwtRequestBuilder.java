package com.dlimana.bookstoremanager.users.builder;

import com.dlimana.bookstoremanager.users.dto.JwtRequest;
import lombok.Builder;

@Builder
public class JwtRequestBuilder {

    @Builder.Default
    private String username = "daniel";

    @Builder.Default
    private String password = "123456";

    public JwtRequest buildJwtRequest(){
        return new JwtRequest(username, password);
    }
}
