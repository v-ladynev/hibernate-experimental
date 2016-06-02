package com.github.experimental.art;

import java.util.List;

import org.junit.Test;

import com.github.fluent.hibernate.H;
import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.HibernateProperties;

public class ArtTest {

    @Test
    public void subTest() {
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(
                        HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true))
                .useNamingStrategy().scanPackages("com.github.experimental.art").build();

        Familia familia = H.save(new Familia("1f"));

        H.save(new Articulo("1a", familia));
        H.save(new Articulo("2a", familia));
        // H.save(new Articulo("1a", H.save(new Familia("2f"))));
        H.save(new Articulo("1a", familia));

        /*
        List<Person> list = H
                .<Person> request(
                        "select p from Person p")
                .list();
        System.out.println(list);
        */

        List<Articulo> list = H.<Articulo> request(Articulo.class).list();
        System.out.println(list);

        Fluent.factory().close();
    }

}
