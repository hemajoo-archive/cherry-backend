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
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerPostalAddressEntity;
import com.hemajoo.commerce.cherry.backend.persistence.person.mapper.AbstractPostalAddressMapper;
import com.hemajoo.commerce.cherry.backend.shared.person.address.ClientPostalAddress;
import com.hemajoo.commerce.cherry.backend.shared.person.address.PostalAddressException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Component to convert between instances of client and server postal addresses.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public final class PostalAddressConverter
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Converts from a server postal address entity to an entity identity.
     * @param server Server postal address entity.
     * @return Entity identity.
     * @throws PostalAddressException Thrown to indicate an error occurred when trying to convert a postal address.
     */
    public EntityIdentity fromServerToIdentity(ServerPostalAddressEntity server) throws PostalAddressException
    {
        try
        {
            return AbstractPostalAddressMapper.INSTANCE.fromServerToIdentity(server, new CycleAvoidingMappingContext());
        }
        catch (Exception e)
        {
            throw new PostalAddressException(e);
        }
    }

    /**
     * Converts from an entity identity to a server postal address entity.
     * @param identity Entity identity.
     * @return Server postal address entity.
     * @throws PostalAddressException Thrown to indicate an error occurred when trying to convert a postal address.
     */
    public ServerPostalAddressEntity fromIdentityToServer(EntityIdentity identity) throws PostalAddressException
    {
        try
        {
            return AbstractBaseEntityMapper.INSTANCE.map(identity,entityManager);
        }
        catch (Exception e)
        {
            throw new PostalAddressException(e);
        }
    }

    /**
     * Converts from a client postal address entity to a server postal address entity.
     * @param client Client postal address entity.
     * @return Server postal address entity.
     * @throws PostalAddressException Thrown to indicate an error occurred while trying to convert a postal address entity.
     */
    public ServerPostalAddressEntity fromClientToServer(ClientPostalAddress client) throws PostalAddressException
    {
        return AbstractPostalAddressMapper.INSTANCE.fromClientToServer(client, new CycleAvoidingMappingContext(), entityManager);
    }

    /**
     * Converts from a server email address entity to a client email address entity.
     * @param server Server postal address entity.
     * @return Client postal address entity.
     */
    public ClientPostalAddress fromServerToClient(ServerPostalAddressEntity server)
    {
        return AbstractPostalAddressMapper.INSTANCE.fromServerToClient(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a server email address entity.
     * @param server Server postal address entity.
     * @return Copied server postal address entity.
     * @throws PostalAddressException Thrown to indicate an error occurred when trying to copy a postal address.
     */
    public static ServerPostalAddressEntity copy(ServerPostalAddressEntity server) throws PostalAddressException
    {
        try
        {
            return AbstractPostalAddressMapper.INSTANCE.copy(server, new CycleAvoidingMappingContext());
        }
        catch (Exception e)
        {
            throw new PostalAddressException(e);
        }
    }

    /**
     * Copy a client email address entity.
     * @param client Client postal address entity.
     * @return Copied client postal address entity.
     * @throws PostalAddressException Thrown to indicate an error occurred when trying to copy a postal address.
     */
    public static ClientPostalAddress copy(ClientPostalAddress client) throws PostalAddressException
    {
        try
        {
            return AbstractPostalAddressMapper.INSTANCE.copy(client, new CycleAvoidingMappingContext());
        }
        catch (Exception e)
        {
            throw new PostalAddressException(e);
        }
    }
}
