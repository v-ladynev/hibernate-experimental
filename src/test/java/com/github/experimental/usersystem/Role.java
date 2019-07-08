package com.github.experimental.usersystem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bank role.
 *
 * @author V.Ladynev
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "f_pid")
    private Long pid;

    @Column(name = "f_role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public Long getPid() {
        return pid;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public static Role create(RoleEnum role) {
        Role result = new Role();
        result.setRole(role);
        return result;
    }

}
