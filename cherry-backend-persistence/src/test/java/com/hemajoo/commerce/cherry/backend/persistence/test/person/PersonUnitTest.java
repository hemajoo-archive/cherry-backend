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

import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.backend.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.person.service.PersonService;
import com.hemajoo.commerce.cherry.backend.persistence.test.base.AbstractPostgresUnitTest;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link ServerPersonEntity} and the {@link PersonService}.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Testcontainers // Not to be used to keep container alive after the tests!
@DirtiesContext
@SpringBootTest
@Log4j2
class PersonUnitTest extends AbstractPostgresUnitTest
{
    /**
     * Person services.
     */
    @Autowired
    private ServiceFactoryPerson servicePerson;

    @Test
    @DisplayName("Create a person")
    void testCreatePerson() throws DocumentException
    {
        ServerPersonEntity person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        assertThat(person)
                .as("Person should not be null!")
                .isNotNull();

        assertThat(person.getId())
                .as("Person identifier should not be null!")
                .isNotNull();
    }

    @Test
    @DisplayName("Update a person")
    void testUpdatePerson() throws DocumentException
    {
        ServerPersonEntity person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        assertThat(person)
                .as("Person should not be null!")
                .isNotNull();

        assertThat(person.getId())
                .as("Person identifier should not be null!")
                .isNotNull();

        String description = person.getDescription();
        person.setDescription("Test description for person: " + person.getId());
        person = servicePerson.getPersonService().saveAndFlush(person);

        ServerPersonEntity updated = servicePerson.getPersonService().findById(person.getId());

        assertThat(updated)
                .as("Person should not be null!")
                .isNotNull();

        assertThat(updated.getDescription())
                .as("Persons description should be different!")
                .isNotEqualTo(description);
    }

    @Test
    @DisplayName("Delete a person")
    void testDeletePerson() throws DocumentException
    {
        ServerPersonEntity person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        assertThat(person)
                .as("Person should not be null!")
                .isNotNull();

        assertThat(person.getId())
                .as("Person identifier should not be null!")
                .isNotNull();

        servicePerson.getPersonService().deleteById(person.getId());

        assertThat(servicePerson.getPersonService().findById(person.getId()))
                .as("Person should be null!")
                .isNull();
    }
}
