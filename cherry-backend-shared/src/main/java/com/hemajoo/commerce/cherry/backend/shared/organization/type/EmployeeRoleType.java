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
package com.hemajoo.commerce.cherry.backend.shared.organization.type;

/**
 * Enumeration representing the several possible <b>employee role</b> types.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum EmployeeRoleType
{
    /**
     * <b>Unspecified</b> employee role.
     */
    UNSPECIFIED,

    /**
     * Employee is a <b>manager</b>.
     */
    MANAGER,

    /**
     * Employee is a <b>crafter</b>.
     */
    CRAFTER,

    /**
     * Employee is a <b>cashier</b>.
     */
    CASHIER,

    /**
     * Employee is a <b>vendor</b>.
     */
    VENDOR,

    /**
     * Employee is a <b>delivery man</b>.
     */
    DELIVERY_MAN,

    /**
     * Employee is a <b>service agent</b>.
     */
    MAINTENANCE,

    /**
     * Employee is a <b>security agent</b>.
     */
    SECURITY,

    /**
     * <b>Other</b> employee role.
     */
    OTHER
}
