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
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.AbstractServerAuditEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.AbstractServerStatusEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.EntityComparator;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.specification.GenericSpecification;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.EmailAddressServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.EmailAddressRepository;
import com.hemajoo.commerce.cherry.backend.shared.base.search.criteria.SearchCriteria;
import com.hemajoo.commerce.cherry.backend.shared.base.search.criteria.SearchOperation;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.AddressType;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressException;
import com.hemajoo.commerce.cherry.backend.shared.person.address.email.EmailAddressSearch;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ReferenceChange;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DocumentService documentService;

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
    public EmailAddressServer findById(UUID id)
    {
        return emailAddressRepository.findById(id).orElse(null);
    }

    @Override
    public EmailAddressServer update(EmailAddressServer emailAddress) throws EmailAddressException
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
    public List<EmailAddressServer> search(final @NonNull EmailAddressSearch search)
    {
        GenericSpecification<EmailAddressServer> specification = new GenericSpecification<>();

        // Inherited fields
        if (search.getCreatedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_CREATED_BY,
                    search.getCreatedBy(),
                    SearchOperation.MATCH));
        }
        if (search.getModifiedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_MODIFIED_BY,
                    search.getModifiedBy(),
                    SearchOperation.MATCH));
        }

        if (search.getId() != null)
        {
            specification.add(new SearchCriteria(
                    ServerEntity.FIELD_ID,
                    UUID.fromString(search.getId().toString()),
                    SearchOperation.EQUAL));
        }

        if (search.getIsDefaultEmail() != null)
        {
            specification.add(new SearchCriteria(
                    EmailAddressServer.FIELD_IS_DEFAULT,
                    search.getIsDefaultEmail(),
                    SearchOperation.EQUAL));
        }

        if (search.getAddressType() != null)
        {
            specification.add(new SearchCriteria(
                    EmailAddressServer.FIELD_ADDRESS_TYPE,
                    search.getAddressType(),
                    SearchOperation.EQUAL));
        }

        if (search.getStatusType() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerStatusEntity.FIELD_STATUS_TYPE,
                    search.getStatusType(),
                    SearchOperation.EQUAL));
        }

        if (search.getEmail() != null)
        {
            specification.add(new SearchCriteria(
                    EmailAddressServer.FIELD_EMAIL,
                    search.getEmail(),
                    SearchOperation.MATCH));
        }

//        if (search.getPersonId() != null)
//        {
//            specification.add(new SearchCriteria(
//                    ServerEmailAddressEntity.FIELD_PERSON,
//                    search.getPersonId(),
//                    SearchOperation.EQUAL_OBJECT_UUID));
//        }

//        if (specification.count() == 0)
//        {
//            specification.add(new SearchCriteria(
//                    ServerEmailAddressEntity.FIELD_PERSON,
//                    "00000000-0000-0000-0000-000000000000",
//                    SearchOperation.EQUAL_OBJECT_UUID));
//        }

        return emailAddressRepository.findAll(specification);
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
                    document.getOwner().getEntityType(),
                    document.getOwner().getId(),
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
                case EmailAddressServer.FIELD_EMAIL:
                    target.setEmail(source.getEmail());
                    break;

                case EmailAddressServer.FIELD_ADDRESS_TYPE:
                    target.setAddressType(source.getAddressType());
                    break;

                case EmailAddressServer.FIELD_DESCRIPTION:
                    target.setDescription(source.getDescription());
                    break;

                case EmailAddressServer.FIELD_STATUS_TYPE:
                    target.setStatusType(source.getStatusType());
                    break;

                case EmailAddressServer.FIELD_REFERENCE:
                    target.setReference(source.getReference());
                    break;

                case EmailAddressServer.FIELD_IS_DEFAULT:
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
