package com.github.experimental.utils.example;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;

public final class HibernateUtils {


    private HibernateUtils() {

    }

    public static int count(Query query) {
        Number result = (Number) query.uniqueResult();
        return result == null ? 0 : result.intValue();
    }

    public static int count(Criteria countCriteria) {
        Number count = (Number) countCriteria
                .setProjection(Projections.rowCount())
                .uniqueResult();
        return count.intValue();
    }

}
