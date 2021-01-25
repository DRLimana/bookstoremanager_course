package com.dlimana.bookstoremanager.author.controller;

import com.dlimana.bookstoremanager.author.dto.AuthorDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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

    @ApiOperation(value = "List all registered authors")
    //UTILIZAÇÃO DO SWAGGER
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all registered authors")
    })
    List<AuthorDTO> findAll();

    @ApiOperation(value = "Delete author by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Sucess author deleted"),
            @ApiResponse(code = 404, message = "Author not found error code")
    })
    void delete( Long id);

}
