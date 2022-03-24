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
package com.hemajoo.commerce.cherry.backend.persistence.person.validation.constraint;

import com.hemajoo.commerce.cherry.backend.persistence.person.validation.validator.EmailAddressValidatorForCreation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validation constraint to be used on field, parameter or local variables used to check
 * if an email address client entity is valid for a creation.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Documented
@Constraint(validatedBy = EmailAddressValidatorForCreation.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailAddressForCreation
{
    /**
     * Returns the message.
     * @return Message.
     */
    String message() default "Person id: '${validatedValue}' does not exist!";

    /**
     * Returns an array of classes for the groups.
     * @return Array of classes.
     */
    Class<?>[] groups() default {};

    /**
     * Returns an array of classes representing the payload.
     * @return Array of classes.
     */
    Class<? extends Payload>[] payload() default {};
}
