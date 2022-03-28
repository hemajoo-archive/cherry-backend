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
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PostalAddressServer;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.AddressType;
import com.hemajoo.commerce.cherry.backend.shared.person.address.postal.PostalAddressQuery;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Postal address persistence service behavior.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IPostalAddressService
{
    /**
     * Returns the number of postal addresses.
     * @return Number of postal addresses.
     */
    Long count();

    /**
     * Finds an postal address given its identifier.
     * @param id Postal address identifier.
     * @return Postal address if found, null otherwise.
     */
    PostalAddressServer findById(UUID id);

    /**
     * Saves a postal address.
     * @param postalAddress Postal address.
     * @return Saved postal address.
     */
    PostalAddressServer save(PostalAddressServer postalAddress);

    /**
     * Deletes a postal address given its identifier.
     * @param id Postal address identifier.
     */
    void deleteById(UUID id);

    /**
     * Returns the postal addresses.
     * @return List of postal addresses.
     */
    List<PostalAddressServer> findAll();

    /**
     * Returns a list of postal addresses given an address type.
     * @param type Address type.
     * @return List of matching postal addresses.
     */
    List<PostalAddressServer> findByAddressType(AddressType type);

    /**
     * Returns a list of postal addresses given a status type.
     * @param status Status type.
     * @return List of matching postal addresses.
     */
    List<PostalAddressServer> findByStatus(StatusType status);

    /**
     * Returns a list of default or not default postal addresses.
     * @param isDefault Is it a default postal address?
     * @return List of matching postal addresses.
     */
    List<PostalAddressServer> findByIsDefault(boolean isDefault);

    /**
     * Returns a list of postal addresses belonging to a person.
     * @param personId Person identifier.
     * @return List of matching email addresses.
     */
    List<PostalAddressServer> findByPersonId(UUID personId);

    /**
     * Returns the postal addresses matching the given set of predicates.
     * @param postalAddress Postal address search object containing the predicates.
     * @return List of postal addresses matching the given predicates.
     */
    List<PostalAddressServer> search(final @NonNull PostalAddressQuery postalAddress) throws QueryConditionException;
}
