package com.github.experimental.strategy.join;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Author {

    @Id
    @GeneratedValue
    private Integer pid;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Book> coauthorBooks;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> ownBooks;

    public Integer getPid() {
        return pid;
    }

    public List<Book> getCoauthorBooks() {
        return coauthorBooks;
    }

    public List<Book> getOwnBooks() {
        return ownBooks;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setCoauthorBooks(List<Book> coauthorBooks) {
        this.coauthorBooks = coauthorBooks;
    }

    public void setOwnBooks(List<Book> ownBooks) {
        this.ownBooks = ownBooks;
    }

}
