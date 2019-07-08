package com.github.experimental.persistent.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Bank department.
 *
 * @author V.Ladynev
 */
@Entity
public class Department {

    @Id
    @GeneratedValue
    private Long pid;

    @Column(name = "name")
    private String name;

    public Long getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Department create(String name) {
        Department result = new Department();
        result.setName(name);
        return result;
    }

}
