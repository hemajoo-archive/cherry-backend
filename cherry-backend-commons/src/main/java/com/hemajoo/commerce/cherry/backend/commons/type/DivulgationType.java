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
package com.hemajoo.commerce.cherry.backend.commons.type;

/**
 * Enumeration representing the several possible <b>divulgation</b> types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum DivulgationType
{
    /**
     * The underlying entity is to be considered as <b>private</b> and should not be publicly published.
     */
    PRIVATE,

    /**
     * The underlying entity is to be considered as <b>protected</b>.
     */
    PROTECTED,

    /**
     * The underlying entity is to be considered as <b>public</b>.
     */
    PUBLIC,

    /** //TODO Not clear!
     * The underlying entity is to be considered as owned by an <b>organization</b>.
     */
    ORGANIZATION,

    /**  //TODO Not clear!
     * The underlying entity is to be considered as owned .
     */
    COMPANY,
}
