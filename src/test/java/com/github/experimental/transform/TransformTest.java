package com.github.experimental.transform;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fluent.hibernate.H;
import com.github.fluent.hibernate.IRequest;
import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.HibernateProperties;
import com.github.fluent.hibernate.cfg.HibernateSessionFactory;
import com.github.fluent.hibernate.internal.util.InternalUtils.CollectionUtils;
import com.github.fluent.hibernate.transformer.FluentHibernateResultTransformer;

public class TransformTest {

    @BeforeClass
    public static void init() {
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(
                        HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true))
                .useNamingStrategy().scanPackages("com.github.experimental.transform").build();
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

    // @Test
    public void transformerTest() {
        List<User> users = HibernateSessionFactory.doInTransaction(new IRequest<List<User>>() {
            @Override
            public List<User> doInTransaction(Session session) {
                Criteria criteria = session.createCriteria(User.class, "u");
                criteria.createAlias("u.department", "d", JoinType.LEFT_OUTER_JOIN);
                criteria.setProjection(Projections.projectionList()
                        .add(Projections.property("u.login").as("login"))
                        .add(Projections.property("d.name").as("department.name")));

                List<User> users = criteria
                        .setResultTransformer(new FluentHibernateResultTransformer(User.class))
                        .list();

                return users;
            }
        });

        System.out.println(users);
    }

    @Test
    public void transformerNativeSqlNestedTest() {
        List<User> list = HibernateSessionFactory.doInTransaction(new IRequest<List<User>>() {
            @Override
            public List<User> doInTransaction(Session session) {
                /*
                String sql = "select login as \"login\" from users u";
                return session.createSQLQuery(sql)
                        .setResultTransformer(Transformers.aliasToBean(UserDto.class)).list();
                 */

                String sql = "select u.login, d.name as \"department.name\" "
                        + "from users u left outer join departments d on u.fk_department = d.f_pid";
                List<User> users = session.createSQLQuery(sql)
                        .setResultTransformer(new FluentHibernateResultTransformer(User.class))
                        .list();

                return users;

            }
        });

        System.out.println(list);

        /*
        final String sql = "select {u.*}, {d.*} from users u left outer join departments d on u.fk_department = d.f_pid";
        
        List<User> users = HibernateSessionFactory.doInTransaction(new IRequest<List<User>>() {
            @Override
            public List<User> doInTransaction(Session session) {
                return session.createSQLQuery(sql).addEntity("u", User.class)
                        .addJoin("d", "u.department").addEntity("u", User.class)
                        .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            }
        });
        */
    }

    // @Test
    public void transformerNativeSqlTest() {
        List<User> list = HibernateSessionFactory.doInTransaction(new IRequest<List<User>>() {
            @Override
            public List<User> doInTransaction(Session session) {
                List<User> users = session.createSQLQuery("select login from users")
                        .setResultTransformer(new FluentHibernateResultTransformer(User.class))
                        .list();
                return users;

            }
        });

        System.out.println(list);
    }

    /**
     *
     * https://hibernate.atlassian.net/browse/HHH-8806
     *
     * http://stackoverflow.com/questions/6333872/hibernate-many-to-one-using-addjoin
     *
     * http://stackoverflow.com/questions/7186806/hibernate-native-sql-query-retrieving-entities-and
     * -collections/
     *
     * https://hibernate.atlassian.net/browse/HHH-2831
     */

    /*
    java.lang.ClassCastException:
    
    com.github.fluent.hibernate.request.persistent.UserDto cannot
    be cast
    to java.
    util.Map
    at org.hibernate.property.access.internal.PropertyAccessMapImpl$SetterImpl.set(PropertyAccessMapImpl.java:102)
    at org.hibernate.transform.AliasToBeanResultTransformer.transformTuple(AliasToBeanResultTransformer.java:78)
    at org.hibernate.hql.internal.HolderInstantiator.instantiate(HolderInstantiator.java:75)
    at org.hibernate.loader.custom.CustomLoader.getResultList(CustomLoader.java:435)
    at org.hibernate.loader.Loader.listIgnoreQueryCache(Loader.java:2422)
    at org.hibernate.loader.Loader.list(Loader.java:2417)
    at org.hibernate.loader.custom.CustomLoader.list(CustomLoader.java:336)
    at org.hibernate.internal.SessionImpl.listCustomQuery(SessionImpl.java:1980)
    at org.hibernate.internal.AbstractSessionImpl.list(AbstractSessionImpl.java:322)
    at org.hibernate.internal.SQLQueryImpl.list(SQLQueryImpl.java:125)
    at com.github.fluent.hibernate.request.HibernateQuery$1.doInTransaction(HibernateQuery.java:77)
    at com.github.fluent.hibernate.request.HibernateQuery$1.doInTransaction(HibernateQuery.java:1)
    at com.github.fluent.hibernate.cfg.HibernateSessionFactory.doInTransaction(HibernateSessionFactory.java:55)
    at com.github.fluent.hibernate.request.HibernateQuery.list(HibernateQuery.java:74)
    at com.github.fluent.hibernate.request.HibernateSqlRequest.list(HibernateSqlRequest.java:91)
    at com.github.fluent.hibernate.request.HibernateSqlRequestTest.requestWithDtoTransformA(HibernateSqlRequestTest.java:93)
    at sun.reflect.NativeMethodAccessorImpl.
    
    invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
    at java.lang.reflect.Method.invoke(Method.java:597)
    at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
    at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
    at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
    at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
    at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
    at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
    at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
    at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
    at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
    at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
    at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
    at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
    at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
    at org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:27)
    at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
    at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
    at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
    at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
    */

}
