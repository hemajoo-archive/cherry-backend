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
package com.hemajoo.commerce.cherry.backend.rest.controller.document;

import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.commons.type.StatusType;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.EntityFactory;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.IServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.backend.persistence.document.converter.DocumentConverter;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentClient;
import com.hemajoo.commerce.cherry.backend.shared.document.exception.DocumentContentException;
import com.hemajoo.commerce.cherry.backend.shared.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.document.query.DocumentQuery;
import com.hemajoo.commerce.cherry.backend.shared.document.type.DocumentType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * <b>REST controller</b> exposing endpoints to manage the document entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Tag(name = "Document REST controller", description = "Set of REST-API endpoints to manage the document entities.")
@Validated
@RestController
@RequestMapping("/api/v1/document")
public class DocumentController
{
    /**
     * Document service.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    /**
     * Entity factory.
     */
    @Autowired
    private EntityFactory factory;

    /**
     * Document converter.
     */
    @Autowired
    private DocumentConverter converterDocument;

    /**
     * Service to count the number of documents.
     * @return Number of documents.
     */
    @Operation(summary = "Count the documents.", description = "Count the total number of documents.", tags = { "Count endpoints" })
    @GetMapping("/count")
    public long count()
    {
        return servicePerson.getDocumentService().count();
    }

    /**
     * Retrieve a document given its identifier.
     * @param id Document identifier.
     * @return Document matching the given document identifier.
     * @throws DocumentException Thrown to indicate an error occurred when trying to retrieve a document.
     */
    @Operation(summary = "Retrieve a document.", description = "Retrieve a document information given its identifier.", tags = { "Get endpoints" })
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentClient> get(
            @Parameter(description = "Document identifier", required = true)
            @NotNull @PathVariable String id) throws DocumentException
    {
        DocumentServer document = servicePerson.getDocumentService().findById(UUID.fromString(id));

        if (document == null)
        {
            throw new DocumentException(String.format("Document with id: '%s' cannot be found!", id), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(new DocumentConverter().fromServerToClient(document));
    }

    /**
     * Create a random document for the given parent entity.
     * @param parentType Parent entity type.
     * @param parentId Parent entity identifier.
     * @return Response.
     * @throws DocumentContentException Thrown to indicate an error occurred while trying to create a random document.
     */
    @Operation(summary = "Create a new random document.", description = "Create a new random document given the parent entity this document will belong to.", tags = { "Create endpoints" })
    @PostMapping("/random")
    public ResponseEntity<String> random(
            @Parameter(name = "parentType", description = "Parent entity type", required = true)
            @NotNull @RequestParam EntityType parentType,
            @Parameter(name = "parentId", description = "Parent entity identifier (UUID)", required = true)
            @NotNull @RequestParam UUID parentId) throws DocumentException
    {
        IServerEntity parent;

        EntityIdentity identity = EntityIdentity.from(parentType, parentId);

        try
        {
            parent = factory.from(identity);
            if (parent == null)
            {
                return new ResponseEntity<>(String.format("Parent entity: '%s' not found!", identity), HttpStatus.NOT_FOUND);
            }

            DocumentServer document = DocumentRandomizer.generateServerEntity(false);
            document.setParent((ServerEntity) parent);
            document = servicePerson.getDocumentService().save(document);

            return ResponseEntity.ok(String.format("Saved document with id: '%s', with content id: '%s' and parent set to: '%s'", document.getId(), document.getContentId(), parent.getIdentity()));
        }
        catch (EntityException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update a document metadata.
     * @param documentId Document identifier to update.
     * @param document Document (metadata) to update.
     * @return Response.
     * @throws EntityException Thrown to indicate an error occurred while trying to update a document metadata.
     */
    @Operation(summary = "Update a document metadata", description = "Update a document metadata information.", tags = { "Update endpoints" })
    @PutMapping("/update/metadata/{documentId}")
    public ResponseEntity<String> updateMetadata(
            @Parameter(name = "documentId", description = "Document identifier (UUID)", required = true)
            @PathVariable String documentId,
            @RequestBody DocumentClient document) throws EntityException
    {
        document.setId(UUID.fromString(documentId));

        servicePerson.getDocumentService().updateMetadata(document);

        return ResponseEntity.ok(String.format("%s metadata updated successfully", document.getIdentity()));
    }

    /**
     * Update a document content information.
     * @param documentId Document identifier to update.
     * @return Response.
     * @throws EntityException Thrown to indicate an error occurred while trying to update a document content.
     */
    @Operation(summary = "Update a document content", description = "Update a document metadata information.", tags = { "Update endpoints" })
    @PutMapping(path = "/update/content/{documentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateContent(
            @RequestPart("file") MultipartFile file,
            @Parameter(name = "documentId", description = "Document identifier (UUID)", required = true)
            @PathVariable UUID documentId) throws EntityException
    {
        DocumentServer document = servicePerson.getDocumentService().findById(documentId);
        if (document == null)
        {
            return new ResponseEntity<>(String.format("%s not found!", EntityIdentity.from(EntityType.DOCUMENT, documentId)), HttpStatus.NOT_FOUND);
        }

        try
        {
            servicePerson.getDocumentService().updateContent(document, file);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(String.format("%s content set: '%s'", document.getIdentity(), document.getContentId()));
    }

    /**
     * Update a document parent.
     * @param documentId Document identifier to update.
     * @param parentType Parent entity type.
     * @param parentId Parent entity identifier.
     * @return Response.
     * @throws EntityException Thrown to indicate an error occurred while trying to update a document parent.
     */
    @Operation(summary = "Update a document parent", description = "Update a document parent.", tags = { "Update endpoints" })
    @PutMapping("/update/parent/{documentId}")
    public ResponseEntity<String> updateParent(
            @Parameter(name = "documentId", description = "Document identifier (UUID)", required = true)
            @PathVariable UUID documentId,
            @Parameter(name = "parentType", description = "Parent entity type.", required = true)
            @RequestParam EntityType parentType,
            @Parameter(name = "parentId", description = "Parent entity identifier (UUID)", required = true)
            @RequestParam UUID parentId) throws EntityException
    {
        EntityIdentity identity = EntityIdentity.from(parentType, parentId);

        IServerEntity parent = factory.from(identity);
        if (parent == null)
        {
            return new ResponseEntity<>(String.format("%s not found!", identity), HttpStatus.NOT_FOUND);
        }

        DocumentServer document = servicePerson.getDocumentService().findById(documentId);
        if (document == null)
        {
            return new ResponseEntity<>(String.format("Document with id: '%s' not found!", documentId), HttpStatus.NOT_FOUND);
        }

        document.setParent((ServerEntity) parent);
        servicePerson.getDocumentService().save(document);

        return ResponseEntity.ok(String.format("%s has parent set to: %s", document.getIdentity(), parent.getIdentity()));
    }

    /**
     * Delete a document given its identifier.
     * @param documentId Document identifier.
     * @return Response.
     * @throws EntityException Thrown to indicate an error occurred when trying to delete a document.
     */
    @Operation(summary = "Delete a document.", description = "Delete a document given its identifier.", tags = { "Delete endpoints" })
    @DeleteMapping("/delete/{documentId}")
    public ResponseEntity<String> delete(
            @Parameter(name = "documentId", description = "Document identifier (UUID)", required = true)
            @NotNull @PathVariable String documentId) throws EntityException
    {
        servicePerson.getDocumentService().deleteById(UUID.fromString(documentId));

        return ResponseEntity.ok(String.format("Document id: '%s' deleted successfully!", documentId));
    }

    /**
     * Upload a new document and its content.
     * @param file File to upload.
     * @param name Document name (if blank, filename will be used).
     * @param description Document description (informative) and not mandatory.
     * @param reference Document reference (internal) and not mandatory.
     * @param tags Document tags (comma separated list of tags) and not mandatory.
     * @param parentId Parent entity identifier this document belongs to.
     * @param parentType Parent entity type.
     * @return Response message.
     * @throws DocumentException Thrown to indicate an error occurred with the document type specified.
     */
    @Operation(summary = "Upload a document.", description = "Upload a document and its content as a multipart file.", tags = { "Upload endpoints" })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String description,
                                         @RequestParam(required = false) String reference,
                                         @RequestParam(required = false) String tags,
                                         @RequestParam DocumentType documentType,
                                         @RequestParam EntityType parentType,
                                         @NotNull @RequestParam String parentId) throws EntityException
    {
        DocumentServer document = new DocumentServer();

        if (file.isEmpty())
        {
            return new ResponseEntity<>("You must select a file!", HttpStatus.BAD_REQUEST);
        }

        if (documentType == DocumentType.UNKNOWN)
        {
            throw new DocumentException(String.format("Document type: '%s' is invalid!", DocumentType.UNKNOWN), HttpStatus.BAD_REQUEST);
        }

        if (parentType == EntityType.DOCUMENT)
        {
            throw new DocumentException("Cannot set a document entity as parent!", HttpStatus.BAD_REQUEST);
        }

        IServerEntity parent = factory.from(parentType, UUID.fromString(parentId));
        if (parent == null)
        {
            throw new EntityException(String.format("Parent entity with type: '%s', with id: '%s' cannot be found!", parentType, parentId), HttpStatus.NOT_FOUND);
        }

        try
        {
            document.setName(name != null ? name : file.getName());
            document.setDescription(description);
            document.setReference(reference);
            document.setTags(tags);
            document.setDocumentType(documentType);
            document.setStatusType(StatusType.ACTIVE);
            document.setParent((ServerEntity) parent);

            document = servicePerson.getDocumentService().uploadContent(document, file);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(String.format("Document: %s created and content set to: '%s' uploaded", document.getIdentity(), document.getContentId()), HttpStatus.OK);
    }

    /**
     * Retrieve the documents belonging to the given parent entity.
     * @param parentId Parent entity identifier.
     * @return List of matching documents.
     * @throws EntityException Thrown to indicate an error occurred while retrieving the parent's documents
     */
    @Operation(summary = "Retrieve the documents of a parent entity.", description = "Retrieve the documents belonging to a given parent entity identifier.")
    @GetMapping(value = "/parent/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentClient>> getParentDocuments(
            @Parameter(name = "parentId", description = "Parent entity identifier (UUID).", required = true)
            @PathVariable UUID parentId) throws EntityException
    {
        List<DocumentClient> list = servicePerson.getDocumentService().findByParentId(parentId)
                .stream()
                .map(element -> converterDocument.fromServerToClient(element))
                .toList();

        return ResponseEntity.ok(list);
    }

    /**
     * Search for documents matching the given query conditions.
     * @param query Document query object.
     * @return List of matching documents.
     * @throws QueryConditionException Thrown to indicate an error occurred while searching for documents.
     */
    @Operation(summary = "Search for documents", description = "Search for documents matching the query conditions.")
    @PatchMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // PATCH method Because a GET method cannot have a request body!
    public ResponseEntity<List<DocumentClient>> search(final @RequestBody @NotNull DocumentQuery query) throws QueryConditionException
    {
        query.validate();

        List<DocumentClient> list = servicePerson.getDocumentService().search(query)
                .stream()
                .map(element -> converterDocument.fromServerToClient(element))
                .toList();

        return ResponseEntity.ok(list);
    }

    /**
     * Query for documents matching the given query conditions.
     * @param query Document query object.
     * @return List of matching documents.
     * @throws QueryConditionException Thrown to indicate an error occurred while querying for documents.
     */
    @Operation(summary = "Query for documents", description = "Query for documents matching a given query object containing conditions.", tags = { "Query endpoints" })
    @PatchMapping(value = "/query", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) // PATCH method Because a GET method cannot have a request body!
    public ResponseEntity<List<EntityIdentity>> query(final @RequestBody @NotNull DocumentQuery query) throws QueryConditionException
    {
        query.validate();

        List<EntityIdentity> list = servicePerson.getDocumentService().search(query)
                .stream()
                .map(element -> converterDocument.fromServerToIdentity(element))
                .toList();

        return ResponseEntity.ok(list);
    }
}
