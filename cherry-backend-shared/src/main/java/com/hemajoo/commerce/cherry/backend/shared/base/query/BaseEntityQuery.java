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
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryField;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * Represents a base entity <b>query</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class BaseEntityQuery extends AbstractStatusQuery
{
    public static final String BASE_ENTITY_ID = "id";
    public static final String BASE_ENTITY_TYPE = "entityType";
    public static final String BASE_NAME = "name";
    public static final String BASE_DESCRIPTION = "description";
    public static final String BASE_REFERENCE = "reference";
    public static final String BASE_PARENT = "parent";
    public static final String BASE_PARENT_TYPE = "parentType";

    /**
     * Creates a new base entity query instance.
     * @param entityType Entity type.
     */
    public BaseEntityQuery(final @NonNull EntityType entityType)
    {
        super(entityType);

        fields.add(QueryField.builder()
                .withFieldName(BASE_ENTITY_ID)
                .withFieldType(DataType.UUID)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_NAME)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_DESCRIPTION)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_REFERENCE)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_ENTITY_TYPE)
                .withFieldType(DataType.ENUM)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_PARENT)
                .withFieldType(DataType.UUID)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_PARENT_TYPE)
                .withFieldType(DataType.ENUM)
                .build());
    }
}
