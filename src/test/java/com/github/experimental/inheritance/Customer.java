package com.github.experimental.inheritance;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue(value = "customer")
public class Customer {

    @Id
    @GeneratedValue
    private Long pid;

    @Column
    private String customerName;

    public Long getPid() {
        return pid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "pid = " + pid + " customerName = " + customerName;
    }

}
