package com.github.experimental.strategy.join;

import static org.assertj.core.api.Assertions.assertThat;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Table;
import org.hibernate.service.ServiceRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fluent.hibernate.H;
import com.github.fluent.hibernate.cfg.strategy.StrategyOptions;
import com.github.fluent.hibernate.factory.HibernateSessionFactory;
import com.google.common.collect.Lists;

/**
 *
 * @author V.Ladynev
 */
public class JoinTableSamePersistentStrategyTest {

    private static final Class<?>[] PERISTENTS = new Class<?>[] { Author.class, Book.class };

    private static ServiceRegistry serviceRegistry;

    @BeforeClass
    public static void setUp() {
        serviceRegistry = new StandardServiceRegistryBuilder().build();
    }

    @AfterClass
    public static void tearDown() {
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }

    @Test
    public void log() {
        /*
        StrategyTestUtils.logSchemaUpdate(serviceRegistry,
                new ImplicitNamingStrategyLegacyJpaImpl(), PERISTENTS);
         */

        HibernateSessionFactory.Builder.configureWithoutHibernateCfgXml()
                .useNamingStrategy(new ImplicitNamingStrategyLegacyJpaImpl())
                .annotatedClasses(PERISTENTS).createSessionFactory();

        Author author = new Author();

        author.setOwnBooks(Lists.<Book> newArrayList());

        author.getOwnBooks().add(H.save(new Book()));

        H.save(author);
        /*
        HibernateSessionFactory.doInTransaction(new IRequest<Object[]>() {
            @Override
            public Object[] doInTransaction(Session session) {
                Query query = session.createSQLQuery(
                        "select * from  JoinTableSamePersistentStrategyTest$Author_JoinTableSamePersistentStrategyTest$Book");
                List<Object[]> list = query.list();
                System.out.println(list);
                for (Object[] join : list) {
                    System.out.println(Arrays.asList(join));
                }
                return null;
            }
        });
        */

        HibernateSessionFactory.closeSessionFactory();
    }

    // @Test
    public void testWithPrefixes() {
        Metadata metadata = StrategyTestUtils
                .createMetadata(serviceRegistry,
                        new Hibernate5NamingStrategy(
                                StrategyOptions.builder().tablePrefix("table").build()),
                        PERISTENTS);

        Table books = getJoinTable(metadata, "ownBooks");
        Table coauthorBooks = getJoinTable(metadata, "coauthorBooks");

        assertThat(books.getName()).isIn("table_authors_books", "table_authors_books_own_books");
        assertThat(coauthorBooks.getName()).isIn("table_authors_books",
                "table_authors_books_coauthor_books");

        assertThat(StrategyTestUtils.getColumNames(books.getColumnIterator()))
                .containsOnly("fk_author", "fk_own_books");
        assertThat(StrategyTestUtils.getColumNames(coauthorBooks.getColumnIterator()))
                .containsOnly("fk_author", "fk_coauthor_books");
    }

    // @Test
    public void testWithoutPrefixes() {
        Metadata metadata = StrategyTestUtils.createMetadata(serviceRegistry,
                new Hibernate5NamingStrategy(), PERISTENTS);

        Table books = getJoinTable(metadata, "ownBooks");
        Table coauthorBooks = getJoinTable(metadata, "coauthorBooks");

        assertThat(books.getName()).isIn("authors_books", "authors_books_own_books");
        assertThat(coauthorBooks.getName()).isIn("authors_books", "authors_books_coauthor_books");

        assertThat(StrategyTestUtils.getColumNames(books.getColumnIterator()))
                .containsOnly("fk_author", "fk_own_books");
        assertThat(StrategyTestUtils.getColumNames(coauthorBooks.getColumnIterator()))
                .containsOnly("fk_author", "fk_coauthor_books");
    }

    // @Test
    public void testRestrictLength() {
        StrategyOptions options = StrategyOptions.builder().tablePrefix("table").restrictLength(24)
                .restrictColumnNames(false).restrictConstraintNames(false).build();

        Metadata metadata = StrategyTestUtils.createMetadata(serviceRegistry,
                new Hibernate5NamingStrategy(options), PERISTENTS);

        Table books = getJoinTable(metadata, "ownBooks");
        Table coauthorBooks = getJoinTable(metadata, "coauthorBooks");

        assertThat(books.getName()).isIn("table_authors_books", "table_authrs_bks_own_bks");
        assertThat(coauthorBooks.getName()).isIn("table_authors_books", "table_athrs_bks_cthr_bks");
    }

    private static Table getJoinTable(Metadata metadata, String property) {
        Collection binding = metadata.getCollectionBinding(Author.class.getName() + "." + property);
        assertThat(binding).isNotNull();
        return binding.getCollectionTable();
    }

}
