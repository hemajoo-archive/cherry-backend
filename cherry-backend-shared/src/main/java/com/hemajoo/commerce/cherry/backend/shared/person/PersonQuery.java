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
package com.hemajoo.commerce.cherry.backend.shared.person;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.query.BaseEntityQuery;
import com.hemajoo.commerce.cherry.backend.shared.base.query.DataType;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryField;

/**
 * Represents a <b>query</b> object for issuing queries on persons.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class PersonQuery extends BaseEntityQuery
{
    public static final String PERSON_LASTNAME = "lastName";
    public static final String PERSON_FIRSTNAME = "firstName";
    public static final String PERSON_BIRTHDATE = "birthDate";
    public static final String PERSON_GENDER_TYPE = "genderType";
    public static final String PERSON_PERSON_TYPE = "personType";
    public static final String PERSON_PARENT_ID = "parentId";

    /**
     * Creates a new <b>person</b> query instance.
     */
    public PersonQuery()
    {
        super(EntityType.PERSON);

        fields.add(QueryField.builder()
                .withFieldName(PERSON_FIRSTNAME)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PERSON_LASTNAME)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PERSON_BIRTHDATE)
                .withFieldType(DataType.DATE)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PERSON_PERSON_TYPE)
                .withFieldType(DataType.ENUM)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PERSON_GENDER_TYPE)
                .withFieldType(DataType.ENUM)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PERSON_PARENT_ID)
                .withFieldType(DataType.UUID)
                .build());
    }
}
