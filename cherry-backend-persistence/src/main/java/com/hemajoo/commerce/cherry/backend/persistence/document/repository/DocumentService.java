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

import com.hemajoo.commerce.cherry.backend.persistence.document.content.DocumentStore;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.shared.base.query.GenericSpecification;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentQuery;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

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
     * Entity manager.
     */
    @Getter
    @PersistenceContext
    private EntityManager entityManager;

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
            documentStore.getStore().unsetContent(document);
            LOGGER.debug(String.format("Successfully deleted document content with id: '%s'", document.getContentId()));
        }

        documentRepository.deleteById(id);
        LOGGER.debug(String.format("Successfully deleted document with id: '%s'", document.getId()));
    }

    @Override
    public List<DocumentServer> findAll()
    {
        List<DocumentServer> documents = documentRepository.findAll();
        documents.forEach(this::loadContent);

        return documents;
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
}

