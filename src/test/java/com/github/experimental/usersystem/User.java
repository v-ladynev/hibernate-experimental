package com.github.experimental.usersystem;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * A user.
 *
 * @author V.Ladynev
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "f_pid")
    private Long pid;

    @Column(name = "f_user_name")
    private String userName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_role") })
    private List<Role> roles = new ArrayList<Role>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "system_assign", joinColumns = { @JoinColumn(name = "fk_user") },
            inverseJoinColumns = { @JoinColumn(name = "fk_system") })
    private List<SomeSystem> systems = new ArrayList<SomeSystem>();

    public Long getPid() {
        return pid;
    }

    public String getUserName() {
        return userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<SomeSystem> getSystems() {
        return systems;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setSystems(List<SomeSystem> systems) {
        this.systems = systems;
    }

    public static User create(String userName) {
        User result = new User();
        result.setUserName(userName);
        return result;
    }

}
