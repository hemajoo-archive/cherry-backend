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

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.commons.type.StatusType;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.EntityFactory;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.IServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.backend.persistence.document.converter.DocumentConverter;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PersonServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.validation.constraint.ValidPersonId;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentClient;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentContentException;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * REST controller providing service endpoints to manage the document entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Tag(name = "Document")
@Validated
@RestController
@RequestMapping("/api/v1/document")
public class DocumentController
{
    private static final Tika TIKA = new Tika();

    /**
     * Document service.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    @Autowired
    private EntityFactory factory;

    /**
     * Service to count the number of documents.
     * @return Number of documents.
     */
    @Operation(summary = "Count the total number of documents")
    @GetMapping("/count")
    public long count()
    {
        return servicePerson.getDocumentService().count();
    }

    /**
     * Retrieves a document given its identifier.
     * @param id Document identifier.
     * @return Document matching the given identifier.
     * @throws DocumentException Thrown to indicate an error occurred when trying to retrieve a document.
     */
    @Operation(summary = "Retrieve a document")
    @GetMapping("/get/{id}")
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
     * Service to create a random document.
     * @param personId Person identifier being the parent of the document to create.
     * @return Response.
     * @throws DocumentContentException Thrown to indicate an error occurred while trying to create a random document.
     */
    @Operation(summary = "Create a new random document")
    @PostMapping("/random")
    public ResponseEntity<String> random(
            @Parameter(name = "personId", description = "Person identifier (UUID) being the parent of the new document", required = true)
            @Valid @ValidPersonId @NotNull @RequestParam String personId) throws DocumentException
    {
        DocumentServer document = DocumentRandomizer.generateServerEntity(false);

        PersonServer person = servicePerson.getPersonService().findById(UUID.fromString(personId));
        document.setParent(person);
        document = servicePerson.getDocumentService().save(document);

        return ResponseEntity.ok(String.format("Successfully saved document with id: '%s', with content id: '%s'", document.getId(), document.getContentId()));
    }

    /**
     * Deletes a document given its identifier.
     * @param id Document identifier.
     * @return Response.
     * @throws DocumentException Thrown to indicate an error occurred when trying to delete a document.
     */
    @Operation(summary = "Delete a document")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "Document identifier (UUID)", required = true)
            @NotNull @PathVariable String id) throws DocumentException
    {
        servicePerson.getDocumentService().deleteById(UUID.fromString(id));

        return ResponseEntity.ok(String.format("Document with identifier: '%s' has been deleted successfully!", id));
    }

    /**
     * Upload a new document with a content.
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
    @Operation(summary = "Upload a document")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String description,
                                         @RequestParam(required = false) String reference,
                                         @RequestParam(required = false) String tags,
                                         @RequestParam DocumentType documentType,
                                         @RequestParam EntityType parentType,
                                         @NotNull @RequestParam String parentId) throws DocumentException, EntityException
    {
        DocumentServer document;

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

        try
        {
            document = uploadDocument(file); // Upload document content
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        IServerEntity parent = factory.from(parentType, UUID.fromString(parentId));
        if (parent == null)
        {
            throw new EntityException(String.format("Parent entity with type: '%s', with id: '%s' cannot be found!", parentType, parentId), HttpStatus.NOT_FOUND);
        }

        document.setName(name != null ? name : file.getName());
        document.setDescription(description);
        document.setReference(reference);
        document.setTags(tags);
        document.setDocumentType(documentType);
        document.setStatusType(StatusType.ACTIVE);
        document.setParent((ServerEntity) parent);
        servicePerson.getDocumentService().save(document);

        String message = String.format("Successfully uploaded file: '%s', as document with id: '%s', with content id: '%s'", file.getOriginalFilename(), document.getId(), document.getContentId());
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
    }

    private DocumentServer uploadDocument(MultipartFile file) throws IOException
    {
        DocumentServer document = new DocumentServer();

        InputStream stream = new ByteArrayInputStream(file.getBytes());

        document.setContent(stream);
        document.setContentLength(file.getBytes().length);
        document.setFilename(file.getOriginalFilename());
        document.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        document.setMimeType(TIKA.detect(stream));

        stream.close();

        return document;
    }
}
