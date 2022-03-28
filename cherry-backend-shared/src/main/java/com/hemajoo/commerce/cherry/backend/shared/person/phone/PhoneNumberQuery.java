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
package com.hemajoo.commerce.cherry.backend.shared.person.phone;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.query.BaseEntityQuery;
import com.hemajoo.commerce.cherry.backend.shared.base.query.DataType;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryField;

/**
 * Object to issue queries on <b>phone number</b> entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class PhoneNumberQuery extends BaseEntityQuery
{
    public static final String PHONE_NUMBER_IS_DEFAULT = "isDefault";
    public static final String PHONE_NUMBER_NUMBER = "number";
    public static final String PHONE_NUMBER_COUNTRY_CODE = "countryCode";
    public static final String PHONE_NUMBER_PHONE_TYPE = "phoneType";
    public static final String PHONE_NUMBER_CATEGORY_TYPE = "categoryType";
    public static final String PHONE_NUMBER_PARENT_ID = "parentId";

    public PhoneNumberQuery()
    {
        super(EntityType.PHONE_NUMBER);

        fields.add(QueryField.builder()
                .withFieldName(PHONE_NUMBER_CATEGORY_TYPE)
                .withFieldType(DataType.ENUM)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PHONE_NUMBER_COUNTRY_CODE)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PHONE_NUMBER_NUMBER)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PHONE_NUMBER_IS_DEFAULT)
                .withFieldType(DataType.BOOLEAN)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PHONE_NUMBER_PHONE_TYPE)
                .withFieldType(DataType.ENUM)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(PHONE_NUMBER_PARENT_ID)
                .withFieldType(DataType.UUID)
                .build());
    }
}
