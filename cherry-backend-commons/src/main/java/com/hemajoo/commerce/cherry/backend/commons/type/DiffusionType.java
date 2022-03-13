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
 * Enumeration representing the several possible <b>diffusion</b> types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum DiffusionType
{
    /**
     * The information is <b>public</b>.
     */
    PUBLIC,

    /**
     * The information can be publicly diffused if a <b>consent</b> is provided.
     */
    PUBLIC_REQUIRE_CONSENT,

    /**
     * The information is <b>private</b>.
     */
    PRIVATE,

    /**
     * The information can be shared only with people belonging to the organization the entity belongs to.
     */
    ORGANIZATION,

    /**
     * The information can be shared only with people belonging to the company the entity belongs to.
     */
    COMPANY
}
