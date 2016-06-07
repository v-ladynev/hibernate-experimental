package com.github.experimental.usersystem;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fluent.hibernate.H;
import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.HibernateProperties;

public class UserSystemTest {

    @BeforeClass
    public static void init() {
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(
                        HibernateProperties.forH2CreateDrop().showSql(true).formatSql(true))
                .scanPackages("com.github.experimental.usersystem").build();
        createTestData();
    }

    @AfterClass
    public static void closeSessionFactory() {
        Fluent.factory().close();
    }

    private static void createTestData() {
        Role admin = H.save(Role.create(RoleEnum.ADMIN));
        Role user = H.save(Role.create(RoleEnum.USER));

        SomeSystem systemA = H.save(SomeSystem.create("system_a"));
        SomeSystem systemB = H.save(SomeSystem.create("system_b"));

        User userA = User.create("user_a");
        userA.getRoles().addAll(Arrays.asList(admin));
        userA.getSystems().addAll(Arrays.asList(systemA));

        H.save(userA);

        User userB = User.create("user_b");
        userB.getRoles().addAll(Arrays.asList(admin, user));
        userB.getSystems().addAll(Arrays.asList(systemA, systemB));

        H.save(userB);
    }

    @Test
    public void fetchUsers() {
        printWithRoles(getUserWithRoles("user_a"));
        printWithSystems(getUserWithSystems("user_a"));

        printWithRoles(getUserWithRoles("user_b"));
        printWithSystems(getUserWithSystems("user_b"));
    }

    private User getUserWithRoles(String name) {
        return H.<User> request("from User u left join fetch u.roles where u.userName = :userName")
                .p("userName", name).first();
    }

    private User getUserWithSystems(String name) {
        return H.<User> request(
                "from User u left join fetch u.systems where u.userName = :userName")
                .p("userName", name).first();
    }

    private static void printWithRoles(User user) {
        System.out.println(String.format("Roles user name: %s", user.getUserName()));
        for (Role role : user.getRoles()) {
            System.out.println(role.getRole().name());
        }
    }

    private static void printWithSystems(User user) {
        System.out.println(String.format("Systems user name: %s", user.getUserName()));
        for (SomeSystem system : user.getSystems()) {
            System.out.println(system.getName());
        }
    }

}
