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
package com.hemajoo.commerce.cherry.backend.shared.person.address.postal;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.search.BaseSearch;
import com.hemajoo.commerce.cherry.backend.shared.person.address.AddressType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Represents a search object for the <b>postal address</b> entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Schema(name = "PostalAddressSearch", description = "Specification object used to search for postal addresses.")
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchPostalAddress extends BaseSearch
{
    /**
     * Postal address street name.
     */
    @Schema(description = "Street name"/*, allowEmptyValue = true*/)
    private String streetName;

    /**
     * Postal address street number.
     */
    @Schema(description = "Street number"/*, allowEmptyValue = true*/)
    private String streetNumber;

    /**
     * Postal address locality.
     */
    @Schema(description = "Locality"/*, allowEmptyValue = true*/)
    private String locality;

    /**
     * Postal address country code (ISO Alpha-3 code).
     */
    @Schema(description = "ISO Alpha-3 code"/*, allowEmptyValue = true*/)
    private String countryCode;

    /**
     * Postal address zip (postal) code.
     */
    @Schema(description = "Postal code"/*, allowEmptyValue = true*/)
    private String zipCode;

    /**
     * Postal address area/region/department depending on the country.
     */
    @Schema(description = "Area/Region"/*, allowEmptyValue = true*/)
    private String area;

    /**
     * Is it a default postal address?
     */
    @Schema(description = "Is default"/*, allowEmptyValue = true*/)
    private Boolean isDefault;

    /**
     * Postal address type.
     */
    @Enumerated(EnumType.STRING)
    @Schema(name = "addressType", description = "Address type"/*, allowEmptyValue = true*/)
    private AddressType addressType;

    /**
     * Postal address category.
     */
    @Enumerated(EnumType.STRING)
    @Schema(name = "categoryType", description = "Address category type"/*, allowEmptyValue = true*/)
    private PostalAddressCategoryType categoryType;

    /**
     * The person identifier this postal address belongs to.
     */
    @Schema(name = "Person identifier"/*, allowEmptyValue = true*/)
    private Long personId;

    /**
     * Creates a new postal address search instance.
     */
    public SearchPostalAddress()
    {
        super(EntityType.POSTAL_ADDRESS);
    }
}
