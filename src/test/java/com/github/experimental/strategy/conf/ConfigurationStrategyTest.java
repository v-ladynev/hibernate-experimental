package com.github.experimental.strategy.conf;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Test;

import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.strategy.StrategyOptions;
import com.github.fluent.hibernate.cfg.strategy.hibernate5.Hibernate5NamingStrategy;

/**
 *
 * @author V.Ladynev
 */
public class ConfigurationStrategyTest {

    @After
    public void closeSessionFactory() {
        Fluent.factory().close();
    }

    @Test
    public void hibernate5Config() {
        /*
        
        // read hibernate.cfg.xml
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure()
                .build();
        
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).getMetadataBuilder()
                .applyImplicitNamingStrategy(new Hibernate5NamingStrategy()).build()
                .buildSessionFactory();
        Fluent.configureFromExistingSessionFactory(sessionFactory);
        */

        /*
        Configuration configuration = new Configuration();
        configuration.setImplicitNamingStrategy(new Hibernate5NamingStrategy());
        SessionFactory sessionFactory = configuration.configure().buildSessionFactory();
        */

        Configuration configuration = new Configuration();
        configuration.setImplicitNamingStrategy(
                new Hibernate5NamingStrategy(StrategyOptions.builder().tablePrefix("acps")
                        .restrictLength(50).restrictJoinTableNames(false).build()));
        SessionFactory sessionFactory = configuration.configure().buildSessionFactory();

    }

}
