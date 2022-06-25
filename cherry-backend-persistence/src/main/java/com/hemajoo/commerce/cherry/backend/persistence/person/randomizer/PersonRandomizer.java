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
package com.hemajoo.commerce.cherry.backend.persistence.person.randomizer;

import com.hemajoo.commerce.cherry.backend.persistence.base.randomizer.AbstractEntityRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PersonServer;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentClient;
import com.hemajoo.commerce.cherry.backend.shared.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.person.GenderType;
import com.hemajoo.commerce.cherry.backend.shared.person.PersonClient;
import com.hemajoo.commerce.cherry.backend.shared.person.PersonType;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressException;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Random person generator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class PersonRandomizer extends AbstractEntityRandomizer
{
    /**
     * Person type enumeration generator.
     */
    private static final EnumRandomGenerator PERSON_TYPE_GENERATOR = new EnumRandomGenerator(PersonType.class);

    /**
     * Gender type enumeration generator.
     */
    private static final EnumRandomGenerator GENDER_TYPE_GENERATOR = new EnumRandomGenerator(GenderType.class);

    /**
     * Generates a list of random server persons.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * @param count Number of persons to generate.
     * @return List of random persons.
     */
    public static List<PersonServer> generateServerEntities(final boolean withRandomId, final int count)
    {
        List<PersonServer> persons = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            persons.add(generateServerEntity(withRandomId));
        }

        return persons;
    }

    /**
     * Generates a new random server person.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * @return Random person.
     */
    public static PersonServer generateServerEntity(final boolean withRandomId)
    {
        var entity = new PersonServer();
        AbstractEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setFirstName(FAKER.name().firstName());
        entity.setLastName(FAKER.name().lastName());
        entity.setBirthDate(FAKER.date().birthday(18, 70));
        entity.setPersonType((PersonType) PERSON_TYPE_GENERATOR.gen());
        entity.setGenderType((GenderType) GENDER_TYPE_GENERATOR.gen());

        return entity;
    }

    /**
     * Generates a list of random server persons each holding multiple documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * @param count Number of persons to generate.
     * @return List of random persons.
     * @throws NoSuchAlgorithmException Thrown in case an error occurred while getting a random number.
     * @throws DocumentException Thrown in case an error occurred while trying to generate a document.
     */
    public static List<PersonServer> generateServerEntitiesWithDocuments(final boolean withRandomId, final int count) throws DocumentException, NoSuchAlgorithmException
    {
        List<PersonServer> persons = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            persons.add(generateServerEntityWithDocument(withRandomId, SecureRandom.getInstanceStrong().nextInt(10)));
        }

        return persons;
    }

    /**
     * Generates a new random server person with associated documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of random documents to generate.
     * @return Person.
     * @throws DocumentException Thrown in case an error occurred while trying to generate a document.
     */
    public static PersonServer generateServerEntityWithDocument(final boolean withRandomId, final int count) throws DocumentException
    {
        var entity = new PersonServer();
        DocumentServer document;
        AbstractEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generateServerEntity(true);
            entity.addDocument(document);
        }

        entity.setFirstName(FAKER.name().firstName());
        entity.setLastName(FAKER.name().lastName());
        entity.setBirthDate(FAKER.date().birthday(18, 70));
        entity.setPersonType((PersonType) PERSON_TYPE_GENERATOR.gen());
        entity.setGenderType((GenderType) GENDER_TYPE_GENERATOR.gen());

        return entity;
    }

    /**
     * Generates a list of random client persons.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * @param count Number of persons to generate.
     * @return List of random persons.
     */
    public static List<PersonClient> generateClientEntities(final boolean withRandomId, final int count)
    {
        List<PersonClient> persons = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            persons.add(generateClientEntity(withRandomId));
        }

        return persons;
    }

    /**
     * Generates a new random client person.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Random person.
     */
    public static PersonClient generateClientEntity(final boolean withRandomId)
    {
        var entity = new PersonClient();
        AbstractEntityRandomizer.populateBaseFields(entity);
   
        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setFirstName(FAKER.name().firstName());
        entity.setLastName(FAKER.name().lastName());
        entity.setBirthDate(FAKER.date().birthday(18, 70));
        entity.setPersonType((PersonType) PERSON_TYPE_GENERATOR.gen());
        entity.setGenderType((GenderType) GENDER_TYPE_GENERATOR.gen());

        return entity;
    }

    /**
     * Generates a list of random client persons each holding a random number of documents (between 0 and 10).
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * @param count Number of persons to generate.
     * @return List of random persons.
     * @throws NoSuchAlgorithmException Thrown in case an error occurred while getting a random number.
     * @throws DocumentException Thrown in case an error occurred while trying to generate a document.
     */
    public static List<PersonClient> generateClientEntitiesWithDocuments(final boolean withRandomId, final int count) throws NoSuchAlgorithmException, DocumentException
    {
        List<PersonClient> persons = new ArrayList<>();

        for (int i = 0; i < count; i++)
        {
            persons.add(generateClientEntityWithDocument(withRandomId, SecureRandom.getInstanceStrong().nextInt(10)));
        }

        return persons;
    }

    /**
     * Generates a new random client person with associated documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of documents to generate.
     * @return Person.
     * @throws DocumentException Thrown in case an error occurred while trying to generate a document.
     */
    public static PersonClient generateClientEntityWithDocument(final boolean withRandomId, final int count) throws DocumentException
    {
        DocumentClient document;
        PersonClient entity = new PersonClient();
        AbstractEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generateClientEntity(true);
            entity.addDocument(document.getIdentity());
        }

        entity.setFirstName(FAKER.name().firstName());
        entity.setLastName(FAKER.name().lastName());
        entity.setBirthDate(FAKER.date().birthday(18, 70));
        entity.setPersonType((PersonType) PERSON_TYPE_GENERATOR.gen());
        entity.setGenderType((GenderType) GENDER_TYPE_GENERATOR.gen());

        return entity;
    }

    /**
     * Create a random person with its dependencies (i.e: email addresses, postal addresses and phone numbers).
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param bound Maximum number of dependencies to generate.
     * @return Person.
     * @throws EmailAddressException Raised in case an error occurred when trying to create an email address!
     */
    public static PersonServer generateServerEntityWithDependencies(final boolean withRandomId, final int bound) throws EmailAddressException
    {
        var person = generateServerEntity(withRandomId);

        int count = bound > 0 ? bound : AbstractEntityRandomizer.DEFAULT_DEPENDENCY_BOUND;
        for (var i = 0; i < count; i++)
        {
            person.addEmailAddress(EmailAddressRandomizer.generateServerEntity(withRandomId));
            person.addPhoneNumber(PhoneNumberRandomizer.generateServerEntity(withRandomId));
            person.addPostalAddress(PostalAddressRandomizer.generateServerEntity(withRandomId));
        }

        return person;
    }
}
