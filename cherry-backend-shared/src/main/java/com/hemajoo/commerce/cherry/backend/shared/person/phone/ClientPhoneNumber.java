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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hemajoo.commerce.cherry.backend.commons.entity.Identity;
import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.ClientBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a <b>client phone number entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientPhoneNumber extends ClientBaseEntity implements IClientPhoneNumber
{
    /**
     * Phone number.
     */
    @Schema(name = "number", description = "Phone number", example = "0652897412")
    private String number;

    /**
     * Phone number country code (ISO Alpha-3 code).
     */
    @Schema(name = "countryCode", description = "Phone number country code (ISO Alpha-3)", example = "FRA")
    private String countryCode;

    /**
     * Phone number type.
     */
    @Enumerated(EnumType.STRING)
    @Schema(name = "phoneType", description = "Phone number type", example = "PRIVATE")
    private PhoneNumberType phoneType;

    /**
     * Phone number category type.
     */
    @Enumerated(EnumType.STRING)
    @Schema(name = "categoryType", description = "Phone number category type", example = "MOBILE")
    private PhoneNumberCategoryType categoryType;

    /**
     * Is it a default phone number?
     */
    @Schema(name = "isDefault", description = "Is it the default phone number?", example = "true")
    private Boolean isDefault;

    /**
     * The entity identity this phone number belongs to.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("phoneNumbers")
    @Schema(name = "owner", description = "Entity identity this phone number belongs to", example = "1")
    private Identity owner;

    /**
     * Creates a new phone number.
     */
    public ClientPhoneNumber()
    {
        super(EntityType.PHONE_NUMBER);
    }
}
