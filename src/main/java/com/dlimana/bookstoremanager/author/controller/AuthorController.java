package com.dlimana.bookstoremanager.author.controller;

import com.dlimana.bookstoremanager.author.dto.AuthorDTO;
import com.dlimana.bookstoremanager.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController implements AuthorControllerDocs{

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {

        this.authorService = authorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO create(@RequestBody @Valid AuthorDTO authorDTO) {

        return authorService.create(authorDTO);
    }

    @GetMapping("/{id}")
    /*
        PathVariable serve para passar uma variável na requisição da chamada.

        Por padrao é retornado o estado 200 quando uma chamada é feita com sucesso.
    */
    public AuthorDTO findById(@PathVariable Long id) {

        return authorService.findById(id);
    }

    @GetMapping
    public List<AuthorDTO> findAll() {
        return authorService.findAll();
    }

    @DeleteMapping("/{id}")
    // indicado para exlclusoes com sucesso
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }
}