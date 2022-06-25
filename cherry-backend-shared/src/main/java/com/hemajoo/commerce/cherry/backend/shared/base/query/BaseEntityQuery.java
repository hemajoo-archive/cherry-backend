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

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    /**
     * Field: <b>id</b> of an entity.
     */
    @JsonIgnore
    public static final String BASE_ENTITY_ID = "id";

    /**
     * Field: <b>entityType</b> of an entity.
     */
    @JsonIgnore
    public static final String BASE_ENTITY_TYPE = "entityType";

    /**
     * Field: <b>name</b> of an entity.
     */
    @JsonIgnore
    public static final String BASE_NAME = "name";

    /**
     * Field: <b>description</b> of an entity.
     */
    @JsonIgnore
    public static final String BASE_DESCRIPTION = "description";

    /**
     * Field: <b>reference</b> of an entity.
     */
    @JsonIgnore
    public static final String BASE_REFERENCE = "reference";

    /**
     * Field: <b>parentId</b> of an entity.
     */
    @JsonIgnore
    public static final String BASE_PARENT_ID = "parentId";

    /**
     * Field: <b>parent</b> of an entity.
     */
    @JsonIgnore
    public static final String BASE_PARENT = "parent";

    /**
     * Field: <b>parentType</b> of an entity.
     */
    @JsonIgnore
    public static final String BASE_PARENT_TYPE = "parentType";

    /**
     * Field: <b>tags</b> of an entity.
     */
    @JsonIgnore
    public static final String BASE_TAGS = "tags";

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
                .withFieldName(BASE_TAGS)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_ENTITY_TYPE)
                .withFieldType(DataType.ENUM)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_PARENT_ID)
                .withFieldType(DataType.UUID)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(BASE_PARENT_TYPE)
                .withFieldType(DataType.ENUM)
                .withClassType(EntityType.class)
                .build());
    }

    /**
     * Creates a new base entity query instance.
     */
    public BaseEntityQuery()
    {
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
                .withFieldName(BASE_TAGS)
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
