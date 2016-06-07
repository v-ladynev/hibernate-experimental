package com.github.experimental.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A system.
 *
 * @author V.Ladynev
 */
@Entity
@Table(name = "systems")
public class SomeSystem {

    @Id
    @GeneratedValue
    @Column(name = "f_pid")
    private Long pid;

    @Column(name = "f_name")
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

    public static SomeSystem create(String name) {
        SomeSystem result = new SomeSystem();
        result.setName(name);
        return result;
    }

}
