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

import com.hemajoo.commerce.cherry.backend.persistence.base.randomizer.AbstractBaseEntityRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.backend.persistence.document.randomizer.DocumentRandomizer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.backend.shared.document.ClientDocument;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentContentException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.AddressType;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.ClientEmailAddress;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.UUID;

/**
 * Random email address generator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public final class EmailAddressRandomizer extends AbstractBaseEntityRandomizer
{
    /**
     * Address type enumeration generator.
     */
    private static final EnumRandomGenerator ADDRESS_TYPE_GENERATOR = new EnumRandomGenerator(AddressType.class);

    /**
     * Generates a new random server email address.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} in unit tests.
     * @return Email address.
     */
    public static ServerEmailAddressEntity generateServerEntity(final boolean withRandomId)
    {
        var entity = new ServerEmailAddressEntity();
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setEmail(FAKER.internet().emailAddress());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setIsDefaultEmail(RANDOM.nextBoolean());

        return entity;
    }

    /**
     * Generates a new random server email address with associated documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of random documents to generate.
     * @return Email address.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static ServerEmailAddressEntity generateServerEntityWithDocument(final boolean withRandomId, final int count) throws DocumentContentException
    {
        var entity = new ServerEmailAddressEntity();
        ServerDocumentEntity document;
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generateServerEntity(true);
            entity.addDocument(document);
        }

        entity.setEmail(FAKER.internet().emailAddress());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setIsDefaultEmail(RANDOM.nextBoolean());

        return entity;
    }

    /**
     * Generates a new random client email address.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @return Email address.
     */
    public static ClientEmailAddress generateClientEntity(final boolean withRandomId)
    {
        var entity = new ClientEmailAddress();
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        entity.setEmail(FAKER.internet().emailAddress());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setIsDefaultEmail(RANDOM.nextBoolean());

        return entity;
    }

    /**
     * Generates a new random client email address with associated documents.
     * @param withRandomId Do we need to generate a random identifier? False by default.
     * <br>Generally set to {@code true} only for unit tests.
     * @param count Number of documents to generate.
     * @return Email address.
     * @throws DocumentContentException Thrown in case an error occurred while trying to generate a document.
     */
    public static ClientEmailAddress generateClientEntityWithDocument(final boolean withRandomId, final int count) throws DocumentContentException
    {
        ClientDocument document;
        ClientEmailAddress entity = new ClientEmailAddress();
        AbstractBaseEntityRandomizer.populateBaseFields(entity);

        if (withRandomId)
        {
            entity.setId(UUID.randomUUID());
        }

        for (int i = 0; i < count; i++)
        {
            document = DocumentRandomizer.generateClientEntity(true);
            entity.addDocument(document.getIdentity());
        }

        entity.setEmail(FAKER.internet().emailAddress());
        entity.setAddressType((AddressType) ADDRESS_TYPE_GENERATOR.gen());
        entity.setIsDefaultEmail(RANDOM.nextBoolean());

        return entity;
    }
}
