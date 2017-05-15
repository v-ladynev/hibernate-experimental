package com.github.experimental.strategy.embedded;

import com.github.fluent.hibernate.annotations.FluentName;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    private Long pid;

    @Embedded
    //@FluentName(prefix="first_auth")
    private AuthorInfo firstAuthor;

    @Embedded
    //@FluentName(prefix="second_auth")
    private AuthorInfo secondAuthor;

}