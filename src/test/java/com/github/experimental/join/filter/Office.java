package com.github.experimental.join.filter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author V.Ladynev
 */
@Entity
public class Office {

    @Id
    @GeneratedValue
    private Integer pid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "BANK_ID")
    private Bank bank;

    public Office() {

    }

    public Office(Bank bank) {
        this.bank = bank;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "Office{" +
                "pid=" + pid +
                '}';
    }

}
