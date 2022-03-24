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
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerPhoneNumberEntity;
import com.hemajoo.commerce.cherry.backend.persistence.person.mapper.AbstractPhoneNumberMapper;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.ClientPhoneNumber;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Component to convert between instances of client and server phone numbers.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public final class PhoneNumberConverter
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Converts from a server phone number entity to an entity identity.
     * @param server Server phone number entity.
     * @return Entity identity.
     */
    public EntityIdentity fromServerToIdentity(ServerPhoneNumberEntity server)
    {
        return AbstractPhoneNumberMapper.INSTANCE.fromServerToIdentity(server, new CycleAvoidingMappingContext());
    }

    /**
     * Converts from an entity identity to a server phone number entity.
     * @param identity Entity identity.
     * @return Server phone number entity.
     * @throws PhoneNumberException Thrown to indicate an error occurred when trying to convert a phone number.
     */
    public ServerPhoneNumberEntity fromIdentityToServer(EntityIdentity identity) throws PhoneNumberException
    {
        try
        {
            return AbstractBaseEntityMapper.INSTANCE.map(identity,entityManager);
        }
        catch (Exception e)
        {
            throw new PhoneNumberException(e);
        }
    }

    /**
     * Converts from a client phone number entity to a server phone number entity.
     * @param client Client phone number entity.
     * @return Server phone number entity.
     * @throws PhoneNumberException Thrown to indicate an error occurred when trying to convert a phone number.
     */
    public ServerPhoneNumberEntity fromClientToServer(ClientPhoneNumber client) throws PhoneNumberException
    {
        try
        {
            return AbstractPhoneNumberMapper.INSTANCE.fromClientToServer(client, new CycleAvoidingMappingContext(), entityManager);
        }
        catch (Exception e)
        {
            throw new PhoneNumberException(e);
        }
    }

    /**
     * Converts from a server phone number entity to a client phone number entity.
     * @param server Server phone number entity.
     * @return Client phone number entity.
     */
    public ClientPhoneNumber fromServerToClient(ServerPhoneNumberEntity server)
    {
        return AbstractPhoneNumberMapper.INSTANCE.fromServerToClient(server, new CycleAvoidingMappingContext());
    }

    /**
     * Copy a server phone number entity.
     * @param server Server phone number entity.
     * @return Copied server phone number entity.
     * @throws PhoneNumberException Thrown to indicate an error occurred when trying to copy a phone number.
     */
    public static ServerPhoneNumberEntity copy(ServerPhoneNumberEntity server) throws PhoneNumberException
    {
        try
        {
            return AbstractPhoneNumberMapper.INSTANCE.copy(server, new CycleAvoidingMappingContext());
        }
        catch (Exception e)
        {
            throw new PhoneNumberException(e);
        }
    }

    /**
     * Copy a client phone number entity.
     * @param client Client phone number entity.
     * @return Copied client phone number entity.
     * @throws PhoneNumberException Thrown to indicate an error occurred when trying to copy a phone number.
     */
    public static ClientPhoneNumber copy(ClientPhoneNumber client) throws PhoneNumberException
    {
        try
        {
            return AbstractPhoneNumberMapper.INSTANCE.copy(client, new CycleAvoidingMappingContext());
        }
        catch (Exception e)
        {
            throw new PhoneNumberException(e);
        }
    }
}
