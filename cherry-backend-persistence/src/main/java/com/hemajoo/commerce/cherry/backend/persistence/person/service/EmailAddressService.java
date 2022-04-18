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
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.EntityComparator;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.repository.IDocumentService;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.EmailAddressServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.EmailAddressRepository;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.document.exception.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.AddressType;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressQuery;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ReferenceChange;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the email address persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Validated
@Service
@Log4j2
public class EmailAddressService implements IEmailAddressService
{
    /**
     * Email address repository.
     */
    @Autowired
    @Getter
    private EmailAddressRepository emailAddressRepository;

    /**
     * Person service.
     */
    @Autowired
    private IPersonService personService;

    /**
     * Document (content store) service.
     */
    @Autowired
    private IDocumentService documentService;

    @Override
    public EmailAddressRepository getRepository()
    {
        return emailAddressRepository;
    }

    @Override
    public Long count()
    {
        return emailAddressRepository.count();
    }

    @Override
    public EmailAddressServer findById(UUID id) throws DocumentException
    {
        EmailAddressServer emailAddress = emailAddressRepository.findById(id).orElse(null);
        if (emailAddress != null)
        {
            for (DocumentServer document : findDocuments(id))
            {
                emailAddress.addDocument(document);
            }
        }

        return emailAddress;
    }

    @Override
    public EmailAddressServer update(EmailAddressServer emailAddress) throws EmailAddressException, DocumentException
    {
        EmailAddressServer original = findById(emailAddress.getId());
        return save(merge(emailAddress, original));
    }

    //@Transactional
    @Override
    public EmailAddressServer save(final @NonNull EmailAddressServer emailAddress) throws EmailAddressException
    {
        emailAddressRepository.save(emailAddress);

        // Save the documents attached to the email address.
        if (emailAddress.getDocuments() != null)
        {
            for (DocumentServer document : emailAddress.getDocuments())
            {
                try
                {
                    saveDocumentContent(document);
                }
                catch (DocumentException e)
                {
                    throw new EmailAddressException(e);
                }
            }
        }

        return emailAddress;
    }

    @Override
    public EmailAddressServer saveAndFlush(EmailAddressServer emailAddress) throws EmailAddressException
    {
        emailAddress = save(emailAddress);

        emailAddressRepository.flush();

        return emailAddress;
    }

    @Override
    public void deleteById(UUID id)
    {
        emailAddressRepository.deleteById(id);
    }

    @Override
    public List<EmailAddressServer> findAll()
    {
        return emailAddressRepository.findAll();
    }

    @Override
    public List<EmailAddressServer> findByAddressType(final AddressType type)
    {
        return emailAddressRepository.findByAddressType(type);
    }

    @Override
    public List<EmailAddressServer> findByStatus(final StatusType status)
    {
        return emailAddressRepository.findByStatusType(status);
    }

    @Override
    public List<EmailAddressServer> findByIsDefaultEmail(final Boolean isDefaultEmail)
    {
        return emailAddressRepository.findByIsDefaultEmail(isDefaultEmail);
    }

    @Override
    public List<EmailAddressServer> findByParentId(final UUID parentId)
    {
        return emailAddressRepository.findByParentId(parentId);
    }

    @Override
    public List<DocumentServer> findDocuments(final @NonNull UUID emailAddressId)
    {
        return documentService.findByParentId(emailAddressId);
    }

    @Override
    public List<EmailAddressServer> search(final @NonNull EmailAddressQuery search) throws QueryConditionException
    {
        return emailAddressRepository.findAll((Specification<EmailAddressServer>) search.getSpecification());
    }

    /**
     * Save the document content to the content store.
     * @param document Document.
     * @throws DocumentException Thrown if an error occurred while trying to save the document content to the content store.
     */
    private void saveDocumentContent(final @NonNull DocumentServer document) throws DocumentException
    {
        try
        {
            if (document.getContentId() == null) // Not stored in the content store.
            {
                documentService.save(document);
            }
        }
        catch (Exception e)
        {
            LOGGER.info(String.format("Cannot save document content id: %s for owner of type: %s, identifier: %s due to: %s",
                    document.getContentId(),
                    document.getParent().getEntityType(),
                    document.getParent().getId(),
                    e.getMessage()), e);
            throw new DocumentException(e.getMessage());
        }
    }

    private EmailAddressServer merge(final EmailAddressServer source, final EmailAddressServer target) throws EmailAddressException
    {
        Diff diff = EntityComparator.getJavers().compare(source, target);

        // Check if some object fields have changed.
        for (ValueChange change : diff.getChangesByType(ValueChange.class))
        {
            switch (change.getPropertyName())
            {
                case EmailAddressQuery.EMAIL_ADDRESS_EMAIL:
                    target.setEmail(source.getEmail());
                    break;

                case EmailAddressQuery.EMAIL_ADDRESS_TYPE:
                    target.setAddressType(source.getAddressType());
                    break;

//                case EmailAddressServer.FIELD_DESCRIPTION:
//                    target.setDescription(source.getDescription());
//                    break;
//
//                case EmailAddressServer.FIELD_STATUS_TYPE:
//                    target.setStatusType(source.getStatusType());
//                    break;
//
//                case EmailAddressServer.FIELD_REFERENCE:
//                    target.setReference(source.getReference());
//                    break;

                case EmailAddressQuery.EMAIL_ADDRESS_IS_DEFAULT:
                    target.setIsDefaultEmail(source.getIsDefaultEmail());
                    break;

                default:
                    LOGGER.warn(String.format("Property name: %s not handled!", change.getPropertyName()));
                    break;
            }
        }

        // Check if some object references have changed.
        for (ReferenceChange change : diff.getChangesByType(ReferenceChange.class))
        {
            switch (change.getPropertyName())
            {
//                case ServerEmailAddressEntity.FIELD_PERSON:
//                    target.setPerson(source.getPerson());
//                    break;

                default:
                    LOGGER.warn(String.format("Property name: %s not handled!", change.getPropertyName()));
                    break;
            }
        }

        return target;
    }
}
