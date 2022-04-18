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

import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;

/**
 * Defines the behavior of a client <b>entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IEntityClient extends IEntity
{
    /**
     * Returns the parent entity this entity belongs to.
     * @return Parent entity identity.
     */
    EntityIdentity getParent();

    /**
     * Sets the parent entity identity.
     * @param parent Parent entity identity.
     */
    void setParent(final EntityIdentity parent) throws RuntimeException;
}
