package com.github.experimental.test.utils;

import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.FluentFactoryBuilder;
import com.github.fluent.hibernate.cfg.HibernateProperties;
import org.junit.AfterClass;

public class BaseTest {

    public static void init(String packageName) {
        defaultBuilder(packageName).useNamingStrategy().build();
    }

    public static FluentFactoryBuilder defaultBuilder(String packageName) {
        return Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(
                        HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true)
                ).scanPackages(packageName);
    }

    @AfterClass
    public static void closeSessionFactory() {
        Fluent.factory().close();
    }

}
