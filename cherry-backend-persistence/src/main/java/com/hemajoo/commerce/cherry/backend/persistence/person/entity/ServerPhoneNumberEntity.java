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
package com.hemajoo.commerce.cherry.backend.persistence.person.entity;

import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerBaseEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.IPhoneNumber;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Represents a server phone number entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Table(name = "PHONE_NUMBER")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ServerPhoneNumberEntity extends ServerBaseEntity implements IPhoneNumber, ServerEntity
{
    /**
     * Property used to set a search criteria for the <b>is default</b> field.
     */
    public static final String FIELD_IS_DEFAULT = "isDefault";

    /**
     * Property used to set a search criteria for the <b>number</b> field.
     */
    public static final String FIELD_NUMBER = "number";

    /**
     * Property used to set a search criteria for the <b>country code</b> field.
     */
    public static final String FIELD_COUNTRY_CODE = "countryCode";

    /**
     * Property used to set a search criteria for the <b>phone type</b> field.
     */
    public static final String FIELD_PHONE_TYPE = "phoneType";

    /**
     * Property used to set a search criteria for the <b>category type</b> field.
     */
    public static final String FIELD_PHONE_CATEGORY_TYPE = "categoryType";

    /**
     * Property used to set a search criteria for the <b>person identifier</b> field.
     */
    public static final String FIELD_PERSON_ID = "personId";

    /**
     * Phone number.
     */
    @Getter
    @Setter
    @NotNull(message = "Phone number: 'phoneNumber' cannot be null!")
    @Column(name = "PHONE_NUMBER", length = 30)
    private String number;

    /**
     * Phone number country code (ISO Alpha-3 code).
     */
    @Getter
    @Setter
    @NotNull(message = "Phone number: 'countryCode' cannot be null!")
    @Column(name = "COUNTRY_CODE", length = 3)
    private String countryCode;

    /**
     * Phone number type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "PHONE_TYPE")
    private PhoneNumberType phoneType;

    /**
     * Phone number category type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY_TYPE")
    private PhoneNumberCategoryType categoryType;

    /**
     * Is it a default phone number?
     */
    @Getter
    @Setter
    @Column(name = "IS_DEFAULT")
    private Boolean isDefault;

    /**
     * The person identifier this phone number belongs to.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter
    @Setter
    @ManyToOne(targetEntity = ServerPersonEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID", nullable = false)
    private ServerPersonEntity person;

    /**
     * Creates a new phone number.
     */
    public ServerPhoneNumberEntity()
    {
        super(EntityType.PHONE_NUMBER);
    }
}
