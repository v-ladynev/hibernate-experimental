package com.github.experimental.strategy.embedded;

import com.github.experimental.strategy.StrategyTestUtils;
import com.github.experimental.strategy.join.Author;
import com.github.experimental.strategy.join.Book;
import com.github.fluent.hibernate.H;
import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.HibernateProperties;
import com.github.fluent.hibernate.cfg.strategy.StrategyOptions;
import com.github.fluent.hibernate.cfg.strategy.hibernate5.Hibernate5NamingStrategy;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Table;
import org.hibernate.service.ServiceRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author V.Ladynev
 */
public class TwoEmbeddedStrategyTest {

    @Test
    public void logSql() {
        System.out.println("ImplicitNamingStrategyComponentPathImpl");
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true))
                .useNamingStrategy(ImplicitNamingStrategyComponentPathImpl.INSTANCE)
                .scanPackages("com.github.experimental.strategy.embedded").build();
        Fluent.factory().close();

        /*
        System.out.println("Hibernate5NamingStrategy");
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true))
                .useNamingStrategy(StrategyOptions.builder().withoutPrefixes().build())
                .scanPackages("com.github.experimental.strategy.embedded").build();
        Fluent.factory().close();
        */
    }

}
