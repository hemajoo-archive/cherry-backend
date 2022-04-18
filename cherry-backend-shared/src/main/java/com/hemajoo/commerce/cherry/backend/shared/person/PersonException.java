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
package com.hemajoo.commerce.cherry.backend.shared.person;

import com.hemajoo.commerce.cherry.backend.shared.base.entity.EntityException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * Checked exception thrown to indicate an error occurred with a <b>person</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class PersonException extends EntityException
{
    /**
     * Default serialization identifier.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Http status code.
     */
    @Getter
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    /**
     * Thrown to indicate that an error occurred with a <b>person</b>.
     * @param exception Parent exception.
     */
    public PersonException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Thrown to indicate that an error occurred with a <b>person</b>.
     * @param message Message describing the error being the cause of the raised exception.
     */
    public PersonException(final String message)
    {
        super(message);
    }

    /**
     * Thrown to indicate that an error occurred with a <b>person</b>.
     * @param message Message describing the error being the cause of the raised exception.
     * @param status {@link HttpStatus}.
     */
    public PersonException(final String message, final HttpStatus status)
    {
        super(message);

        this.status = status;
    }

    /**
     * Thrown to indicate that an error occurred with a <b>person</b>.
     * @param message Message describing the error being the cause of the raised exception.
     * @param exception Parent exception.
     */
    public PersonException(final String message, final Exception exception)
    {
        super(message, exception);
    }
}
