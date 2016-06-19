package com.github.experimental.inheritance;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fluent.hibernate.H;
import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.HibernateProperties;
import com.github.fluent.hibernate.internal.util.InternalUtils.CollectionUtils;

/**
 *
 * @author V.Ladynev
 */
public class InheritanceTest {

    @BeforeClass
    public static void init() {
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(
                        HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true))
                .useNamingStrategy().scanPackages("com.github.experimental.inheritance").build();
        createTestData();
    }

    private static void createTestData() {
        List<Customer> customers = CollectionUtils.newArrayList();

        Customer customer = new Customer();
        customer.setCustomerName("parent name");
        customers.add(customer);

        ValuedCustomer valuedCustomer = new ValuedCustomer();
        valuedCustomer.setCustomerName("parent name");
        valuedCustomer.setValuedCustomerName("valued name");

        customers.add(valuedCustomer);
        H.saveAll(customers);
    }

    @AfterClass
    public static void closeSessionFactory() {
        Fluent.factory().close();
    }

    @Test
    public void loadCustomers() {
        System.out.println(H.request(Customer.class).list());
    }

}
