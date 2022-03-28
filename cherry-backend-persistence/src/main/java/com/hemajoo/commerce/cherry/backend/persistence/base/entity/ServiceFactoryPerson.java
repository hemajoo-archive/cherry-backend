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
package com.hemajoo.commerce.cherry.backend.persistence.base.entity;

import com.hemajoo.commerce.cherry.backend.persistence.document.repository.IDocumentService;
import com.hemajoo.commerce.cherry.backend.persistence.person.service.IEmailAddressService;
import com.hemajoo.commerce.cherry.backend.persistence.person.service.IPersonService;
import com.hemajoo.commerce.cherry.backend.persistence.person.service.IPhoneNumberService;
import com.hemajoo.commerce.cherry.backend.persistence.person.service.IPostalAddressService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Person service factory providing access to persistence services of the person domain.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Component
public class ServiceFactoryPerson
{
    /**
     * Person persistence service.
     */
    @Getter
    @Autowired
    private IPersonService personService;

    /**
     * Document persistence service.
     */
    @Getter
    @Autowired
    private IDocumentService documentService;

    /**
     * Email persistence service.
     */
    @Getter
    @Autowired
    private IEmailAddressService emailAddressService;

    /**
     * Phone number persistence service.
     */
    @Getter
    @Autowired
    private IPhoneNumberService phoneNumberService;

    /**
     * Postal address persistence service.
     */
    @Getter
    @Autowired
    private IPostalAddressService postalAddressService;
}
