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
package com.hemajoo.commerce.cherry.backend.persistence.base.entity;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.persistence.document.repository.DocumentRepository;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.EmailAddressRepository;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.PersonRepository;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.PhoneNumberRepository;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.PostalAddressRepository;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Provides an entity factory to instantiate a server entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
@Component
public class EntityFactory
{
    /**
     * Person repository.
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * Document repository.
     */
    @Autowired
    private DocumentRepository documentRepository;

    /**
     * Email address repository.
     */
    @Autowired
    private EmailAddressRepository emailAddressRepository;

    /**
     * Postal address repository.
     */
    @Autowired
    private PostalAddressRepository postalAddressRepository;

    /**
     * Phone number repository.
     */
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    /**
     * Creates a server entity given its entity type and its identifier.
     * @param type Entity type.
     * @param uuid Entity identifier.
     * @param <T> Object type.
     * @return Server entity object.
     * @throws EntityFactoryException Thrown to indicate an error occurred when trying to create the server entity object.
     */
    public <T extends ServerBaseEntity> T from(final EntityType type, final @NonNull String uuid) throws EntityFactoryException
    {
        Optional<T> object;

        switch (type)
        {
            case PERSON:
                object = (Optional<T>) personRepository.findById(UUID.fromString(uuid));
                break;

            case DOCUMENT:
                object = (Optional<T>) documentRepository.findById(UUID.fromString(uuid));
                break;

            case EMAIL_ADDRESS:
                object = (Optional<T>) emailAddressRepository.findById(UUID.fromString(uuid));
                break;

            case POSTAL_ADDRESS:
                object = (Optional<T>) postalAddressRepository.findById(UUID.fromString(uuid));
                break;

            case PHONE_NUMBER:
                object = (Optional<T>) phoneNumberRepository.findById(UUID.fromString(uuid));
                break;

            default:
                throw new EntityFactoryException(String.format("Entity type: '%s' is not handled!", type));
        }

        if (object.isEmpty())
        {
            throw new EntityException(type, String.format("Cannot find entity for type: '%s', with id: '%s'", type, uuid));
        }

        return object.get();
    }
}
