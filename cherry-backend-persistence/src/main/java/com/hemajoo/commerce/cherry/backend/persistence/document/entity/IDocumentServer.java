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
package com.hemajoo.commerce.cherry.backend.persistence.document.entity;

import com.hemajoo.commerce.cherry.backend.persistence.base.entity.IServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.shared.document.IDocument;

/**
 * Behavior of a server document entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IDocumentServer extends IDocument, IServerEntity
{
    /**
     * Returns the owner entity of this document.
     * @param <T> Type of the owner.
     * @return Owner entity.
     */
    <T extends ServerEntity> T getOwner();

    /**
     * Sets the owner entity of this document.
     * @param <T> Type of the owner.
     * @param owner Owner entity.
     */
    <T extends ServerEntity> void setOwner(final T owner);
}
