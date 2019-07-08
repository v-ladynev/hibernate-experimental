package com.github.experimental.join.filter;

import com.github.experimental.test.utils.BaseTest;
import com.github.fluent.hibernate.H;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JoinFilterTest extends BaseTest {

    @BeforeClass
    public static void init() {
        init("com.github.experimental.join.filter");
    }

    @Test
    public void updateTest() {
        Bank bank = new Bank();
        bank.setOfficeList(Arrays.asList(new Office(bank), new Office(bank)));
        H.save(bank);


        List<Bank> result = H.<Bank>request(
                "from Bank"
        ).list();

        //System.out.println(result);

        Office office = H.getById(Office.class, 2);
        H.delete(office);
    }

}
