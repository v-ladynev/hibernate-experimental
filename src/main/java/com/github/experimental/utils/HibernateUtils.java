package com.github.experimental.utils;


import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class HibernateUtils {


    private HibernateUtils() {

    }

    @SuppressWarnings("unchecked")
    public static <T> T init(T proxy) {
        Predicate initialized = Hibernate::isInitialized;
        Optional.ofNullable(proxy).filter(initialized.negate()).ifPresent(Hibernate::initialize);
        return proxy;
    }

    public static void init(Supplier<?>... suppliers) {
        Stream.of(suppliers).forEach(HibernateUtils::init);
    }

    public static boolean isObjectPropertyInitialized(Object object, String propertyName) throws Exception {
        Object propertyValue = PropertyUtils.getSimpleProperty(object, propertyName);
        return Hibernate.isInitialized(propertyValue);
    }


    /**
     * If object is lazy, we can't just simply call "getId" method or get id from the field in the object method.
     */
    @SuppressWarnings("unchecked")
    public static <T, R> R getId(T persistent, Supplier<R> getId) {
        if (persistent == null) {
            return null;
        }

        if (!(persistent instanceof HibernateProxy) || Hibernate.isInitialized(persistent)) {
            return getId.get();
        }

        LazyInitializer initializer = ((HibernateProxy) persistent).getHibernateLazyInitializer();
        return (R) initializer.getIdentifier();
    }

    public static String getIdPropertyName(Class<?> entityClass, SessionFactory factory) {
        return factory.getClassMetadata(entityClass).getIdentifierPropertyName();
    }

    public static Long getForeignKey(Criteria baseCriteria, Long ownerId, String foreignKeyProperty) {
        baseCriteria
                .add(Restrictions.idEq(ownerId))
                .setProjection(Projections.property(foreignKeyProperty));
        return uniqueResult(baseCriteria);
    }

    public static boolean isUniqueExists(Criteria uniqueCriteria) {
        uniqueCriteria.setProjection(Projections.id());
        return uniqueCriteria.uniqueResult() != null;
    }

    public static int count(Criteria countCriteria) {
        Number count = (Number) countCriteria
                .setProjection(Projections.rowCount())
                .uniqueResult();
        return count.intValue();
    }

    public static int count(Query query) {
        return correctedNumber(query).intValue();
    }

    public static Number correctedNumber(Query query) {
        Number result = uniqueResult(query);
        return result == null ? Long.valueOf(0) : result;
    }


    public static <T> T firstRecord(Criteria criteria) {
        List<T> result = list(criteria);
        return result == null || result.size() == 0 ? null : result.get(0);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> list(Criteria criteria) {
        return (List<T>) criteria.list();
    }

    public static <T> T firstByLimit(Query query) {
        query.setMaxResults(1);
        return uniqueResult(query);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> list(Query query) {
        return (List<T>) query.list();
    }

    @SuppressWarnings("unchecked")
    public static <T> T uniqueResult(Query query) {
        return (T) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public static <T> T uniqueResult(Criteria criteria) {
        return (T) criteria.uniqueResult();
    }

}
