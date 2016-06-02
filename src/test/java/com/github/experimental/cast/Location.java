package com.github.experimental.cast;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Location {

    @Id
    @GeneratedValue
    private int lId;

    private String lName;

    public int getlId() {
        return lId;
    }

    public String getlName() {
        return lName;
    }

    public void setlId(int lId) {
        this.lId = lId;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

}