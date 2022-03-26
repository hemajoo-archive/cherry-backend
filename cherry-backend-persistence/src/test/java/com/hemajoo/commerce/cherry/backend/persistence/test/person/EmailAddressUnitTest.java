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
package com.hemajoo.commerce.cherry.backend.persistence.test.person;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.EntityFactory;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.EmailAddressServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PersonServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.randomizer.EmailAddressRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.test.base.AbstractPostgresUnitTest;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the email address server class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@DirtiesContext
@Testcontainers // Not to be used to keep container alive after the tests!
@SpringBootTest
class EmailAddressUnitTest extends AbstractPostgresUnitTest
{
    /**
     * Entity factory.
     */
    @Autowired
    private EntityFactory factory;

    /**
     * Person services.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    @Test
    @DisplayName("Create an email address")
    void testCreateEmailAddress() throws EmailAddressException, DocumentException
    {
        PersonServer person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        EmailAddressServer emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        emailAddress.setParent(person);
        emailAddress = servicePerson.getEmailAddressService().save(emailAddress);

        LOGGER.info(String.format("Person id generated: '%s'", person.getId()));
        LOGGER.info(String.format("Email address id generated: '%s'", emailAddress.getId()));

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address identifier should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Create an email address with one document")
    void testCreateEmailAddressWithOneDocument() throws EmailAddressException, DocumentException
    {
        DocumentServer document = DocumentRandomizer.generateServerEntity(false);
        PersonServer person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        EmailAddressServer emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        emailAddress.addDocument(document);
        person.addEmailAddress(emailAddress);
        emailAddress = servicePerson.getEmailAddressService().save(emailAddress);

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address identifier should not be null!")
                .isNotNull();

        assertThat(emailAddress.getDocuments().size())
                .as("Email address document list should not be empty!")
                .isNotZero();
    }

    @Test
    @DisplayName("Create an email address with several documents")
    void testCreateEmailAddressWithSeveralDocument() throws EmailAddressException, DocumentException
    {
        List<DocumentServer> documents = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            documents.add(DocumentRandomizer.generateServerEntity(false));
        }

        PersonServer person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        EmailAddressServer emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        documents.forEach(emailAddress::addDocument);
        person.addEmailAddress(emailAddress);
        emailAddress = servicePerson.getEmailAddressService().save(emailAddress);

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address identifier should not be null!")
                .isNotNull();

        assertThat(emailAddress.getDocuments().size())
                .as("Email address document list should not be empty!")
                .isEqualTo(5);
    }

    @Test
    @DisplayName("Update an email address")
    void testUpdateEmailAddress() throws EmailAddressException, DocumentException
    {
        PersonServer person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        EmailAddressServer emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        emailAddress.setParent(person);
        emailAddress = servicePerson.getEmailAddressService().save(emailAddress);

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address identifier should not be null!")
                .isNotNull();

        String description = emailAddress.getDescription();
        emailAddress.setDescription("Test description for person: " + emailAddress.getId());
        emailAddress = servicePerson.getEmailAddressService().saveAndFlush(emailAddress);

        EmailAddressServer updated = servicePerson.getEmailAddressService().findById(emailAddress.getId());

        assertThat(updated)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(updated.getDescription())
                .as("Email addresses description should be different!")
                .isNotEqualTo(description);
    }

    @Test
    @DisplayName("Delete an email address")
    void testDeleteEmailAddress() throws EmailAddressException, DocumentException
    {
        PersonServer person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        EmailAddressServer emailAddress = EmailAddressRandomizer.generateServerEntity(false);
        emailAddress.setParent(person);
        emailAddress = servicePerson.getEmailAddressService().save(emailAddress);

        assertThat(emailAddress)
                .as("Email address should not be null!")
                .isNotNull();

        assertThat(emailAddress.getId())
                .as("Email address should not be null!")
                .isNotNull();

        servicePerson.getEmailAddressService().deleteById(emailAddress.getId());

        assertThat(servicePerson.getEmailAddressService().findById(emailAddress.getId()))
                .as("Email address should be null!")
                .isNull();
    }

    @Test
    @DisplayName("Delete email address. Orphan document is to be deleted")
    final void testValidateEmailAddressDocumentOrphanRemove() throws DocumentException, EmailAddressException
    {
        EmailAddressServer email = EmailAddressRandomizer.generateServerEntity(false);
        DocumentServer document = DocumentRandomizer.generateServerEntity(false);
        PersonServer person = PersonRandomizer.generateServerEntity(false);

        servicePerson.getPersonService().save(person); // Save it to get its UUID
        servicePerson.getDocumentService().save(document); // Save it to get its UUID
        servicePerson.getEmailAddressService().save(email); // Save it to get its UUID

        email.addDocument(document);
        person.addEmailAddress(email);
        servicePerson.getPersonService().save(person);

        assertThat(email.getId())
                .as("Email id should not be null!")
                .isNotNull();
        assertThat(document.getId())
                .as("Document id should not be null!")
                .isNotNull();
        assertThat(person.getId())
                .as("Person id should not be null!")
                .isNotNull();

        assertThat(person.getEmailAddresses())
                .as("Person's email address list should not be empty!")
                .isNotEmpty();
        assertThat(person.getEmailAddresses().get(0))
                .as("Person's email address should contain 5one email address!")
                .isNotNull();
        assertThat(person.getEmailAddresses().get(0).getId())
                .as("Person's email address should have a valid identifier!")
                .isNotNull();
        assertThat(person.getEmailAddresses().get(0).getEntityType())
                .as("Person's email address should be of type: EMAIL_ADDRESS!")
                .isEqualTo(EntityType.EMAIL_ADDRESS);

        UUID documentId = document.getId();

        // By deleting the email address, the child document should be removed!
        person.removeEmailAddress(email);
        servicePerson.getEmailAddressService().deleteById(email.getId());
        servicePerson.getPersonService().save(person);

        assertThat(person.getEmailAddresses())
                .as("Person's email address list should be empty!")
                .isEmpty();
        assertThat(servicePerson.getEmailAddressService().findById(documentId))
                .as("Document should have been deleted!")
                .isNull();
    }

    @Test
    @DisplayName("Sets a document as being the parent of an email address.")
    final void testSetDocumentAsParentOfEmailAddress() throws EmailAddressException, DocumentException
    {
        EmailAddressServer email = EmailAddressRandomizer.generateServerEntity(false);
        DocumentServer document = DocumentRandomizer.generateServerEntity(false);

        document = servicePerson.getDocumentService().save(document);
        email = servicePerson.getEmailAddressService().save(email);

        email.setParent(document);
        servicePerson.getEmailAddressService().save(email);

        assertThat(servicePerson.getEmailAddressService().findById(email.getId()))
                .as("Email address should not be null!")
                .isNotNull();
        assertThat(servicePerson.getDocumentService().findById(document.getId()))
                .as("Email address should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Sets an email address as being the parent of another email address.")
    final void testSetEmailAddressAsParentOfEmailAddress() throws EmailAddressException
    {
        EmailAddressServer email = EmailAddressRandomizer.generateServerEntity(false);
        EmailAddressServer other = EmailAddressRandomizer.generateServerEntity(false);

        other = servicePerson.getEmailAddressService().save(other); // Must be saved first!
        email = servicePerson.getEmailAddressService().save(email);
        email.setParent(other);
        email = servicePerson.getEmailAddressService().save(email);

        EmailAddressServer object = servicePerson.getEmailAddressService().findById(email.getId());
        assertThat(object)
                .as("Should not be null!")
                .isNotNull();
        assertThat(object.getParent())
                .as("Parent should not be null!")
                .isNotNull();
        assertThat(object.getParent().getEntityType())
                .as(String.format("Parent entity type should be: '%s'", other.getEntityType()))
                .isEqualTo(other.getEntityType());
    }

    @Test
    @DisplayName("Sets a person as being the parent of another email address.")
    final void testSetPersonAsParentOfEmailAddress() throws EmailAddressException, DocumentException
    {
        EmailAddressServer email = EmailAddressRandomizer.generateServerEntity(false);
        PersonServer person = PersonRandomizer.generateServerEntity(false);

        person = servicePerson.getPersonService().save(person); // Must be saved first!
        email = servicePerson.getEmailAddressService().save(email);
        email.setParent(person);
        email = servicePerson.getEmailAddressService().save(email);

        EmailAddressServer object = servicePerson.getEmailAddressService().findById(email.getId());
        assertThat(object)
                .as("Should not be null!")
                .isNotNull();
        assertThat(object.getParent())
                .as("Parent should not be null!")
                .isNotNull();
        assertThat(object.getParent().getEntityType())
                .as(String.format("Parent entity type should be: '%s'", person.getEntityType()))
                .isEqualTo(person.getEntityType());
    }

    @Test
    @DisplayName("Ensures an entity cannot be set as its parent.")
    final void testCannotSetEmailAddressAsOwnParent()
    {
        final EmailAddressServer email = EmailAddressRandomizer.generateServerEntity(false);

        assertThrows(RuntimeException.class, () -> {
            email.setParent(email);
        });
    }

    @Test
    @DisplayName("Retrieves an email address using the entity factory.")
    final void testRetrieveEmailAddressUsingEntityFactory() throws EntityException
    {
        EmailAddressServer email = EmailAddressRandomizer.generateServerEntity(false);
        email = servicePerson.getEmailAddressService().save(email);

        EmailAddressServer other = (EmailAddressServer) factory.from(EntityType.EMAIL_ADDRESS, email.getId());
        assertThat(other)
                .as("Entity should not be null!")
                .isNotNull();
        assertThat(other.getEmail())
                .as("Entity emails should match!")
                .isEqualTo(email.getEmail());
    }
}
