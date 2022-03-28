/*
 * (C) Copyright Resse Christophe 2021 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Resse Christophe. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Resse C. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Resse Christophe (christophe.resse@gmail.com).
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.backend.shared.base.query;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryCondition;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryField;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryOperatorType;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an abstract <b>query</b> object for the <b>audit</b> part of entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public abstract class AbstractAuditQuery implements IQuery, Serializable
{
    public static final String BASE_CREATED_DATE = "createdDate";
    public static final String BASE_MODIFIED_DATE = "modifiedDate";
    public static final String BASE_CREATED_BY = "createdBy";
    public static final String BASE_MODIFIED_BY = "modifiedBy";

    @Getter
    private final EntityType entityType;

    /**
     * Available fields that can be queried.
     */
    protected final List<QueryField> fields = new ArrayList<>();

    /**
     * Query conditions.
     */
    protected final List<QueryCondition> conditions = new ArrayList<>();

    /**
     * Adds a query condition.
     * @param condition Query condition.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    @Override
    public final <T extends BaseEntityQuery> T addCondition(final @NonNull QueryCondition condition) throws QueryConditionException
    {
        if (condition.getField() != null && condition.getOperator() != null && !condition.getValues().isEmpty())
        {
            if (fields.stream().anyMatch(e -> e.getFieldName().equals(condition.getField())))
            {
                conditions.add(condition);
            }
            else
            {
                String message = String.format("Cannot add query condition for field with name: '%s' because this field is not part of the entity class hierarchy for: '%s'!",
                        condition.getField(),
                        this.getClass().getName());
                LOGGER.error(message);

                throw new QueryConditionException(message);
            }
        }
        else
        {
            String message = String.format("Invalid query condition field with name: '%s', with operator: '%s', with values: '%s'",
                    condition.getField(),
                    condition.getOperator(),
                    condition.getValues());
            LOGGER.error(message);

            throw new QueryConditionException(message);
        }

        QueryField field = fields.stream().filter(e -> e.getFieldName().equals(condition.getField())).findAny().orElse(null);
        if (field != null && field.getFieldType() == DataType.ENUM)
        {
            checkConditionForEnumField(field, condition);
        }

        return (T) this;
    }

    /**
     * Checks query condition for fields of type {@link DataType#ENUM}.
     * @param field Field.
     * @param condition Query condition.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    private void checkConditionForEnumField(final @NonNull QueryField field, final @NonNull QueryCondition condition) throws QueryConditionException
    {
        if (condition.getOperator() != QueryOperatorType.EQUAL)
        {
            String message = String.format("Invalid query condition for field with name: '%s', with type: '%s', with operator: '%s'! Only: '%s' operator is allowed for fields of type: '%s'",
                    condition.getField(),
                    field.getFieldType(),
                    condition.getOperator(),
                    Arrays.toString(new QueryOperatorType[]{ QueryOperatorType.EQUAL }),
                    field.getFieldType());
            LOGGER.error(message);

            throw new QueryConditionException(message);
        }
    }

    @Override
    public final GenericSpecification<?> getSpecification()
    {
        GenericSpecification<?> specification = new GenericSpecification<>();

        for (QueryCondition condition : conditions)
        {
            specification.add(condition);
        }

        return specification;
    }

    /**
     * Creates a new abstract audit query instance.
     * @param entityType Entity type.
     */
    public AbstractAuditQuery(final @NonNull EntityType entityType)
    {
        this.entityType = entityType;

        fields.add(QueryField.builder()
                .withFieldName(BASE_CREATED_DATE)
                .withFieldType(DataType.DATE)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_MODIFIED_DATE)
                .withFieldType(DataType.DATE)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_CREATED_BY)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_MODIFIED_BY)
                .withFieldType(DataType.STRING)
                .build());
    }
}
