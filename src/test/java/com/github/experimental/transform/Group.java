package com.github.experimental.transform;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Group {

    @Id
    @GeneratedValue
    private Long pid;

}
