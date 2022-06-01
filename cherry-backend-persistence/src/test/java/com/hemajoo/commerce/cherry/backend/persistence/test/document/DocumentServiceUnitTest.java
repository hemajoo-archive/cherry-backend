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
package com.hemajoo.commerce.cherry.backend.persistence.test.document;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hemajoo.commerce.cherry.backend.commons.type.StatusType;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.test.base.AbstractPostgresUnitTest;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryCondition;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryOperatorType;
import com.hemajoo.commerce.cherry.backend.shared.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.document.query.DocumentQuery;
import com.hemajoo.commerce.cherry.backend.shared.document.type.DocumentType;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressQuery;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the <b>document</b> service class.
 * <br>
 * The <b>persistence</b> module unit tests are all executed against a <b>PostgresSQL</b> database contained in a <b>Docker container</b>, so the database instance is
 * container in the <b>Docker image</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@DirtiesContext
@Testcontainers // Not to be used to keep container alive after the tests!
@SpringBootTest
@Log4j2
class DocumentServiceUnitTest extends AbstractPostgresUnitTest
{
    /**
     * Date time formatter.
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z");

    /**
     * Person services.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    /**
     * Prepare before each test.
     * @throws DocumentException Thrown to indicate an error occurred when trying to randomly generate test documents.
     */
    @BeforeEach
    public void beforeEach() throws DocumentException
    {
        // Not using a @TestInstance(TestInstance.Lifecycle.PER_CLASS) and an associated beforeAll / afterAll because of a bug with the Spring context!
        DocumentServer document;

        // Generate 100 test documents
        for (int i = 0; i < 100; i++)
        {
            document = servicePerson.getDocumentService().save(DocumentRandomizer.generateServerEntity(false));
        }
    }

    /**
     * Cleanup after each test.
     * @throws DocumentException Thrown to indicate an error occurred when trying to randomly generate test documents.
     */
    @AfterEach
    public void afterEach() throws DocumentException
    {
        for (DocumentServer document : servicePerson.getDocumentService().findAll())
        {
            try
            {
                servicePerson.getDocumentService().deleteById(document.getId());
            }
            catch (EmptyResultDataAccessException e)
            {
                // Do nothing!
            }
        }
    }

    @Test
    @DisplayName("Create a document")
    void testCreateDocument() throws DocumentException
    {
        DocumentServer document = servicePerson.getDocumentService().save(DocumentRandomizer.generateServerEntity(false));

        assertThat(document)
                .as("Document should not be null!")
                .isNotNull();

        assertThat(document.getId())
                .as("Document identifier should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Ensure we cannot set a document as being the parent of another document")
    void testSetDocumentAsParentOfDocument() throws DocumentException
    {
        DocumentServer parent = servicePerson.getDocumentService().save(DocumentRandomizer.generateServerEntity(false));
        DocumentServer child = servicePerson.getDocumentService().save(DocumentRandomizer.generateServerEntity(false));

        assertThat(parent)
                .as("Parent document should not be null!")
                .isNotNull();
        assertThat(parent.getId())
                .as("Parent document identifier should not be null!")
                .isNotNull();
        assertThat(child)
                .as("Child document should not be null!")
                .isNotNull();
        assertThat(child.getId())
                .as("Child document identifier should not be null!")
                .isNotNull();

        assertThrows(DocumentException.class, () -> {
            parent.addDocument(child);
        });
    }

    @Test
    @DisplayName("Update a document")
    void testUpdateDocument() throws DocumentException
    {
        DocumentServer document = servicePerson.getDocumentService().save(DocumentRandomizer.generateServerEntity(false));

        assertThat(document)
                .as("Document should not be null!")
                .isNotNull();

        assertThat(document.getId())
                .as("Document identifier should not be null!")
                .isNotNull();

        String description = document.getDescription();
        document.setDescription("Test description for document: " + document.getId());
        servicePerson.getDocumentService().saveAndFlush(document);

        DocumentServer updated = servicePerson.getDocumentService().findById(document.getId());

        assertThat(updated)
                .as("Document should not be null!")
                .isNotNull();

        assertThat(updated.getDescription())
                .as("Documents description should be different!")
                .isNotEqualTo(description);
    }

    @Test
    @DisplayName("Delete a document")
    void testDeleteDocument() throws DocumentException
    {
        DocumentServer document = servicePerson.getDocumentService().save(DocumentRandomizer.generateServerEntity(false));

        assertThat(document)
                .as("Document should not be null!")
                .isNotNull();

        assertThat(document.getId())
                .as("Document identifier should not be null!")
                .isNotNull();

        servicePerson.getDocumentService().deleteById(document.getId());

        assertThat(servicePerson.getDocumentService().findById(document.getId()))
                .as("Document should be null!")
                .isNull();
    }

    @Test
    @DisplayName("Create an invalid search criteria")
    void testCreateInvalidSearchCriteria()
    {
        try
        {
            new DocumentQuery().addCondition(QueryCondition.builder()
                    .withField(EmailAddressQuery.EMAIL_ADDRESS_EMAIL)
                    .withValue("john.doe@gmail.com")
                    .withOperator(QueryOperatorType.EQUAL)
                    .build());
            Assertions.fail(String.format("Should have raised an: '%s'", QueryConditionException.class));
        }
        catch (QueryConditionException e)
        {
            // Do nothing!
        }
    }

    @Test
    @DisplayName("Query documents by their extension equal to")
    void testQueryDocumentByExtensionEqualTo() throws QueryConditionException, JsonProcessingException
    {
        final String DOCUMENT_EXTENSION = "jpg";

        DocumentQuery search = new DocumentQuery()
                .addCondition(QueryCondition.builder()
                        .withField(DocumentQuery.DOCUMENT_EXTENSION)
                        .withValue(DOCUMENT_EXTENSION)
                        .withOperator(QueryOperatorType.EQUAL)
                        .build())
                .addCondition(QueryCondition.builder()
                        .withField(DocumentQuery.DOCUMENT_CONTENT_LENGTH)
                        .withValue(0L)
                        .withValue(50000L)
                        .withOperator(QueryOperatorType.BETWEEN)
                        .build());

        List<DocumentServer> documents = servicePerson.getDocumentService().search(search);
        assertThat(documents)
                .as("Document list should not be empty!")
                .isNotEmpty();

        for (DocumentServer document : documents)
        {
            assertThat(document.getExtension())
                    .as(String.format("Document extension should be: '%s'", DOCUMENT_EXTENSION))
                    .isEqualTo(DOCUMENT_EXTENSION);
            assertThat(document.getContentId())
                    .as("Document content id should not be null!")
                    .isNotNull();
            assertThat(document.getContent())
                    .as("Document content should not be null!")
                    .isNotNull();
        }
    }

    @Test
    @DisplayName("Query documents by mime type equal to")
    void testQueryDocumentByMimeTypeEqualTo() throws QueryConditionException
    {
        final String DOCUMENT_MIMETYPE = "text/plain";

        DocumentQuery search = new DocumentQuery()
                .addCondition(QueryCondition.builder()
                        .withField(DocumentQuery.DOCUMENT_MIMETYPE)
                        .withValue(DOCUMENT_MIMETYPE)
                        .withOperator(QueryOperatorType.EQUAL)
                        .build());

        List<DocumentServer> documents = servicePerson.getDocumentService().search(search);
        assertThat(documents)
                .as("Document list should not be empty!")
                .isNotEmpty();

        for (DocumentServer document : documents)
        {
            assertThat(document.getMimeType())
                    .as(String.format("Document mime type should be: '%s'", DOCUMENT_MIMETYPE))
                    .isEqualTo(DOCUMENT_MIMETYPE);
            assertThat(document.getContentId())
                    .as("Document content id should not be null!")
                    .isNotNull();
            assertThat(document.getContent())
                    .as("Document content should not be null!")
                    .isNotNull();
        }
    }

    @Test
    @DisplayName("Query documents by inactive date between")
    void testQueryDocumentByInactiveDateBetween() throws QueryConditionException
    {
        final ZonedDateTime DOCUMENT_DATE_LOW = ZonedDateTime.parse("2000-08-21 00:00:00.001 Europe/Paris", formatter);
        final Instant DOCUMENT_DATE_HIGH = Instant.now();

        DocumentQuery search = new DocumentQuery()
                .addCondition(QueryCondition.builder()
                        .withField(DocumentQuery.BASE_SINCE)
                        .withValue(Date.from(DOCUMENT_DATE_LOW.toInstant()))
                        .withValue(Date.from(DOCUMENT_DATE_HIGH))
                        .withOperator(QueryOperatorType.BETWEEN)
                        .build());

        List<DocumentServer> documents = servicePerson.getDocumentService().search(search);
        assertThat(documents)
                .as("Document list should not be empty!")
                .isNotEmpty();

        for (DocumentServer document : documents)
        {
            assertThat(document.getStatusType())
                    .as(String.format("Document status type should be: '%s'", StatusType.INACTIVE))
                    .isEqualTo(StatusType.INACTIVE);
            assertThat(document.getSince())
                    .as(String.format("Document inactive (since) date should be greater or equal to: '%s' and less or equal to: '%s'", DOCUMENT_DATE_LOW, DOCUMENT_DATE_HIGH))
                    .isBetween(Date.from(DOCUMENT_DATE_LOW.toInstant()), Date.from(DOCUMENT_DATE_HIGH));
        }
    }

    @Test
    @DisplayName("Query documents by filename matching")
    void testQueryDocumentByFilenameMatching() throws QueryConditionException
    {
        final String FILENAME_PATTERN = "license";

        DocumentQuery search = new DocumentQuery()
                .addCondition(QueryCondition.builder()
                        .withField(DocumentQuery.DOCUMENT_FILENAME)
                        .withValue(FILENAME_PATTERN)
                        .withOperator(QueryOperatorType.CONTAINS)
                        .build());

        List<DocumentServer> documents = servicePerson.getDocumentService().search(search);
        assertThat(documents)
                .as("Document list should not be empty!")
                .isNotEmpty();

        for (DocumentServer document : documents)
        {
            assertThat(document.getFilename())
                    .as(String.format("Document filename: '%s' should contain: '%s'", document.getFilename(), FILENAME_PATTERN))
                    .contains(FILENAME_PATTERN);
        }
    }

    @Test
    @DisplayName("Query documents by content length between")
    void testQueryDocumentByContentLengthBetween() throws QueryConditionException
    {
        final Long CONTENT_LENGTH_LOW = 34000L;
        final Long CONTENT_LENGTH_HIGH = 35000L;

        DocumentQuery search = new DocumentQuery()
                .addCondition(QueryCondition.builder()
                        .withField(DocumentQuery.DOCUMENT_CONTENT_LENGTH)
                        .withValue(CONTENT_LENGTH_LOW)
                        .withValue(CONTENT_LENGTH_HIGH)
                        .withOperator(QueryOperatorType.BETWEEN)
                        .build());

        List<DocumentServer> documents = servicePerson.getDocumentService().search(search);
        assertThat(documents)
                .as("Document list should not be empty!")
                .isNotEmpty();

        for (DocumentServer document : documents)
        {
            assertThat(document.getContentLength())
                    .as(String.format("Document content length: '%s' should be greater or equal to: '%s' and less or equal to: '%s'", document.getContentLength(), CONTENT_LENGTH_LOW, CONTENT_LENGTH_HIGH))
                    .isBetween(CONTENT_LENGTH_LOW, CONTENT_LENGTH_HIGH);
        }
    }

    @Test
    @DisplayName("Query documents by content length greater than or equal")
    void testQueryDocumentByContentLengthGreaterThanOrEqual() throws QueryConditionException
    {
        final Long CONTENT_LENGTH_LOW = 35000L;

        DocumentQuery search = new DocumentQuery()
                .addCondition(QueryCondition.builder()
                        .withField(DocumentQuery.DOCUMENT_CONTENT_LENGTH)
                        .withValue(CONTENT_LENGTH_LOW)
                        .withOperator(QueryOperatorType.GREATER_THAN_EQUAL)
                        .build());

        List<DocumentServer> documents = servicePerson.getDocumentService().search(search);
        assertThat(documents)
                .as("Document list should not be empty!")
                .isNotEmpty();

        for (DocumentServer document : documents)
        {
            assertThat(document.getContentLength())
                    .as(String.format("Document content length: '%s' should be greater or equal to: '%s'", document.getContentLength(), CONTENT_LENGTH_LOW))
                    .isGreaterThanOrEqualTo(CONTENT_LENGTH_LOW);
        }
    }

    @Test
    @DisplayName("Query documents by their document type equal to")
    void testQueryDocumentByDocumentTypeEqualTo() throws QueryConditionException
    {
        final DocumentType DOCUMENT_TYPE = DocumentType.DOCUMENT_INVOICE;

        DocumentQuery search = new DocumentQuery()
                .addCondition(QueryCondition.builder()
                        .withField(DocumentQuery.DOCUMENT_TYPE)
                        .withValue(DOCUMENT_TYPE)
                        .withOperator(QueryOperatorType.EQUAL)
                        .build());

        List<DocumentServer> documents = servicePerson.getDocumentService().search(search);
        assertThat(documents)
                .as("Document list should not be empty!")
                .isNotEmpty();

        for (DocumentServer document : documents)
        {
            assertThat(document.getDocumentType())
                    .as(String.format("Document type expected: '%s'!", document.getDocumentType()))
                    .isEqualTo(DOCUMENT_TYPE);
        }
    }

    @Test
    @DisplayName("Query documents by their status type equal to")
    void testQueryDocumentByStatusTypeEqualTo() throws QueryConditionException, JsonProcessingException
    {
        final StatusType DOCUMENT_STATUS = StatusType.INACTIVE;

        DocumentQuery search = new DocumentQuery()
                .addCondition(QueryCondition.builder()
                        .withField(DocumentQuery.BASE_STATUS_TYPE)
                        .withValue(DOCUMENT_STATUS)
                        .withOperator(QueryOperatorType.EQUAL)
                        .build());

        List<DocumentServer> documents = servicePerson.getDocumentService().search(search);
        assertThat(documents)
                .as("Document list should not be empty!")
                .isNotEmpty();

        for (DocumentServer document : documents)
        {
            assertThat(document.getStatusType())
                    .as(String.format("Document status type expected: '%s'!", document.getStatusType()))
                    .isEqualTo(DOCUMENT_STATUS);
        }
    }
}
