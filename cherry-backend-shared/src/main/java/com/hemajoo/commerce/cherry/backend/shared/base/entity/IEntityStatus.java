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
package com.hemajoo.commerce.cherry.backend.shared.base.entity;

import com.hemajoo.commerce.cherry.backend.commons.type.StatusType;

import java.util.Date;

/**
 * Defines the behavior of the status of an <b>entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IEntityStatus extends IEntityAudit
{
    /**
     * Returns the status type.
     * @return Status type.
     */
    StatusType getStatusType();

    /**
     * Sets the status type date.
     * @param type Status type.
     */
    void setStatusType(final StatusType type);

    /**
     * Returns the inactivation date.
     * @return Inactivation since date.
     */
    Date getSince();

    /**
     * Sets the since (inactivation) date.
     * @param date Inactivation date.
     */
    void setSince(final Date date);
}
