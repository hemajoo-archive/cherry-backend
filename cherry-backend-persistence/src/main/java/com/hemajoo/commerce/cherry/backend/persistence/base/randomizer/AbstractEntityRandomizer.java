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
package com.hemajoo.commerce.cherry.backend.persistence.base.randomizer;

import com.github.javafaker.Faker;
import com.hemajoo.commerce.cherry.backend.commons.type.StatusType;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityClient;
import lombok.NonNull;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Abstract entity randomizer.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractEntityRandomizer
{
    /**
     * Default dependency bound.
     */
    protected static final int DEFAULT_DEPENDENCY_BOUND = 10;

    /**
     * Faker generator.
     */
    protected static final Faker FAKER = new Faker();

    /**
     * Random number generator.
     */
    protected static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Entity status type enumeration generator.
     */
    protected static final EnumRandomGenerator STATUS_TYPE_GENERATOR = new EnumRandomGenerator(StatusType.class);

    /**
     * Creates a new base entity randomizer.
     */
    protected AbstractEntityRandomizer()
    {
        // Empty
    }

    /**
     * Populates the base persistent entity with random values.
     * @param parent Parent entity.
     */
    public static void populateBaseFields(final @NonNull ServerEntity parent)
    {
        final Instant dateEnd = Instant.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z");
        final ZonedDateTime zonedDateTime = ZonedDateTime.parse("1970-01-01 00:00:00.001 Europe/Paris", formatter);

        String description = FAKER.hitchhikersGuideToTheGalaxy().marvinQuote();
        if (description.length() > 255)
        {
            description = description.substring(1, 255);
        }
        parent.setDescription(description);
        parent.setReference(FAKER.ancient().hero());
        parent.setStatusType((StatusType) STATUS_TYPE_GENERATOR.gen());
        if (parent.getStatusType() == StatusType.INACTIVE)
        {
            parent.setSince(FAKER.date().between(Date.from(zonedDateTime.toInstant()), Date.from(dateEnd)));
        }
        parent.setCreatedBy(FAKER.internet().emailAddress());
        parent.setModifiedBy(FAKER.internet().emailAddress());
        parent.setCreatedDate(FAKER.date().between(Date.from(zonedDateTime.toInstant()), Date.from(dateEnd)));
        parent.setModifiedDate(FAKER.date().between(parent.getCreatedDate(), Date.from(dateEnd)));
    }

    /**
     * Populates the base client entity with random values.
     * @param parent Parent entity.
     */
    public static void populateBaseFields(final @NonNull EntityClient parent)
    {
        final Instant dateEnd = Instant.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z");
        final ZonedDateTime zonedDateTime = ZonedDateTime.parse("1970-01-01 00:00:00.001 Europe/Paris", formatter);

        String description = FAKER.hitchhikersGuideToTheGalaxy().marvinQuote();
        if (description.length() > 255)
        {
            description = description.substring(1, 255);
        }
        parent.setDescription(description);
        parent.setReference(FAKER.ancient().hero());
        parent.setStatusType((StatusType) STATUS_TYPE_GENERATOR.gen());
        if (parent.getStatusType() == StatusType.INACTIVE)
        {
            parent.setSince(FAKER.date().between(Date.from(zonedDateTime.toInstant()), Date.from(dateEnd)));
        }
        parent.setCreatedBy(FAKER.internet().emailAddress());
        parent.setModifiedBy(FAKER.internet().emailAddress());
        parent.setCreatedDate(FAKER.date().between(Date.from(zonedDateTime.toInstant()), Date.from(dateEnd)));
        parent.setModifiedDate(FAKER.date().between(parent.getCreatedDate(), Date.from(dateEnd)));
    }

    /**
     * Returns a random element from a list.
     * @param list List.
     * @param <T> Element type.
     * @return Random element.
     */
    public static <T> T getRandomElement(final List<T> list)
    {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
