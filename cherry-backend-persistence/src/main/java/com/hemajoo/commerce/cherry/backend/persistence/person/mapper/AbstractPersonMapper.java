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
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.AbstractBaseEntityMapper;
import com.hemajoo.commerce.cherry.backend.persistence.base.mapper.CycleAvoidingMappingContext;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.ServerPersonEntity;
import com.hemajoo.commerce.cherry.backend.shared.person.ClientPerson;
import com.hemajoo.commerce.cherry.backend.shared.person.PersonException;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import javax.persistence.EntityManager;

/**
 * Mapper interface to convert between instances of client and server persons.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Mapper(uses = { AbstractBaseEntityMapper.class })
public abstract class AbstractPersonMapper
{
    /**
     * Instance to this bean mapper.
     */
    public static final AbstractPersonMapper INSTANCE = Mappers.getMapper(AbstractPersonMapper.class);

    /**
     * Maps from a server person entity to an entity identity.
     * @param entity Server person entity.
     * @param context Context object.
     * @return Entity identity.
     */
    public abstract EntityIdentity fromServerToIdentity(ServerPersonEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Maps from a server person entity to a client person entity.
     * @param entity Server person entity.
     * @param context Context object.
     * @param entityManager Entity manager.
     * @return Client person entity.
     * @throws PersonException Thrown to indicate an error occurred while trying to convert a person entity.
     */
    public abstract ServerPersonEntity fromClientToServer(ClientPerson entity, @Context CycleAvoidingMappingContext context, @Context EntityManager entityManager) throws Exception;

    /**
     * Maps from a server person entity to a client person entity.
     * @param entity Server person entity.
     * @param context Context object.
     * @return Client person entity.
     */
    public abstract ClientPerson fromServerToClient(ServerPersonEntity entity, @Context CycleAvoidingMappingContext context);

    /**
     * Copy a server person entity.
     * @param entity Server person entity.
     * @param context Context object.
     * @return Copy of the server person entity.
     * @throws PersonException Thrown to indicate an error occurred while trying to copy a person entity.
     */
    public abstract ServerPersonEntity copy(ServerPersonEntity entity, @Context CycleAvoidingMappingContext context) throws PersonException;

    /**
     * Copy a client person entity.
     * @param entity Client person entity.
     * @param context Context object.
     * @return Copy of the client person entity.
     * @throws PersonException Thrown to indicate an error occurred while trying to copy a person entity.
     */
    public abstract ClientPerson copy(ClientPerson entity, @Context CycleAvoidingMappingContext context) throws PersonException;
}
