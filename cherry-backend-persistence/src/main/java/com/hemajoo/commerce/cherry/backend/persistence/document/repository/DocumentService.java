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
package com.hemajoo.commerce.cherry.backend.persistence.document.repository;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.commons.type.StatusType;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.EntityFactory;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.document.content.DocumentStore;
import com.hemajoo.commerce.cherry.backend.persistence.document.converter.DocumentConverter;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import com.hemajoo.commerce.cherry.backend.shared.base.filter.IEntityFilter;
import com.hemajoo.commerce.cherry.backend.shared.base.query.AbstractStatusQuery;
import com.hemajoo.commerce.cherry.backend.shared.base.query.BaseEntityQuery;
import com.hemajoo.commerce.cherry.backend.shared.base.query.GenericSpecification;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentClient;
import com.hemajoo.commerce.cherry.backend.shared.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.document.filter.DocumentFilterMetadata;
import com.hemajoo.commerce.cherry.backend.shared.document.query.DocumentQuery;
import com.hemajoo.commerce.cherry.backend.shared.document.type.DocumentType;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.javers.core.diff.ListCompareAlgorithm.LEVENSHTEIN_DISTANCE;

/**
 * Implementation of the <b>document</b> persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@Service
public class DocumentService implements IDocumentService
{
    /**
     * Document repository.
     */
    @Getter
    @Autowired
    private IDocumentRepository documentRepository;

    /**
     * Document store.
     */
    @Autowired
    private DocumentStore documentStore;

    /**
     * Object changes detector.
     */
    private final Javers javers = JaversBuilder.javers()
            .withListCompareAlgorithm(LEVENSHTEIN_DISTANCE)
            .build();

    /**
     * Document converter.
     */
    private DocumentConverter converter = new DocumentConverter();

    /**
     * Entity factory.
     */
    @Getter
    @Autowired
    private EntityFactory factory;

    @Override
    public IDocumentRepository getRepository()
    {
        return documentRepository;
    }

    @Override
    public Long count()
    {
        return documentRepository.count();
    }

    @Override
    public DocumentServer findById(UUID id)
    {
        DocumentServer document = documentRepository.findById(id).orElse(null);

        if (document != null)
        {
            loadContent(document);
        }

        return document;
    }

    @Transactional
    @Override
    public DocumentServer update(final @NonNull DocumentServer document) throws EntityException
    {
        return save(document);
    }

    @Override
    public DocumentServer updateMetadata(final @NonNull DocumentClient document) throws EntityException
    {
        DocumentServer serverDocument = documentRepository.findById(document.getId()).orElse(null);
        if (serverDocument == null)
        {
            throw new DocumentException(String.format("Document with id: '%s' not found!", document.getId()), HttpStatus.NOT_FOUND);
        }

        DocumentClient original =  converter.fromServerToClient(serverDocument);

        LOGGER.debug(String.format("%s detecting changes on properties...", document.getIdentity()));
        List<ValueChange> changes = filterDocumentChanges(getPropertyChanges(original, document), DocumentFilterMetadata.build());
        if (!changes.isEmpty())
        {
            applyPropertyChanges(serverDocument, changes);
            serverDocument = documentRepository.save(serverDocument);
            LOGGER.debug(String.format("%s updated successfully", document.getIdentity()));
        }
        else
        {
            LOGGER.debug(String.format("%s no change detected on properties!", document.getIdentity()));
        }

        return serverDocument;
    }

    /**
     * Retrieve the changes on properties between two client documents.
     * @param original Original client document.
     * @param other Other client document.
     * @return List of changes on properties.
     */
    private List<ValueChange> getPropertyChanges(final @NonNull DocumentClient original, final @NonNull DocumentClient other)
    {
        return javers.compare(original, other).getChangesByType(ValueChange.class);
    }

    /**
     * Apply the changes detected on properties to a server document.
     * @param documentServer Server document.
     * @param changes List of changes.
     * @throws EntityException Thrown to indicate an error occurred when trying to connect retrieve an entity.
     * @throws DocumentException Thrown to indicate an error occurred when trying to apply changes on document properties.
     */
    private void applyPropertyChanges(final @NonNull DocumentServer documentServer, final @NonNull List<ValueChange> changes) throws EntityException
    {
        for (ValueChange change : changes)
        {
            switch (change.getPropertyName())
            {
                case DocumentQuery.DOCUMENT_TAGS ->
                        documentServer.setTags((String) change.getRight());

                case DocumentQuery.DOCUMENT_TYPE ->
                        documentServer.setDocumentType((DocumentType) change.getRight());

                case BaseEntityQuery.BASE_DESCRIPTION ->
                        documentServer.setDescription((String) change.getRight());

                case BaseEntityQuery.BASE_NAME ->
                        documentServer.setName((String) change.getRight());

                case BaseEntityQuery.BASE_REFERENCE ->
                        documentServer.setReference((String) change.getRight());

                case AbstractStatusQuery.BASE_STATUS_TYPE ->
                        documentServer.setStatusType((StatusType) change.getRight());

                case BaseEntityQuery.BASE_PARENT_TYPE ->
                        documentServer.setParentType((EntityType) change.getRight());

                case BaseEntityQuery.BASE_PARENT_ID -> {
                    ServerEntity parent = (ServerEntity) factory.from(documentServer.getParentType(), UUID.fromString((String) change.getRight()));
                    documentServer.setParent(parent);
                }

                default -> throw new DocumentException(String.format("Document property change for property name: '%s' is not handled!", change.getPropertyName()));
            }
        }
    }

    /**
     * Filter the changes on properties detected to the metadata part of a document.
     * @param changes List of changes.
     * @return List of filtered changes.
     */
    private List<ValueChange> filterDocumentChanges(final @NonNull List<ValueChange> changes, final @NonNull IEntityFilter filter)
    {
        List<ValueChange> filtered = new ArrayList<>();

        for (ValueChange change : changes)
        {
            if (filter.contains(change.getPropertyName()))
            {
                LOGGER.debug(String.format("-> property name: [%-15.15s], original: [%-50.50s], updated: [%s]", change.getPropertyName(), change.getLeft(), change.getRight()));
                filtered.add(change);
            }
        }

        return filtered;
    }

    @Transactional
    @Override
    public DocumentServer save(DocumentServer document)
    {
        // Save the content file, if one exist and not already saved!
        if (document.getContent() != null && document.getContentId() == null)
        {
            document = (DocumentServer) documentStore.getStore().setContent(document, document.getContent());
            LOGGER.debug(String.format("Successfully saved document content with id: '%s'", document.getId()));
        }

        document = documentRepository.save(document);
        LOGGER.debug(String.format("Successfully saved document with id: '%s'", document.getId()));

        return document;
    }

    @Override
    public DocumentServer saveAndFlush(DocumentServer document)
    {
        return documentRepository.saveAndFlush(document); //TODO What about the content ?
    }

    @Override
    public void deleteById(UUID id) throws DocumentException
    {
        DocumentServer document = findById(id);

        if (document == null)
        {
            throw new DocumentException(String.format("Document with id: '%s' cannot be found!", id.toString()), HttpStatus.NOT_FOUND);
        }

        // If a content file is associated, then delete it!
        if (document.getContentId() != null)
        {
            LOGGER.debug(String.format("Successfully deleted document content with id: '%s'", document.getContentId()));
            documentStore.getStore().unsetContent(document);
        }

        LOGGER.debug(String.format("Successfully deleted document with id: '%s'", document.getId()));
        documentRepository.deleteById(id);
    }

    @Override
    public List<DocumentServer> findAll()
    {
        List<DocumentServer> documents = documentRepository.findAll();
        documents.forEach(this::loadContent);

        return documents;
    }

    @Override
    public List<DocumentServer> findByParentId(final @NonNull String parentId)
    {
        return documentRepository.findByParentId(UUID.fromString(parentId));
    }

    @Override
    public void loadContent(DocumentServer document)
    {
        document.setContent(documentStore.getStore().getContent(document));
    }

    @Override
    public void loadContent(UUID documentId) throws DocumentException
    {
        DocumentServer document = findById(documentId);
        if (document != null)
        {
            loadContent(document);
        }

        throw new DocumentException(String.format("Document with id: '%s' cannot be found!", documentId.toString()), HttpStatus.NOT_FOUND);
    }

    @Override
    public List<DocumentServer> search(@NonNull DocumentQuery search) throws QueryConditionException
    {
        List<DocumentServer> documents;

        GenericSpecification<DocumentServer> specification = (GenericSpecification<DocumentServer>) search.getSpecification();

        documents = documentRepository.findAll(specification);
        documents.forEach(this::loadContent);

        return documents;
    }

//    private DocumentServer merge(final @NonNull DocumentServer source, final @NonNull DocumentServer target) throws DocumentException, EntityException
//    {
//        IServerEntity entity;
//
//        Diff diff = EntityComparator.getJavers().compare(source, target);
//
//        // Check if some object fields have changed?
//        for (ValueChange change : diff.getChangesByType(ValueChange.class))
//        {
//            switch (change.getPropertyName())
//            {
//                case AbstractAuditQuery.BASE_CREATED_DATE:
//                case AbstractAuditQuery.BASE_MODIFIED_DATE:
//                case AbstractAuditQuery.BASE_CREATED_BY:
//                case AbstractAuditQuery.BASE_MODIFIED_BY:
//                    // Do nothing as they are handled by the repository itself!
//                    break;
//
//                case BaseEntityQuery.BASE_DESCRIPTION:
//                    target.setDescription(source.getDescription());
//                    break;
//
//                case BaseEntityQuery.BASE_NAME:
//                    target.setName(source.getName());
//                    break;
//
//                case BaseEntityQuery.BASE_REFERENCE:
//                    target.setReference(source.getReference());
//                    break;
//
//                case AbstractStatusQuery.BASE_STATUS_TYPE:
//                    target.setStatusType(source.getStatusType());
//                    break;
//
//                case BaseEntityQuery.BASE_ENTITY_ID:
//                    throw new DocumentException("Cannot change document entity identifier!");
//
//                case BaseEntityQuery.BASE_ENTITY_TYPE:
//                    throw new DocumentException("Cannot change document entity type!");
//
//                case BaseEntityQuery.BASE_PARENT_TYPE:
//                    target.setParentType(source.getParentType());
//                    break;
//
//                case DocumentQuery.DOCUMENT_TAGS:
//                    target.setTags(source.getTags());
//                    break;
//
//                case DocumentQuery.DOCUMENT_TYPE:
//                    target.setDocumentType(source.getDocumentType());
//                    break;
//
//                    //TODO Handle the document content!
//
//                default:
//                    LOGGER.warn(String.format("Property name: %s not handled!", change.getPropertyName()));
//                    break;
//            }
//        }
//
//        // Check if some object references have changed.
//        for (ReferenceChange change : diff.getChangesByType(ReferenceChange.class))
//        {
//            switch (change.getPropertyName())
//            {
//                case BaseEntityQuery.BASE_PARENT:
//                    entity = factory.from(source.getParent().getEntityType(), source.getParent().getId());
//                    entity.removeDocument(source);
//                    factory.getEntityManager().persist(entity);
//
//                    entity = factory.from(target.getParent().getEntityType(), target.getParent().getId());
//                    entity.addDocument(target);
//                    factory.getEntityManager().persist(entity);
//
//                    source.setParent(source.getParent());
//                    break;
//
//                default:
//                    LOGGER.warn(String.format("Property name: %s not handled!", change.getPropertyName()));
//                    break;
//            }
//        }
//
//        return target;
//    }
}

