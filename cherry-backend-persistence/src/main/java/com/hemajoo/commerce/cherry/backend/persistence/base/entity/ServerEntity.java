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
package com.hemajoo.commerce.cherry.backend.persistence.base.entity;

import com.hemajoo.commerce.cherry.backend.shared.base.entity.IBaseEntity;

/**
 * Defines the behavior of a <b>server entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @since Cherry 0.1.0
 * @version 1.0.0
 */
public interface ServerEntity extends IBaseEntity
{
    /**
     * Returns the parent entity.
     * @return Parent entity if set, {@code null} otherwise.
     */
    ServerBaseEntity getParent();

    /**
     * Sets the parent entity.
     * @param parent Parent entity.
     * @throws RuntimeException Thrown to indicate an error occurred when trying to set the parent entity.
     */
    void setParent(final ServerBaseEntity parent) throws RuntimeException;
}
