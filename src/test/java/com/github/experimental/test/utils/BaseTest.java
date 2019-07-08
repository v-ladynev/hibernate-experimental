package com.github.experimental.test.utils;

import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.HibernateProperties;
import org.junit.AfterClass;

public class BaseTest {

    public static void init(String packageName) {
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(
                        HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true))
                /*.useNamingStrategy()*/.scanPackages(packageName)
                .build();
    }

    @AfterClass
    public static void closeSessionFactory() {
        Fluent.factory().close();
    }

}
