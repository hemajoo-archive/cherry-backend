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
package com.hemajoo.commerce.cherry.backend.persistence.person.mapper;

import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.AbstractEntityMapper;
import com.hemajoo.commerce.cherry.backend.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PostalAddressServer;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.postal.PostalAddressClient;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.EntityManager;

/**
 * Mapper interface to convert between instances of client and server postal addresses.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(uses = { AbstractEntityMapper.class })
public abstract class AbstractPostalAddressMapper
{
    /**
     * Instance to this bean mapper.
     */
    public static final AbstractPostalAddressMapper INSTANCE = Mappers.getMapper(AbstractPostalAddressMapper.class);

    /**
     * Maps from a server postal address entity to an entity identity.
     * @param entity Server postal address entity.
     * @param context Context object.
     * @return Entity identity.
     * @throws EntityException Thrown to indicate an error occurred while trying to convert a postal address entity.
     */
    public abstract EntityIdentity fromServerToIdentity(PostalAddressServer entity, @Context CycleAvoidingMappingContext context) throws EntityException;

    /**
     * Maps from a server postal address entity to a client postal address entity.
     * @param entity Server postal address entity.
     * @param context Context object.
     * @param entityManager Entity manager.
     * @return Client postal address entity.
     * @throws EntityException Thrown to indicate an error occurred while trying to convert a postal address entity.
     */
    public abstract PostalAddressServer fromClientToServer(PostalAddressClient entity, @Context CycleAvoidingMappingContext context, @Context EntityManager entityManager) throws EntityException;

    /**
     * Maps from a server postal address entity to a client postal address entity.
     * @param entity Server postal address entity.
     * @param context Context object.
     * @return Client postal address entity.
     */
    public abstract PostalAddressClient fromServerToClient(PostalAddressServer entity, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a server postal address entity.
     * @param entity Server postal address entity.
     * @param context Context object.
     * @return Copy of the server postal address entity.
     * @throws EntityException Thrown to indicate an error occurred while trying to copy a postal address entity.
     */
    public abstract PostalAddressServer copy(PostalAddressServer entity, @Context CycleAvoidingMappingContext context) throws EntityException;

    /**
     * Copy a client postal address entity.
     * @param entity Client postal address entity.
     * @param context Context object.
     * @return Copy of the client postal address entity.
     * @throws EntityException Thrown to indicate an error occurred while trying to copy a postal address entity.
     */
    public abstract PostalAddressClient copy(PostalAddressClient entity, @Context CycleAvoidingMappingContext context) throws EntityException;
}
