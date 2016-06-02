package com.github.experimental.transform;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author V.Ladynev
 */
@Entity
@Table(name = "memberships")
public class Memberships implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "status")
    private Integer status;

}
