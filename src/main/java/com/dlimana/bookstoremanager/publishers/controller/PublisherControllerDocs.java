package com.dlimana.bookstoremanager.publishers.controller;

import com.dlimana.bookstoremanager.publishers.dto.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

@Api("Publishers management")
public interface PublisherControllerDocs {

    @ApiOperation(value = "Publisher creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Sucess publisher creation"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or publisher already registered on system")
    })
    PublisherDTO create(PublisherDTO publisherDTO);

    @ApiOperation(value = "Find publisher by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Sucess publisher found"),
            @ApiResponse(code = 404, message = "Publisher not found error")
    })
    PublisherDTO findById(Long id);
}
