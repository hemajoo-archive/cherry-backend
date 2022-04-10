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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.IDocumentServer;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import com.hemajoo.commerce.cherry.backend.shared.document.exception.DocumentException;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.javers.core.metamodel.annotation.DiffIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a server base entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
//@Table(name = "ENTITY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ServerEntity extends AbstractServerStatusEntity implements IServerEntity
{
//    /**
//     * Entity identifier.
//     */
//    @Getter
//    @Setter
//    @Id
//    @Type(type = "uuid-char") // Allow displaying in the DB the UUID as a string instead of a binary field!
//    @GeneratedValue
//    private UUID id;

    /**
     * Entity identifier.
     */
    @DiffIgnore
    @Getter
    @Setter
    @Id
    @Type(type = "uuid-char") // Allow displaying in the DB the UUID as a string instead of a binary field!
    @GenericGenerator(name = "cherry-uuid-gen", strategy = "com.hemajoo.commerce.cherry.backend.persistence.base.entity.UuidGenerator")
    @GeneratedValue(generator = "cherry-uuid-gen")
    private UUID id;

    /**
     * Entity type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ENTITY_TYPE", length = 50)
    private EntityType entityType;

    /**
     * Entity name.
     */
    @Getter
    @Setter
    @Column(name = "NAME")
    private String name;

    /**
     * Entity description.
     */
    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Entity internal reference.
     */
    @Getter
    @Setter
    @Column(name = "REFERENCE", length = 100)
    private String reference;

    /**
     * Tags.
     */
    @Getter
    @Setter
    @Column(name = "TAGS")
    private String tags;

    /**
     * Documents.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private List<DocumentServer> documents = null;

    /**
     * The parent entity.
     */
    @Getter
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties
    @ManyToOne(targetEntity = ServerEntity.class, fetch = FetchType.EAGER)
    private ServerEntity parent;

    /**
     * Parent type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "PARENT_TYPE", length = 50)
    private EntityType parentType;

    /**
     * Creates a new base entity.
     * @param type Entity type.
     */
    protected ServerEntity(final EntityType type)
    {
        this.entityType = type;
    }

    @Override
    public final EntityIdentity getIdentity()
    {
        return EntityIdentity.from(entityType, id);
    }

    @Override
    public void setParent(final ServerEntity parent) throws EntityException
    {
        if (parent == this)
        {
            throw new EntityException("Cannot set itself as parent!");
        }

        this.parent = parent;
        this.parentType = parent != null ? parent.getEntityType() : null;
    }

    @Override
    public final int getDocumentCount()
    {
        return documents.size();
    }

    @JsonIgnore
    @Override
    public List<DocumentServer> getDocuments()
    {
        if (entityType == EntityType.MEDIA)
        {
            return new ArrayList<>();
        }

        return documents != null ? Collections.unmodifiableList(documents) : null;
    }

    @Override
    public final boolean existDocument(final @NonNull IDocumentServer document)
    {
        return existDocument(document.getId());
    }

    @Override
    public final boolean existDocument(final UUID documentId)
    {
        if (documentId != null)
        {
            return documents.stream().anyMatch(doc -> doc.getId().equals(documentId));
        }

        return false; // Random documents do not have a UUID assigned yet by the database!
    }

    @Override
    public final void addDocument(final @NonNull IDocumentServer document) throws DocumentException
    {
        if (entityType == EntityType.DOCUMENT && document.getEntityType() == EntityType.DOCUMENT)
        {
            throw new DocumentException("Cannot add a document to another document!");
        }

        if (documents == null)
        {
            documents = new ArrayList<>();
        }

        if (!existDocument(document))
        {
            documents.add((DocumentServer) document);
            try
            {
                document.setParent(this);
            }
            catch (EntityException e)
            {
                throw new DocumentException(e);
            }
        }
    }

    @Override
    public final void removeDocument(@NonNull IDocumentServer document)
    {
        removeDocument(document.getId());
    }

    @Override
    public final void removeDocument(@NonNull UUID documentId)
    {
        documents.removeIf(doc -> doc.getId().equals(documentId));
    }
}
