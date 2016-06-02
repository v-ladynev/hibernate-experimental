package com.github.experimental.cast;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class FuturePlan {

    @Id
    @GeneratedValue
    private int fpId;

    @ManyToOne(fetch = FetchType.EAGER)
    private Location location;

    public int getFpId() {
        return fpId;
    }

    public Location getLocation() {
        return location;
    }

    public void setFpId(int fpId) {
        this.fpId = fpId;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}