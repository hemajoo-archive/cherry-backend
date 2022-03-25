package com.hemajoo.commerce.cherry.backend.persistence.base.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entity identifier generator.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class UuidGenerator implements IdentifierGenerator
{
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSession, Object object) throws HibernateException
    {
        return UUID.randomUUID();
    }
}
