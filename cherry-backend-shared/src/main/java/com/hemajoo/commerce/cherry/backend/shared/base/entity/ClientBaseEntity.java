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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a <b>base client entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class ClientBaseEntity extends AbstractClientStatusEntity implements IClientEntity
{
    /**
     * Entity identifier.
     */
    @Getter
    @Setter
    @JsonProperty("uuid")
    @Schema(name = "id", description = "Identifier")
    private UUID id;

    /**
     * Entity type.
     */
    @Setter
    @JsonProperty("type")
    @Schema(name = "entityType", description = "Entity type", example = "PERSON")
    private EntityType entityType;

    /**
     * Entity name.
     */
    @Getter
    @Setter
    @JsonProperty("name")
    @Schema(name = "name", description = "Name")
    private String name;

    /**
     * Entity description.
     */
    @Getter
    @Setter
    @JsonProperty("description")
    @Schema(name = "description", description = "Description")
    private String description;

    /**
     * Entity reference.
     */
    @Getter
    @Setter
    @JsonProperty("reference")
    @Schema(name = "reference", description = "Reference")
    private String reference;

    /**
     * Entity documents.
     */
    @Setter
    @JsonProperty("documents")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(hidden = true)
    private List<EntityIdentity> documents = new ArrayList<>();

    /**
     * The entity (parent) this entity (child) belongs to.
     */
    @Getter
    @Setter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EntityIdentity parent;

    /**
     * Creates a new base client entity.
     * @param type Entity type.
     */
    protected ClientBaseEntity(final EntityType type)
    {
        this.entityType = type;
    }

    @Override
    public final EntityType getEntityType()
    {
        return entityType;
    }

    @Override
    public final EntityIdentity getIdentity()
    {
        return new EntityIdentity(id, entityType);
    }

    /**
     * Adds a document to this entityDocumentEntity.
     * @param document Document.
     */
    public final void addDocument(final @NonNull EntityIdentity document)
    {
        documents.add(document);
    }

    /**
     * Returns the documents associated with this entity.
     * @return List of documents.
     */
    @JsonIgnore
    public List<EntityIdentity> getDocuments()
    {
        if (entityType == EntityType.DOCUMENT)
        {
            return new ArrayList<>();
        }

        return Collections.unmodifiableList(documents);
    }
}
