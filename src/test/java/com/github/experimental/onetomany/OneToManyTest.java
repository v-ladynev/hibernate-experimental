package com.github.experimental.onetomany;

import com.github.experimental.onetoone.BookingModel;
import com.github.experimental.onetoone.ClassModel;
import com.github.experimental.test.utils.BaseTest;
import com.github.fluent.hibernate.H;
import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class OneToManyTest extends BaseTest {

    @BeforeClass
    public static void init() {
        init("com.github.experimental.onetomany");
    }

    @Test
    public void updateTest() {
        System.out.println(H.save(new Car(Lists.newArrayList(new Reservation(), new Reservation()))).getPid());
        System.out.println(H.save(new Car(Lists.newArrayList(new Reservation()))).getPid());
        System.out.println(H.save(new Car()).getPid());
        System.out.println(H.save(new Car()).getPid());

        List<Car> result = H.<Car>request(
                "select c from Car c where not exists (select r.pid from Reservation r where r.car.pid = c.pid)"
        ).list();

        System.out.println(result);
    }

}
