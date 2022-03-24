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
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.ServerDocumentEntity;
import com.hemajoo.commerce.cherry.backend.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerPhoneNumberEntity;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerPostalAddressEntity;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
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
     * Document repository.
     */
    @Autowired
    private DocumentService documentService;

    /**
     * Creates a server entity given its entity type and its identifier.
     * @param type Entity type.
     * @param uuid Entity identifier.
     * @return Server entity object.
     * @throws EntityException Thrown to indicate an error occurred when trying to create the server entity object.
     */
    public final ServerEntity from(final EntityType type, final @NonNull UUID uuid) throws EntityException
    {
        EntityManager entityManager = documentService.getEntityManager();

        switch (type)
        {
            case PERSON:
                return entityManager.find(ServerPersonEntity.class, uuid);

            case DOCUMENT:
                return entityManager.find(ServerDocumentEntity.class, uuid);

            case EMAIL_ADDRESS:
                return entityManager.find(ServerEmailAddressEntity.class, uuid);

            case POSTAL_ADDRESS:
                return entityManager.find(ServerPostalAddressEntity.class, uuid);

            case PHONE_NUMBER:
                return entityManager.find(ServerPhoneNumberEntity.class, uuid);

            default:
                throw new EntityException(String.format("Entity type: '%s' is not handled!", type));
        }
    }
}
