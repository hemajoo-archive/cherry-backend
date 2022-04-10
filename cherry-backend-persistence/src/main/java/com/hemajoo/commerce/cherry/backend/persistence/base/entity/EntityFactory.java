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

import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.EmailAddressServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PersonServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PhoneNumberServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PostalAddressServer;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
     * Entity manager.
     */
    @Getter
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Retrieves a server entity given its identity.
     * @param identity Entity identity.
     * @return Server entity object.
     * @throws EntityException Thrown to indicate an error occurred when trying to retrieve the server entity object.
     */
    public final IServerEntity from(final @NonNull EntityIdentity identity) throws EntityException
    {
        return from(identity.getEntityType(), identity.getId());
    }

    /**
     * Retrieves a server entity given its type and its identifier.
     * @param type Entity type.
     * @param uuid Entity identifier.
     * @return Server entity object.
     * @throws EntityException Thrown to indicate an error occurred when trying to retrieve the server entity object.
     */
    public final IServerEntity from(final EntityType type, final @NonNull UUID uuid) throws EntityException
    {
        switch (type)
        {
            case PERSON:
                return entityManager.find(PersonServer.class, uuid);

            case DOCUMENT:
                return entityManager.find(DocumentServer.class, uuid);

            case EMAIL_ADDRESS:
                return entityManager.find(EmailAddressServer.class, uuid);

            case POSTAL_ADDRESS:
                return entityManager.find(PostalAddressServer.class, uuid);

            case PHONE_NUMBER:
                return entityManager.find(PhoneNumberServer.class, uuid);

            default:
                throw new EntityException(String.format("Entity type: '%s' is not handled!", type));
        }
    }
}
