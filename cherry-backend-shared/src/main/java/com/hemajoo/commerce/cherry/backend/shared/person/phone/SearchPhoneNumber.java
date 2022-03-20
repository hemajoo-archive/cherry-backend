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
package com.hemajoo.commerce.cherry.backend.shared.person.phone;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.search.BaseSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a search object for the <b>phone number</b> entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Schema(name = "PhoneNumberSearch", description = "Specification object used to search for phone numbers.")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchPhoneNumber extends BaseSearch
{
    /**
     * Phone number.
     */
    @Schema(description = "Number"/*, allowEmptyValue = true*/)
    private String number;

    /**
     * Phone number country code (ISO Alpha-3 code).
     */
    @Schema(name = "Country code", description = "Iso Alpha-3 code."/*, allowEmptyValue = true*/)
    private String countryCode;

    /**
     * Phone number type.
     */
    @Enumerated(EnumType.STRING)
    @Schema(name = "phoneType", description = "Phone type"/*, allowEmptyValue = true*/)
    private PhoneNumberType phoneType;

    /**
     * Phone number category type.
     */
    @Enumerated(EnumType.STRING)
    @Schema(name = "categoryType", description = "Category type"/*, allowEmptyValue = true*/)
    private PhoneNumberCategoryType categoryType;

    /**
     * Is it a default phone number?
     */
    @Schema(description = "Is default"/*, allowEmptyValue = true*/)
    private Boolean isDefault;

    /**
     * The person identifier this phone number belongs to.
     */
    @Schema(description = "Person identifier"/*, allowEmptyValue = true*/)
    private Long personId;

    /**
     * Creates a new phone number search instance.
     */
    public SearchPhoneNumber()
    {
        super(EntityType.PHONE_NUMBER);
    }
}
