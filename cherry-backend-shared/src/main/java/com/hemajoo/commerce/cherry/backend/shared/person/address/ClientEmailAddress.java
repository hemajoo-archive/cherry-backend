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
package com.hemajoo.commerce.cherry.backend.shared.person.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.entity.ClientBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a <b>client email address entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString(callSuper = true)
//@Builder(setterPrefix = "with") // Does not work well with MapStruct!
@EqualsAndHashCode(callSuper = true)
public class ClientEmailAddress extends ClientBaseEntity implements IClientEmailAddress
{
    /**
     * Email address.
     */
    @JsonProperty("email")
    @Schema(name = "email", description = "Email address", example = "joe.doe@gmail.com")
    //@Email(message = "email: '${validatedValue}' is not a valid email!")
    private String email;

    /**
     * Is it the default email address?
     */
    @JsonProperty("isDefault")
    @Schema(name = "defaultEmail", description = "Is it the default email address", example = "true")
    private Boolean isDefaultEmail;

    /**
     * Email address type.
     */
    @JsonProperty("addressType")
    @Schema(name = "addressType", description = "Address type", example = "PRIVATE")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

//    /**
//     * The person identifier this email address belongs to.
//     */
//    @JsonProperty("person")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    //@JsonIgnoreProperties("emailAddresses")
//    @ApiModelProperty(name = "person", notes = "Person this email address belongs to", value = "1")
//    private EntityIdentity person; // TODO Could it be moved to base entity?

    /**
     * Creates a new client email address entity.
     */
    public ClientEmailAddress()
    {
        super(EntityType.EMAIL_ADDRESS);
    }
}
