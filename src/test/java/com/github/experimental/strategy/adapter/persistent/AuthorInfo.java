package com.github.experimental.strategy.adapter.persistent;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class AuthorInfo {

    @Column
    private String authorInfo;

    @OneToOne
    private Book bestBook;

}
