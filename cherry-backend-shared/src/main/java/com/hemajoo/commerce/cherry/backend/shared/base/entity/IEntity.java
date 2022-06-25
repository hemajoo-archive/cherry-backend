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

import com.hemajoo.commerce.cherry.backend.commons.entity.Identity;
import com.hemajoo.commerce.cherry.backend.commons.entity.IdentityAware;

import java.security.NoSuchAlgorithmException;

/**
 * Defines the behavior of an <b>entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @since Cherry 0.1.0
 * @version 1.0.0
 */
public interface IEntity extends IEntityStatus, Identity, IdentityAware
{
    /**
     * Returns the entity name.
     * @return Entity name.
     */
    String getName();

    /**
     * Sets the entity name.
     * @param name Entity name.
     */
    void setName(final String name);

    /**
     * Returns the entity description.
     * @return Entity description.
     */
    String getDescription();

    /**
     * Sets the entity description.
     * @param description Entity description.
     */
    void setDescription(final String description);

    /**
     * Returns the entity reference.
     * @return Entity reference.
     */
    String getReference();

    /**
     * Sets the entity reference.
     * @param reference Entity reference.
     */
    void setReference(final String reference);

    /**
     * Adds a tag.
     * @param tag Tag.
     */
    void addTag(final String tag);

    /**
     * Removes a tag.
     * @param tag Tag to remove.
     */
    void removeTag(final String tag);

    /**
     * Returns a random tag.
     * @return Tag.
     */
    String getRandomTag() throws NoSuchAlgorithmException;

    /**
     * Checks if the given tag exist.
     * @param tag Tag.
     * @return {@code True} if the tag exist, {@code false} otherwise.
     */
    boolean existTag(final String tag);

    /**
     * Returns the number of tags.
     * @return Number of tags.
     */
    int getTagCount();
}
