package com.github.experimental.aliases;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.experimental.persistent.user.Department;
import com.github.experimental.persistent.user.User;
import com.github.fluent.hibernate.H;
import com.github.fluent.hibernate.IRequest;
import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.HibernateProperties;
import com.github.fluent.hibernate.cfg.HibernateSessionFactory;
import com.github.fluent.hibernate.internal.util.InternalUtils.CollectionUtils;

public class AliasesTest {

    @BeforeClass
    public static void init() {
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(
                        HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true))
                .useNamingStrategy().scanPackages("com.github.experimental.persistent.user")
                .build();
        createTestData();
    }

    private static void createTestData() {
        List<User> users = CollectionUtils.newArrayList();
        users.add(User.create("login_a", H.save(Department.create("department_a"))));
        users.add(User.create("login_b", H.save(Department.create("department_b"))));
        H.saveAll(users);
    }

    @AfterClass
    public static void closeSessionFactory() {
        Fluent.factory().close();
    }

    @Test
    public void aliasesTest() {
        List<User> users = HibernateSessionFactory.doInTransaction(new IRequest<List<User>>() {
            @Override
            public List<User> doInTransaction(Session session) {
                Criteria criteria = session.createCriteria(User.class, "u");

                /*
                Aliases aliases = Aliases.create()
                        .add("u.department", "d", JoinType.LEFT_OUTER_JOIN)
                        .add("u.department", "d", JoinType.LEFT_OUTER_JOIN);
                aliases.addToCriteria(criteria);
                */

                criteria.createAlias("u.department", "d", JoinType.LEFT_OUTER_JOIN);
                criteria.createAlias("u.department", "d", JoinType.LEFT_OUTER_JOIN);

                criteria.add(Restrictions.isNotNull("d.name"));
                List<User> users = criteria.list();

                return users;
            }
        });

        System.out.println(users);
    }

}
