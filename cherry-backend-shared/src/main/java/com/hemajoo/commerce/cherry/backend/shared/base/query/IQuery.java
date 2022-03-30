package com.hemajoo.commerce.cherry.backend.shared.base.query;

import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryCondition;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import lombok.NonNull;

/**
 * Defines the behavior of a <b>query</b> object.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IQuery
{
    /**
     * Adds a query condition.
     * @param condition Condition.
     * @param <T> Query object type.
     * @return Query object type.
     * @throws QueryConditionException Thrown to indicate an error occurred when adding a query condition.
     */
    <T extends BaseEntityQuery> T addCondition(final @NonNull QueryCondition condition) throws QueryConditionException;

    /**
     * Returns the specification for the query object.
     * @return Specification object.
     */
    GenericSpecification<?> getSpecification();

    /**
     * Validates the conditions of the query object.
     * @throws QueryConditionException Thrown to indicate an error occurred when validating the conditions of a query.
     */
    void validate() throws QueryConditionException;
}
