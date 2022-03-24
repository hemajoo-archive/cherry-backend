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
package com.hemajoo.commerce.cherry.backend.persistence.person.converter;

import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.AbstractBaseEntityMapper;
import com.hemajoo.commerce.cherry.backend.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerEmailAddressEntity;
import com.hemajoo.commerce.cherry.backend.persistence.person.mapper.AbstractEmailAddressMapper;
import com.hemajoo.commerce.cherry.backend.shared.person.address.ClientEmailAddressEntity;
import com.hemajoo.commerce.cherry.backend.shared.person.address.EmailAddressException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Component to convert between instances of client and server email addresses.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public final class EmailAddressConverter
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Converts from a server email address entity to an entity identity.
     * @param server Server email address entity.
     * @return Entity identity.
     */
    public EntityIdentity fromServerToIdentity(ServerEmailAddressEntity server)
    {
        return AbstractEmailAddressMapper.INSTANCE.fromServerToIdentity(server, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from an entity identity to a server email address entity.
     * @param identity Entity identity.
     * @return Server email address entity.
     * @throws EmailAddressException Thrown to indicate an error occurred when trying to convert an email address.
     */
    public ServerEmailAddressEntity fromIdentityToServer(EntityIdentity identity) throws EmailAddressException
    {
        try
        {
            return AbstractBaseEntityMapper.INSTANCE.map(identity,entityManager);
        }
        catch (Exception e)
        {
            throw new EmailAddressException(e);
        }
    }

    /**
     * Converts from a client email address entity to a server email address entity.
     * @param client Client email address entity.
     * @return Server email address entity.
     * @throws EmailAddressException Thrown to indicate an error occurred when trying to convert an email address.
     */
    public ServerEmailAddressEntity fromClientToServer(ClientEmailAddressEntity client) throws EmailAddressException
    {
        try
        {
            return AbstractEmailAddressMapper.INSTANCE.fromClientToServer(client, new CycleAvoidingMappingContext(), entityManager);
        }
        catch (Exception e)
        {
            throw new EmailAddressException(e);
        }
    }

    /**
     * Converts from a server email address entity to a client email address entity.
     * @param server Server email address entity.
     * @return Client email address entity.
     */
    public ClientEmailAddressEntity fromServerToClient(ServerEmailAddressEntity server)
    {
        return AbstractEmailAddressMapper.INSTANCE.fromServerToClient(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a server email address entity.
     * @param server Server email address entity.
     * @return Copied server email address entity.
     * @throws EmailAddressException Thrown to indicate an error occurred when trying to copy an email address.
     */
    public static ServerEmailAddressEntity copy(ServerEmailAddressEntity server) throws EmailAddressException
    {
        try
        {
            return AbstractEmailAddressMapper.INSTANCE.copy(server, new CycleAvoidingMappingContext());
        }
        catch (Exception e)
        {
            throw new EmailAddressException(e);
        }
    }

    /**
     * Copy a client email address entity.
     * @param client Client email address entity.
     * @return Copied client email address entity.
     * @throws EmailAddressException Thrown to indicate an error occurred when trying to copy an email address.
     */
    public static ClientEmailAddressEntity copy(ClientEmailAddressEntity client) throws EmailAddressException
    {
        try
        {
            return AbstractEmailAddressMapper.INSTANCE.copy(client, new CycleAvoidingMappingContext());
        }
        catch (Exception e)
        {
            throw new EmailAddressException(e);
        }
    }
}
