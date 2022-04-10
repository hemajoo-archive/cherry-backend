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

import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.IDocumentServer;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.IEntity;
import com.hemajoo.commerce.cherry.backend.shared.document.exception.DocumentException;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Defines the behavior of a <b>server entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @since Cherry 0.1.0
 * @version 1.0.0
 */
public interface IServerEntity extends IEntity
{
    /**
     * Returns the parent entity.
     * @return Parent entity if set, {@code null} otherwise.
     */
    ServerEntity getParent();

    /**
     * Sets the parent entity.
     * @param parent Parent entity.
     * @throws EntityException Thrown to indicate an error occurred when trying to set the parent entity.
     */
    void setParent(final ServerEntity parent) throws EntityException;

    /**
     * Return the number of documents this entity holds.
     * @return Number of documents.
     */
    int getDocumentCount();

    /**
     * Return the list of documents this entity holds.
     * @return List of documents.
     */
    List<DocumentServer> getDocuments();

    /**
     * Add a document.
     * @param document Document to add.
     */
    void addDocument(final @NonNull IDocumentServer document) throws DocumentException;

    /**
     * Check if the given document exist in the list of this entity documents?
     * @param document Document to check.
     * @return {@code True} if the document exist, {@code false} otherwise.
     */
    boolean existDocument(final @NonNull IDocumentServer document);

    /**
     * Check if the given document identifier exist in the list of this entity documents?
     * @param documentId Document identifier to check.
     * @return {@code True} if the document identifier exist, {@code false} otherwise.
     */
    boolean existDocument(final UUID documentId);

    /**
     * Remove a document from the list of this entity documents.
     * @param document Document to remove.
     */
    void removeDocument(final @NonNull IDocumentServer document);

    /**
     * Remove a document from the list of this entity documents.
     * @param documentId Document identifier to remove.
     */
    void removeDocument(final @NonNull UUID documentId);
}
