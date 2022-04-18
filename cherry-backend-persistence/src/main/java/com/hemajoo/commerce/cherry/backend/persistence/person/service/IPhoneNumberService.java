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
package com.hemajoo.commerce.cherry.backend.persistence.person.service;

import com.hemajoo.commerce.cherry.backend.commons.type.StatusType;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PhoneNumberServer;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberQuery;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberType;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Phone number persistence service behavior.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IPhoneNumberService
{
    /**
     * Returns the number of phone numbers.
     * @return Number of phone numbers.
     */
    Long count();

    /**
     * Finds a phone number given its identifier.
     * @param id Phone number identifier.
     * @return Phone number if found, null otherwise.
     */
    PhoneNumberServer findById(UUID id);

    /**
     * Saves a phone number.
     * @param phoneNumber Phone number.
     * @return Saved phone number.
     */
    PhoneNumberServer save(PhoneNumberServer phoneNumber);

    /**
     * Saves and flush a phone number.
     * @param phoneNumber Phone number.
     * @return Saved phone number.
     */
    PhoneNumberServer saveAndFlush(PhoneNumberServer phoneNumber);

    /**
     * Deletes a phone number given its identifier.
     * @param id Phone number identifier.
     */
    void deleteById(UUID id);

    /**
     * Returns the phone numbers.
     * @return List of phone numbers.
     */
    List<PhoneNumberServer> findAll();

    /**
     * Returns a list of phone numbers given a phone number type.
     * @param type Phone number type.
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServer> findByPhoneType(PhoneNumberType type);

    /**
     * Returns a list of phone numbers given a phone number category type.
     * @param category Phone number category type.
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServer> findByCategoryType(PhoneNumberCategoryType category);

    /**
     * Returns a list of phone numbers given a status type.
     * @param status Status type.
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServer> findByStatus(StatusType status);

    /**
     * Returns a list of default or not default phone numbers.
     * @param isDefault Is it a default phone number?
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServer> findByIsDefault(boolean isDefault);

    /**
     * Returns a list of phone numbers belonging to a person.
     * @param personId Person identifier.
     * @return List of matching phone numbers.
     */
    List<PhoneNumberServer> findByPersonId(long personId);

    /**
     * Returns the phone numbers matching the given set of predicates.
     * @param phoneNumber Phone number search object containing the predicates.
     * @return List of phone numbers matching the given predicates.
     */
    List<PhoneNumberServer> search(final @NonNull PhoneNumberQuery phoneNumber) throws QueryConditionException;
}
