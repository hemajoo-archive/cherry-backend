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
package com.hemajoo.commerce.cherry.backend.shared.base.search.criteria;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a <b>search criteria</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public final class SearchCriteria
{
    /**
     * Criteria key.
     */
    @Getter
    private final String key;

    /**
     * Criteria value.
     */
    @Getter
    private final Object value;

    /**
     * Criteria search operator.
     */
    @Getter
    private final SearchOperation operation;

    /**
     * Creates a new search criteria.
     * @param key Criteria key.
     * @param value Criteria value.
     * @param operator Criteria operator.
     */
    @Builder(setterPrefix = "with")
    public SearchCriteria(String key, Object value, SearchOperation operator)
    {
        this.key = key;
        this.value = value;
        this.operation = operator;
    }
}
