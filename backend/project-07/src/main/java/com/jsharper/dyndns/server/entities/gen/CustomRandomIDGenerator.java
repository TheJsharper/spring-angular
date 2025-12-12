package com.jsharper.dyndns.server.entities.gen;

import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;

import static org.hibernate.generator.EventTypeSets.INSERT_ONLY;

public class CustomRandomIDGenerator implements BeforeExecutionGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue, EventType eventType) {
        final var criteriaBuilder = session.getCriteriaBuilder();

        final var query = criteriaBuilder.createQuery(Long.class);

        final var baseRoot = query.from(owner.getClass());

        final CriteriaQuery<Long> r = query.select(criteriaBuilder.count(baseRoot));

        final Long currentCount = session.createSelectionQuery(r).getSingleResult();

        return currentCount == 0 ? 1 : currentCount * 150;
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return INSERT_ONLY;
    }


}
