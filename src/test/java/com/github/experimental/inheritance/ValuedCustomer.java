package com.github.experimental.inheritance;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue(value = "valuedCustomer")
@PrimaryKeyJoinColumn
public class ValuedCustomer extends Customer {

    @Column
    private String valuedCustomerName;

    public String getValuedCustomerName() {
        return valuedCustomerName;
    }

    public void setValuedCustomerName(String valuedCustomerName) {
        this.valuedCustomerName = valuedCustomerName;
    }

    @Override
    public String toString() {
        return "pid = " + getPid() + " customerName = " + getCustomerName()
                + " valuedCustomerName = " + valuedCustomerName;
    }

}
