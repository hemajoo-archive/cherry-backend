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
package com.hemajoo.commerce.cherry.backend.shared.base.entity;

import java.io.Serial;

/**
 * Checked exception thrown to indicate an error occurred with an <b>entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class EntityException extends Exception
{
    /**
     * Default serialization identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Thrown to indicate that an error occurred with an <b>entity</b>.
     * @param exception Parent {@link Exception}.
     */
    public EntityException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Thrown to indicate that an error occurred with an <b>entity</b>.
     * @param message Message describing the error being the cause of the raised exception.
     */
    public EntityException(final String message)
    {
        super(message);
    }

    /**
     * Thrown to indicate that an error occurred with an <b>entity</b>.
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent {@link Exception}.
     */
    public EntityException(final String message, final Exception exception)
    {
        super(message, exception);
    }
}
