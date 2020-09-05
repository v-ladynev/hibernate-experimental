package com.github.experimental.manytomany;

import com.github.experimental.test.utils.BaseTest;
import com.github.experimental.utils.HibernateUtils;
import com.github.fluent.hibernate.H;
import com.google.common.collect.Sets;
import org.hibernate.Query;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ManyToManyTest extends BaseTest {

    @BeforeClass
    public static void init() {
        defaultBuilder("com.github.experimental.manytomany").build();
    }

    @Test
    public void loadTest() {
        Class xclass = new Class();
        xclass.setStudents(Sets.newHashSet(
                H.save(new Student()),
                H.save(new Student()),
                H.save(new Student())
        ));

        H.save(xclass);

        List<Class> result = H.<Class>request("select c from Class c").list();
        System.out.println(result);

        Integer count = H.request(session -> {
            Query query = session.createQuery("select count(*) from Class c");
            return HibernateUtils.count(query);
        });

        System.out.println(count);
    }

}
