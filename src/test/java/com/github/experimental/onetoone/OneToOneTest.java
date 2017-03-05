package com.github.experimental.onetoone;

import com.github.experimental.test.utils.BaseTest;
import com.github.fluent.hibernate.H;
import org.junit.BeforeClass;
import org.junit.Test;

public class OneToOneTest extends BaseTest {

    @BeforeClass
    public static void init() {
        init("com.github.experimental.onetoone");
    }

    @Test
    public void updateTest() {
        BookingModel booking = H.save(new BookingModel("booking"));
        H.save(new ClassModel("booking"));
        BookingModel savedBooking = H.getById(BookingModel.class, booking.getId());
        System.out.println(savedBooking.getClassModel());
    }

}
