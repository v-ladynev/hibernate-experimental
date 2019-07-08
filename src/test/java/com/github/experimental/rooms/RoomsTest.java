package com.github.experimental.rooms;

import com.github.experimental.test.utils.BaseTest;
import com.github.fluent.hibernate.H;
import com.google.common.collect.ImmutableSet;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class RoomsTest extends BaseTest {

    @BeforeClass
    public static void init() {
        init("com.github.experimental.rooms");
    }

    @Test
    public void updateTest() {
        Category c1 = H.save(new Category());

        Facility f1 = H.save(new Facility("f1"));
        Facility f2 = H.save(new Facility("f2"));

        Room roomA = new Room("roomA", c1);
        roomA.setFacilities(ImmutableSet.of(f1, f2));
        H.save(roomA);


        Room roomB = new Room("roomB", c1);
        roomB.setFacilities(ImmutableSet.of(f1));
        H.save(roomB);


        List<Room> result = H.<Room>request(
                "select r from Room r left join fetch r.category"
        ).list();

        System.out.println(result);
    }

}
