package com.dlimana.bookstoremanager.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// definição do caminho url
@RequestMapping("/api/v1/books")
public class BookController {

    @GetMapping
    public String hello(){
        return "Hello Bookstore Manager, I am running an example with PR!!s";
    }
}
