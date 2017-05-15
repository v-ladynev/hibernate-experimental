package com.github.experimental.strategy.join;

import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitJoinColumnNameSource;
import org.hibernate.boot.model.naming.ImplicitJoinTableNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;

public class PosperNamingStrategy extends ImplicitNamingStrategyLegacyJpaImpl {

    private static final long serialVersionUID = -6156051787915506283L;
    private static final String PREFIX = "posper_";

    @Override
    protected String transformEntityName(EntityNaming entityNaming) {
        return PREFIX + entityNaming.getJpaEntityName().toLowerCase();
    }

    @Override
    public Identifier determineJoinTableName(ImplicitJoinTableNameSource source) {
        String tname = (PREFIX + source.getOwningEntityNaming().getJpaEntityName() + "_"
                + source.getNonOwningEntityNaming().getJpaEntityName()).toLowerCase();
        return Identifier.toIdentifier(tname);
    }

    @Override
    public Identifier determineJoinColumnName(ImplicitJoinColumnNameSource source) {
        final String name;
        if (source.getNature() == ImplicitJoinColumnNameSource.Nature.ELEMENT_COLLECTION
                || source.getAttributePath() == null) {
            // This is the relevant change to avoid that the join column name is also prefixed
            // which happened for ManyToMany associations
            name = source.getEntityNaming().getJpaEntityName().toLowerCase() + '_'
                    + source.getReferencedColumnName().getText();
        } else {
            name = transformAttributePath(source.getAttributePath()) + '_'
                    + source.getReferencedColumnName().getText();
        }

        return toIdentifier(name, source.getBuildingContext());
    }

}

/*

@Entity
public class Car {

    @Id
    private Integer pid;

    @ManyToMany
    private List<Reservation> coauthorBooks;

    @OneToMany
    private List<Reservation> ownBooks;

}

@Entity
public class Reservation {

    @Id
    private Integer pid;

}

   create table posper_author_book (
        author_pid integer not null,
        ownBooks_pid integer not null,
        coauthorBooks_pid integer not null
    );

    // @Test
    public void showSchema() {

        StrategyTestUtils
                .logSchemaUpdate(serviceRegistry,
                        new Hibernate5NamingStrategy(
                                StrategyOptions.builder().tablePrefix("table").build()),
                        PERISTENTS);

        // StrategyTestUtils.logSchemaUpdate(serviceRegistry, new PosperNamingStrategy(),
        // PERISTENTS);

        // StrategyTestUtils.logSchemaUpdate(serviceRegistry, null, PERISTENTS);
    }


  */
