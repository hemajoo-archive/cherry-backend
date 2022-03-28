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
package com.hemajoo.commerce.cherry.backend.shared.person.address.email;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.query.BaseEntityQuery;
import com.hemajoo.commerce.cherry.backend.shared.base.query.DataType;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryField;

/**
 * Represents a <b>query</b> object for issuing queries on email addresses.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public final class EmailAddressQuery extends BaseEntityQuery
{
    public static final String EMAIL_ADDRESS_EMAIL = "email";
    public static final String EMAIL_ADDRESS_IS_DEFAULT = "isDefaultEmail";
    public static final String EMAIL_ADDRESS_TYPE = "addressType";
    public static final String EMAIL_ADDRESS_PARENT_ID = "parentId";

    /**
     * Creates a new <b>email address</b> search instance.
     */
    public EmailAddressQuery()
    {
        super(EntityType.EMAIL_ADDRESS);

        fields.add(QueryField.builder()
                .withFieldName(EMAIL_ADDRESS_EMAIL)
                .withFieldType(DataType.STRING)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(EMAIL_ADDRESS_IS_DEFAULT)
                .withFieldType(DataType.BOOLEAN)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(EMAIL_ADDRESS_TYPE)
                .withFieldType(DataType.ENUM)
                .build());
        fields.add(QueryField.builder()
                .withFieldName(EMAIL_ADDRESS_PARENT_ID)
                .withFieldType(DataType.UUID)
                .build());
    }
}
