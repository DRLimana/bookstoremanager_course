package com.dlimana.bookstoremanager.author.entity;

import com.dlimana.bookstoremanager.books.entity.Book;
import com.dlimana.bookstoremanager.entity.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Author extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "integer default 0")
    private int age;

    /*
    FetchType.LAZY = reduza a quantidade de dados quando for fazer uma consulta, sem mostrar dados de tabelas relacionadas ao mesmo.
    FetchType.EAGER = mostra as informações da tabela no qual está o relacionamento, fazendo um JOIN.
    */
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;
}
