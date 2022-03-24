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
package com.hemajoo.commerce.cherry.backend.shared.person.phone;

import com.hemajoo.commerce.cherry.backend.commons.entity.Identity;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.IClientEntity;

/**
 * Defines the behavior of a <b>client phone number</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IClientPhoneNumber extends IPhoneNumber, IClientEntity
{
    /**
     * Returns the entity identity owning this phone number.
     * @return Entity identity.
     */
    Identity getOwner();

    /**
     * Sets the entity identity owning this phone number.
     * @param owner Entity identity.
     */
    void setOwner(final Identity owner);
}
