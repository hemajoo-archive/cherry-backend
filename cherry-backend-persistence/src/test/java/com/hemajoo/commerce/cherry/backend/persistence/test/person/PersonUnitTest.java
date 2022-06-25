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

import com.hemajoo.commerce.cherry.backend.commons.type.StatusType;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServiceFactoryPerson;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PersonServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.randomizer.PersonRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.person.service.IPersonService;
import com.hemajoo.commerce.cherry.backend.persistence.test.base.AbstractPostgresUnitTest;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryCondition;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryOperatorType;
import com.hemajoo.commerce.cherry.backend.shared.person.GenderType;
import com.hemajoo.commerce.cherry.backend.shared.person.PersonException;
import com.hemajoo.commerce.cherry.backend.shared.person.PersonQuery;
import com.hemajoo.commerce.cherry.backend.shared.person.PersonType;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link PersonServer} and the {@link IPersonService}.
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

    /**
     * Test to create a person.
     * @throws PersonException Thrown to indicate an error occurred when trying to create a person.
     */
    @Test
    @DisplayName("Create a person")
    void testCreatePerson() throws PersonException
    {
        PersonServer person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        assertThat(person)
                .as("Person should not be null!")
                .isNotNull();

        assertThat(person.getId())
                .as("Person identifier should not be null!")
                .isNotNull();
    }

    /**
     * Test to update a person.
     * @throws PersonException Thrown to indicate an error occurred when trying to update a person.
     */
    @Test
    @DisplayName("Update a person")
    void testUpdatePerson() throws PersonException
    {
        PersonServer person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

        assertThat(person)
                .as("Person should not be null!")
                .isNotNull();

        assertThat(person.getId())
                .as("Person identifier should not be null!")
                .isNotNull();

        String description = person.getDescription();
        person.setDescription("Test description for person: " + person.getId());
        person = servicePerson.getPersonService().saveAndFlush(person);

        PersonServer updated = servicePerson.getPersonService().findById(person.getId());

        assertThat(updated)
                .as("Person should not be null!")
                .isNotNull();

        assertThat(updated.getDescription())
                .as("Persons description should be different!")
                .isNotEqualTo(description);
    }

    /**
     * Test to delete a person.
     * @throws PersonException Thrown to indicate an error occurred when trying to delete a person.
     */
    @Test
    @DisplayName("Delete a person")
    void testDeletePerson() throws PersonException
    {
        PersonServer person = servicePerson.getPersonService().save(PersonRandomizer.generateServerEntity(false));

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

    /**
     * Test to query persons by their last name.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    @Test
    @DisplayName("Query persons by their last name")
    void testQueryPersonByLastName() throws QueryConditionException
    {
        List<PersonServer> persons = PersonRandomizer.generateServerEntities(true, 10);

        for (PersonServer person : persons)
        {
            servicePerson.getPersonService().saveAndFlush(person);
        }

        final String LASTNAME = persons.get(0).getLastName();

        PersonQuery query = new PersonQuery();
        query.addCondition(QueryCondition.builder()
                .withField(PersonQuery.PERSON_LASTNAME)
                .withOperator(QueryOperatorType.EQUAL)
                .withValue(LASTNAME)
                .build());

        List<PersonServer> results = servicePerson.getPersonService().search(query);
        assertThat(results)
                .as("Result list should not be empty!")
                .isNotEmpty();

        boolean found = persons.stream().anyMatch(person -> person.getLastName().equals(LASTNAME));
        assertThat(found)
                .as(String.format("At least one person's last name should be: '%s'", LASTNAME))
                .isTrue();
    }

    /**
     * Test to query persons by their first name.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    @Test
    @DisplayName("Query persons by their first name")
    void testQueryPersonByFirstName() throws QueryConditionException
    {
        List<PersonServer> persons = PersonRandomizer.generateServerEntities(true, 10);

        for (PersonServer person : persons)
        {
            servicePerson.getPersonService().saveAndFlush(person);
        }

        final String FIRSTNAME = persons.get(0).getFirstName();

        PersonQuery query = new PersonQuery();
        query.addCondition(QueryCondition.builder()
                .withField(PersonQuery.PERSON_FIRSTNAME)
                .withOperator(QueryOperatorType.EQUAL)
                .withValue(FIRSTNAME)
                .build());

        List<PersonServer> results = servicePerson.getPersonService().search(query);
        assertThat(results)
                .as("Result list should not be empty!")
                .isNotEmpty();

        boolean found = persons.stream().anyMatch(person -> person.getFirstName().equals(FIRSTNAME));
        assertThat(found)
                .as(String.format("At least one person's first name should be: '%s'", FIRSTNAME))
                .isTrue();
    }

    /**
     * Test to query persons by their birthdate.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    @Test
    @DisplayName("Query persons by their birthdate")
    void testQueryPersonByBirthdate() throws QueryConditionException
    {
        List<PersonServer> persons = PersonRandomizer.generateServerEntities(true, 10);

        for (PersonServer person : persons)
        {
            servicePerson.getPersonService().saveAndFlush(person);
        }

        final Date BIRTHDATE = persons.get(0).getBirthDate();

        PersonQuery query = new PersonQuery();
        query.addCondition(QueryCondition.builder()
                .withField(PersonQuery.PERSON_BIRTHDATE)
                .withOperator(QueryOperatorType.EQUAL)
                .withValue(BIRTHDATE)
                .build());

        List<PersonServer> results = servicePerson.getPersonService().search(query);
        assertThat(results)
                .as("Result list should not be empty!")
                .isNotEmpty();

        boolean found = persons.stream().anyMatch(person -> person.getBirthDate().equals(BIRTHDATE));
        assertThat(found)
                .as(String.format("At least one person's birthdate should be: '%s'", BIRTHDATE))
                .isTrue();
    }

    /**
     * Test to query persons by their gender.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    @Test
    @DisplayName("Query persons by their gender")
    void testQueryPersonByGender() throws QueryConditionException
    {
        List<PersonServer> persons = PersonRandomizer.generateServerEntities(true, 10);

        for (PersonServer person : persons)
        {
            servicePerson.getPersonService().saveAndFlush(person);
        }

        final GenderType GENDER = persons.get(0).getGenderType();

        PersonQuery query = new PersonQuery();
        query.addCondition(QueryCondition.builder()
                .withField(PersonQuery.PERSON_GENDER_TYPE)
                .withOperator(QueryOperatorType.EQUAL)
                .withValue(GENDER)
                .build());

        List<PersonServer> results = servicePerson.getPersonService().search(query);
        assertThat(results)
                .as("Result list should not be empty!")
                .isNotEmpty();

        boolean found = persons.stream().anyMatch(person -> person.getGenderType().equals(GENDER));
        assertThat(found)
                .as(String.format("At least one person's gender should be: '%s'", GENDER))
                .isTrue();
    }

    /**
     * Test to query persons by their status.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    @Test
    @DisplayName("Query persons by their status")
    void testQueryPersonByStatus() throws QueryConditionException
    {
        List<PersonServer> persons = PersonRandomizer.generateServerEntities(true, 10);

        for (PersonServer person : persons)
        {
            servicePerson.getPersonService().saveAndFlush(person);
        }

        final StatusType STATUS = persons.get(0).getStatusType();

        PersonQuery query = new PersonQuery();
        query.addCondition(QueryCondition.builder()
                .withField(PersonQuery.BASE_STATUS_TYPE)
                .withOperator(QueryOperatorType.EQUAL)
                .withValue(STATUS)
                .build());

        List<PersonServer> results = servicePerson.getPersonService().search(query);
        assertThat(results)
                .as("Result list should not be empty!")
                .isNotEmpty();

        boolean found = persons.stream().anyMatch(person -> person.getStatusType().equals(STATUS));
        assertThat(found)
                .as(String.format("At least one person's status should be: '%s'", STATUS))
                .isTrue();
    }

    /**
     * Test to query persons by their type.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    @Test
    @DisplayName("Query persons by their type")
    void testQueryPersonByType() throws QueryConditionException
    {
        List<PersonServer> persons = PersonRandomizer.generateServerEntities(true, 10);

        for (PersonServer person : persons)
        {
            servicePerson.getPersonService().saveAndFlush(person);
        }

        final PersonType TYPE = persons.get(0).getPersonType();

        PersonQuery query = new PersonQuery();
        query.addCondition(QueryCondition.builder()
                .withField(PersonQuery.PERSON_PERSON_TYPE)
                .withOperator(QueryOperatorType.EQUAL)
                .withValue(TYPE)
                .build());

        List<PersonServer> results = servicePerson.getPersonService().search(query);
        assertThat(results)
                .as("Result list should not be empty!")
                .isNotEmpty();

        boolean found = persons.stream().anyMatch(person -> person.getPersonType().equals(TYPE));
        assertThat(found)
                .as(String.format("At least one person's type should be: '%s'", TYPE))
                .isTrue();
    }

    /**
     * Test to query persons by their reference.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     */
    @Test
    @DisplayName("Query persons by their reference")
    void testQueryPersonByReference() throws QueryConditionException
    {
        List<PersonServer> persons = PersonRandomizer.generateServerEntities(true, 10);

        for (PersonServer person : persons)
        {
            servicePerson.getPersonService().saveAndFlush(person);
        }

        final String REFERENCE = persons.get(0).getReference();

        PersonQuery query = new PersonQuery();
        query.addCondition(QueryCondition.builder()
                .withField(PersonQuery.BASE_REFERENCE)
                .withOperator(QueryOperatorType.EQUAL)
                .withValue(REFERENCE)
                .build());

        List<PersonServer> results = servicePerson.getPersonService().search(query);
        assertThat(results)
                .as("Result list should not be empty!")
                .isNotEmpty();

        boolean found = persons.stream().anyMatch(person -> person.getReference().equals(REFERENCE));
        assertThat(found)
                .as(String.format("At least one person's reference should be: '%s'", REFERENCE))
                .isTrue();
    }

    /**
     * Test to query persons by tag.
     * @throws QueryConditionException Thrown to indicate an error occurred with a query condition.
     * @throws NoSuchAlgorithmException Thrown to indicate an error occurred with trying to generate a random number.
     */
    @Test
    @DisplayName("Query persons by tag")
    void testQueryPersonByTag() throws QueryConditionException, NoSuchAlgorithmException
    {
        List<PersonServer> persons = PersonRandomizer.generateServerEntities(true, 10);

        for (PersonServer person : persons)
        {
            servicePerson.getPersonService().saveAndFlush(person);
        }

        final String TAG = persons.get(0).getRandomTag();

        PersonQuery query = new PersonQuery();
        query.addCondition(QueryCondition.builder()
                .withField(PersonQuery.BASE_TAGS)
                .withOperator(QueryOperatorType.CONTAINS)
                .withValue(TAG)
                .build());

        List<PersonServer> results = servicePerson.getPersonService().search(query);
        assertThat(results)
                .as("Result list should not be empty!")
                .isNotEmpty();

        boolean found = persons.stream().anyMatch(person -> person.getTags().contains(TAG));
        assertThat(found)
                .as(String.format("At least one person's tags should be: '%s'", TAG))
                .isTrue();
    }
}
