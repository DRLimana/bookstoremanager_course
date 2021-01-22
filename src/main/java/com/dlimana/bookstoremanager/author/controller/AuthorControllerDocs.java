package com.dlimana.bookstoremanager.author.controller;

import com.dlimana.bookstoremanager.author.dto.AuthorDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Authors management")
public interface AuthorControllerDocs {

    @ApiOperation(value = "Author creation operation")
    //UTILIZAÇÃO DO SWAGGER
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sucess author creation"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or author already registered on system")
    })
    AuthorDTO create(AuthorDTO authorDTO);

    @ApiOperation(value = "Find author by id operation")
    //UTILIZAÇÃO DO SWAGGER
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucess author found"),
            @ApiResponse(code = 404, message = "Author not found error code")
    })
    AuthorDTO findById(Long id);
}
