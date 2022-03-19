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

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.search.BaseSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

/**
 * Represents a search object for the <b>person</b> entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Schema(name = "PersonSearch", description = "Specification object used to search for persons.")
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchPerson extends BaseSearch
{
    /**
     * Person last name.
     */
    @Schema(description = "Last name"/*, allowEmptyValue = true*/)
    private String lastName;

    /**
     * Person first name.
     */
    @Schema(description = "First name"/*, allowEmptyValue = true*/)
    private String firstName;

    /**
     * Person birthdate.
     */
    @Schema(description = "Birth date", format = "YYYY-MM-DD"/*, allowEmptyValue = true*/)
    private LocalDate birthDate;

    /**
     * Person gender type.
     */
    @Enumerated(EnumType.STRING)
    @Schema(name = "genderType", description = "Gender type"/*, allowEmptyValue = true*/)
    private GenderType genderType;

    /**
     * Person type.
     */
    @Enumerated(EnumType.STRING)
    @Schema(name = "personType", description = "Person type"/*, allowEmptyValue = true*/)
    private PersonType personType;

    /**
     * Creates a new person search instance.
     */
    public SearchPerson()
    {
        super(EntityType.PERSON);
    }
}
