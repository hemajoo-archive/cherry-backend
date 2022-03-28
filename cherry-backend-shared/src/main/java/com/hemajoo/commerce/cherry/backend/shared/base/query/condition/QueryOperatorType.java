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

/**
 * Enumeration representing the several possible query <b>operator</b> types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum QueryOperatorType
{
    /**
     * Operator is <b>Greater Than</b>.
     */
    GREATER_THAN,

    /**
     * Operator is <b>Less Than</b>.
     */
    LESS_THAN,

    /**
     * Operator is <b>Greater Than or Equal</b>.
     */
    GREATER_THAN_EQUAL,

    /**
     * Operator is <b>Less Than or Equal</b>.
     */
    LESS_THAN_EQUAL,

    /**
     * Operator is <b>Not Equal</b>.
     */
    NOT_EQUAL,

    /**
     * Operator is <b>Equal</b>.
     */
    EQUAL,

    /**
     * Operator is <b>Match</b>.
     */
    MATCH,

    /**
     * Operator is <b>Match Between</b>.
     */
    MATCH_BETWEEN,

    /**
     * Operator is <b>Match Before</b>.
     */
    MATCH_BEFORE,

    /**
     * Operator is <b>Match End</b>.
     */
    MATCH_END,

    /**
     * Operator is <b>Equal Object UUID</b>.
     */
    EQUAL_OBJECT_UUID,

    /**
     * Value should be <b>between</b> low and high values.
     */
    BETWEEN;
}
