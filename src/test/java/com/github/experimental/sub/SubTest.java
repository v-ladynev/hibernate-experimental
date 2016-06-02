package com.github.experimental.sub;

import java.util.List;

import org.junit.Test;

import com.github.fluent.hibernate.H;
import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.HibernateProperties;

public class SubTest {

    @Test
    public void subTest() {
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(
                        HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true))
                .useNamingStrategy().scanPackages("com.github.experimental.sub").build();

        Person p = H.save(new Person());

        H.save(new Car(p));
        H.save(new Profession(p));
        H.save(new Profession(p));

        /*
        List<Person> list = H
                .<Person> request(
                        "select p from Person p")
                .list();
        System.out.println(list);
        */

        List<Person> list = H.<Person> request(Person.class).list();
        System.out.println(list);

        Fluent.factory().close();
    }

}
