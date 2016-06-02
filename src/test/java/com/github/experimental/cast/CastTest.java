package com.github.experimental.cast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.github.fluent.hibernate.H;
import com.github.fluent.hibernate.cfg.Fluent;
import com.github.fluent.hibernate.cfg.HibernateProperties;

public class CastTest {

    // @Test
    public void castTest() {
        List listOfFriends = new ArrayList<Object>(Arrays.asList("a", "r"));
        fetchListOfFriendss(listOfFriends);
    }

    private List<FriendsDetails> fetchListOfFriendss(List<?> genericList) {
        Iterator<?> itr = genericList.iterator();
        List<FriendsDetails> listOfFriend = new ArrayList<FriendsDetails>();

        while (itr.hasNext()) {
            Object obj = itr.next();
            String userName = String.valueOf(obj);
            System.out.println(userName);

            FriendsDetails obj1 = new FriendsDetails();
            obj1.setFriendName(userName);

            listOfFriend.add(obj1);
        }
        return listOfFriend;
    }

    class FriendsDetails {

        public void setFriendName(String name) {

        }
    }

    @Test
    public void joinTest() {
        Fluent.factory().dontUseHibernateCfgXml()
                .hibernateProperties(HibernateProperties.forH2CreateDrop().showSql(true))
                .useNamingStrategy().scanPackages("com.github.experimental.cast").build();

        List<FuturePlan> list = H
                .<FuturePlan> request(
                        "select fp from FuturePlan fp where fp.location.lName = :name")
                .p("name", "name").list();
        System.out.println(list);

        Fluent.factory().close();
    }

}
