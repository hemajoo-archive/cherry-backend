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

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;

/**
 * Provides an entity comparator able to detect objects' changes.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public class EntityComparator
{
    /**
     * Entity version comparator.
     */
    @Getter
    private static final Javers javers = JaversBuilder.javers().build();
}
