package com.github.experimental.inheritance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "xxxYY")
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
        return "customerPid = " + getCustomerPid() + " customerName = " + getCustomerName()
                + " valuedCustomerName = " + valuedCustomerName;
    }

}
