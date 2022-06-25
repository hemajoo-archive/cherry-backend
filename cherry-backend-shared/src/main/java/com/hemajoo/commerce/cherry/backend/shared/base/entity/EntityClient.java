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
import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Represents a <b>base client entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class EntityClient extends AbstractEntityStatusClient implements IEntityClient
{
    /**
     * Entity identifier.
     */
    @Getter
    @Setter
    //@JsonProperty("uuid")
    @Schema(name = "id", description = "Entity identifier.")
    private UUID id;

    /**
     * Entity type.
     */
    @Setter
    //@JsonProperty("entityType")
    @Schema(hidden = true)
    private EntityType entityType;

    /**
     * Entity name.
     */
    @Getter
    @Setter
    //@JsonProperty("name")
    @Schema(name = "name", description = "Entity name.")
    private String name;

    /**
     * Entity description.
     */
    @Getter
    @Setter
    //@JsonProperty("description")
    @Schema(name = "description", description = "Entity description.")
    private String description;

    /**
     * Entity reference.
     */
    @Getter
    @Setter
    //@JsonProperty("reference")
    @Schema(name = "reference", description = "Entity reference.")
    private String reference;

    /**5
     * Tags.
     */
    @Getter
    @Setter
    @Schema(name = "tags", description = "Entity tags.")
    private String tags;

    /**
     * Entity documents.
     */
    @Setter
    //@JsonProperty("documents")
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
    protected EntityClient(final EntityType type)
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
        return new EntityIdentity(entityType, id);
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

    @Override
    public final void addTag(final @NonNull String tag)
    {
        if (convertTagAsList().stream().noneMatch(element -> element.equals(tag)))
        {
            tags = tags.isEmpty() ? tag : tags + ", " + tag;
        }
    }

    @Override
    public final void removeTag(final @NonNull String tag)
    {
        List<String> sourceTags = convertTagAsList();
        List<String> targetTags = new ArrayList<>();

        for (String element : sourceTags)
        {
            if (!element.equals(tag))
            {
                targetTags.add(element);
            }
        }

        setTags(convertTagAsString(targetTags));
    }

    @Override
    public final String getRandomTag() throws NoSuchAlgorithmException
    {
        List<String> tagList = convertTagAsList();

        if (tagList.isEmpty())
        {
            return null;
        }

        int index = SecureRandom.getInstanceStrong().nextInt(tagList.size() + 1);

        return tagList.get(index);
    }

    @Override
    public final boolean existTag(final @NonNull String tag)
    {
        return convertTagAsList().stream().anyMatch(element -> element.equals(tag));
    }

    @Override
    public final int getTagCount()
    {
        return convertTagAsList().size();
    }

    /**
     * Converts a string of tags (separated by comma) to a list of tags.
     * @return List of tags.
     */
    private List<String> convertTagAsList()
    {
        if (tags.isEmpty())
        {
            return new ArrayList<>();
        }

        return Arrays.asList(tags.split(",", -1));
    }

    /**
     * Converts a list of tags to a string of tags (separated by comma).
     * @return String of tags.
     */
    private String convertTagAsString(final List<String> tagList)
    {
        StringBuilder builder = new StringBuilder();

        for (String tag : tagList)
        {
            if (builder.length() == 0)
            {
                builder.append(tag);
            }
            else
            {
                builder.append(", ").append(tag);
            }
        }

        return builder.toString();
    }
}
