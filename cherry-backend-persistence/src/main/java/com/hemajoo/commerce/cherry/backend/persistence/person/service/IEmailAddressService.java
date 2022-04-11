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
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.EmailAddressServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.EmailAddressRepository;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.AddressType;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressQuery;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Email address persistence service behavior.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IEmailAddressService
{
    /**
     * Email address repository.
     * @return Repository.
     */
    EmailAddressRepository getRepository();

    /**
     * Return the total number of email addresses.
     * @return Total number of email addresses.
     */
    Long count();

    /**
     * Return the email address matching the given identifier.
     * @param id Email address identifier.
     * @return Email address.
     */
    EmailAddressServer findById(UUID id) throws DocumentException;

    /**
     * Update the given server email address entity.
     * @param emailAddress Server email address entity to update.
     * @return Updated server email address entity.
     * @throws EmailAddressException Thrown in case an error occurred while trying to update the server email address entity.
     */
    EmailAddressServer update(final EmailAddressServer emailAddress) throws EmailAddressException, DocumentException;

    /**
     * Save the given email address.
     * @param emailAddress Email address.
     * @return Saved email address.
     * @throws EmailAddressException Thrown in case an error occurred while trying to save the email address.
     */
    EmailAddressServer save(EmailAddressServer emailAddress) throws EmailAddressException;

    /**
     * Save and flush a email address.
     * @param emailAddress Email address.
     * @return Email address.
     * @throws EmailAddressException Thrown in case an error occurred while trying to save the email address.
     */
    EmailAddressServer saveAndFlush(EmailAddressServer emailAddress) throws EmailAddressException;

    /**
     * Delete the email address matching the given identifier.
     * @param id Email address identifier.
     */
    void deleteById(UUID id);

    /**
     * Return all email addresses.
     * @return List of email addresses.
     */
    List<EmailAddressServer> findAll();

    /**
     * Return the list of email addresses matching the given address type.
     * @param type Address type.
     * @return List of email addresses.
     */
    List<EmailAddressServer> findByAddressType(AddressType type);

    /**
     * Return the list of email addresses matching the given status type.
     * @param status Status type.
     * @return List of email addresses.
     */
    List<EmailAddressServer> findByStatus(StatusType status);

    /**
     * Return the list of default or not default email addresses.
     * @param isDefaultEmail Is it the default email address?
     * @return List of matching email addresses.
     */
    List<EmailAddressServer> findByIsDefaultEmail(Boolean isDefaultEmail);

    /**
     * Return the list of email addresses belonging to a given parent identifier.
     * @param parentId Parent identifier.
     * @return List of matching email addresses.
     */
    List<EmailAddressServer> findByParentId(UUID parentId);

    /**
     * Return the email addresses matching the given set of predicates.
     * @param emailAddress Email address search object containing the predicates.
     * @return List of email addresses matching the given predicates.
     */
    List<EmailAddressServer> search(final @NonNull EmailAddressQuery emailAddress) throws QueryConditionException;

    /**
     * Return the documents belonging to the given email address.
     * @param emailAddressId Email address identifier.
     * @return List of documents.
     * @throws QueryConditionException Thrown to indicate an error occurred when retrieving a list of documents.
     */
    List<DocumentServer> findDocuments(final @NonNull UUID emailAddressId) throws QueryConditionException;
}

