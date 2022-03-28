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
package com.hemajoo.commerce.cherry.backend.shared.base.query.condition;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a query <b>condition</b> to issue queries on entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@Builder(setterPrefix = "with")
public final class QueryCondition
{
    /**
     * Field's name.
     */
    @Getter
    private final String field;

    /**
     * List of values.
     */
    @Getter
    @Singular // For chaining of arguments
    private List<Object> values;

    /**
     * Operator type.
     */
    @Getter
    private final QueryOperatorType operator;

    /**
     * Sets the value for the given index.
     * @param index Index of the value to set.
     * @param value Value to set.
     */
    public void setValue(final int index, final @NonNull Object value)
    {
        if (index <= values.size())
        {
            List<Object> list = new ArrayList<>(List.of(values));
            list.set(index, value);
            values = list;
        }
        else
        {
            LOGGER.warn(String.format("Cannot set value: '%s' for search condition with index: '%s' because index is out of bound!", value, index));
        }
    }
}
