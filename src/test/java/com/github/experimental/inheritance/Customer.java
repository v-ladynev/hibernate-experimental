package com.github.experimental.inheritance;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "someDiscriminator")
public class Customer {

    @Id
    @GeneratedValue
    private Long customerPid;

    @Column
    private String customerName;

    public Long getCustomerPid() {
        return customerPid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerPid(Long customerPid) {
        this.customerPid = customerPid;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "pid = " + customerPid + " customerName = " + customerName;
    }

}
