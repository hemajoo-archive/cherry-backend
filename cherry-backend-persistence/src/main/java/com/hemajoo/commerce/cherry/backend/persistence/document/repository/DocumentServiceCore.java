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

import com.hemajoo.commerce.cherry.backend.persistence.base.entity.AbstractServerAuditEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.AbstractServerStatusEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.specification.GenericSpecification;
import com.hemajoo.commerce.cherry.backend.persistence.document.content.DocumentStore;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.backend.shared.base.search.criteria.SearchCriteria;
import com.hemajoo.commerce.cherry.backend.shared.base.search.criteria.SearchOperation;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentSearch;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of the document persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Service
public class DocumentServiceCore implements DocumentService
{
    /**
     * Document repository.
     */
    @Getter
    @Autowired
    private DocumentRepository documentRepository;

    /**
     * Document store.
     */
    @Autowired
    private DocumentStore documentStore;

    /**
     * Entity manager.
     */
    @Getter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentRepository getRepository()
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
        return documentRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public DocumentServer save(DocumentServer document)
    {
        // Save the content file, if one exist and not already saved!
        if (document.getContent() != null && document.getContentId() == null)
        {
            document = (DocumentServer) documentStore.getStore().setContent(document, document.getContent());
        }

        document = documentRepository.save(document);

        return document;
    }

    @Override
    public DocumentServer saveAndFlush(DocumentServer document)
    {
        return documentRepository.saveAndFlush(document);
    }

    @Override
    public void deleteById(UUID id)
    {
        DocumentServer document = findById(id);

        // If a content file is associated, then delete it!
        if (document != null && document.getContentId() != null)
        {
            documentStore.getStore().unsetContent(document);
        }

        documentRepository.deleteById(id);
    }

    @Override
    public List<DocumentServer> findAll() //TODO Should be removed!
    {
        // TODO We should for each document inject its content such as for the findById
        return documentRepository.findAll();
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

        throw new DocumentException(String.format("Cannot find document id.: '%s'", documentId.toString()));
    }

    @Override
    public List<DocumentServer> search(@NonNull DocumentSearch search)
    {
        GenericSpecification<DocumentServer> specification = new GenericSpecification<>();

        // Inherited fields
        if (search.getCreatedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_CREATED_BY,
                    search.getCreatedBy(),
                    SearchOperation.MATCH));
        }

        if (search.getModifiedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_MODIFIED_BY,
                    search.getModifiedBy(),
                    SearchOperation.MATCH));
        }

        if (search.getId() != null)
        {
            specification.add(new SearchCriteria(
                    ServerPersonEntity.FIELD_ID,
                    search.getId(),
                    SearchOperation.EQUAL));
        }

        if (search.getStatusType() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerStatusEntity.FIELD_STATUS_TYPE,
                    search.getStatusType(),
                    SearchOperation.EQUAL));
        }

        if (search.getDescription() != null)
        {
            specification.add(new SearchCriteria(
                    ServerEntity.FIELD_DESCRIPTION,
                    search.getDescription(),
                    SearchOperation.MATCH));
        }

        if (search.getName() != null)
        {
            specification.add(new SearchCriteria(
                    ServerEntity.FIELD_NAME,
                    search.getName(),
                    SearchOperation.MATCH));
        }

        if (search.getExtension() != null)
        {
            specification.add(new SearchCriteria(
                    DocumentServer.FIELD_EXTENSION,
                    search.getExtension(),
                    SearchOperation.MATCH));
        }

        if (search.getFilename() != null)
        {
            specification.add(new SearchCriteria(
                    DocumentServer.FIELD_FILENAME,
                    search.getFilename(),
                    SearchOperation.MATCH));
        }

        if (search.getContentPath() != null)
        {
            specification.add(new SearchCriteria(
                    DocumentServer.FIELD_CONTENT_PATH,
                    search.getContentPath(),
                    SearchOperation.MATCH));
        }

        if (search.getContentLength() != null)
        {
            specification.add(new SearchCriteria(
                    DocumentServer.FIELD_CONTENT_LENGTH,
                    search.getContentLength(),
                    SearchOperation.GREATER_THAN_EQUAL));
        }

        if (search.getMimeType() != null)
        {
            specification.add(new SearchCriteria(
                    DocumentServer.FIELD_MIMETYPE,
                    search.getMimeType(),
                    SearchOperation.MATCH));
        }

        if (search.getTags() != null)
        {
            specification.add(new SearchCriteria(
                    DocumentServer.FIELD_TAGS,
                    search.getTags(),
                    SearchOperation.MATCH));
        }

        return documentRepository.findAll(specification);
    }
}

