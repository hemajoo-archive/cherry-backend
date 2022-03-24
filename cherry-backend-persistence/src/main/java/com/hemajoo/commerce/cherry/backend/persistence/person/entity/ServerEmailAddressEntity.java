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
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.IServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerBaseEntity;
import com.hemajoo.commerce.cherry.backend.shared.person.address.AddressType;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.IEmailAddress;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Represents a server side email address entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
//@GroupSequence( { ServerEmailAddressEntity.class, BasicValidation.class, ExtendedValidation.class } )
@Log4j2
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "EMAIL_ADDRESS")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ServerEmailAddressEntity extends ServerBaseEntity implements IEmailAddress, IServerEntity
{
    /**
     * Property used to set a search criteria for the <b>email</b> field.
     */
    public static final String FIELD_EMAIL = "email";

    /**
     * Property used to set a search criteria for the <b>is default email</b> field.
     */
    public static final String FIELD_IS_DEFAULT = "isDefaultEmail";

    /**
     * Property used to set a search criteria for the <b>address type</b> field.
     */
    public static final String FIELD_ADDRESS_TYPE = "addressType";

    //public static final String FIELD_PERSON         = "person";

    /**
     * Email address.
     */
    @Getter
    @Setter
    @NotNull(message = "Email address: 'email' cannot be null!")
    @Email(message = "Email address: '${validatedValue}' is not a valid email!")
    @Column(name = "EMAIL")
    private String email;

    /**
     * Is it the default email address?
     */
    @Getter
    @Setter
    @Column(name = "IS_DEFAULT", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDefaultEmail;

    /**
     * Email type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ADDRESS_TYPE")
    private AddressType addressType;

//    /**
//     * The person identifier this email address belongs to.
//     */
//    @Getter
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @JsonIgnoreProperties
//    @ManyToOne(targetEntity = ServerPersonEntity.class, fetch = FetchType.EAGER)
//    @JoinColumn(name = "PERSON_ID", nullable = true)
//    private ServerPersonEntity person;

    /**
     * Creates a new persistent email address.
     */
    public ServerEmailAddressEntity()
    {
        super(EntityType.EMAIL_ADDRESS);
    }

//    @Override
//    public void setParent(ServerBaseEntity parent) throws EmailAddressException
//    {
//        try
//        {
//            super.setParent(parent);
//        }
//        catch (Exception e)
//        {
//            throw new EmailAddressException(e);
//        }
//    }
}
