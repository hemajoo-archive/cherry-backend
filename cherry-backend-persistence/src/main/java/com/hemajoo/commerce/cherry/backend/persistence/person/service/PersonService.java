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

import com.hemajoo.commerce.cherry.backend.persistence.base.entity.AbstractServerAuditEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.AbstractServerStatusEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.entity.ServerEntity;
import com.hemajoo.commerce.cherry.backend.persistence.base.specification.GenericSpecification;
import com.hemajoo.commerce.cherry.backend.persistence.document.entity.DocumentServer;
import com.hemajoo.commerce.cherry.backend.persistence.document.repository.DocumentService;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.EmailAddressServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PersonServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.EmailAddressRepository;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.PersonRepository;
import com.hemajoo.commerce.cherry.backend.shared.base.search.criteria.SearchCriteria;
import com.hemajoo.commerce.cherry.backend.shared.base.search.criteria.SearchOperation;
import com.hemajoo.commerce.cherry.backend.shared.document.DocumentException;
import com.hemajoo.commerce.cherry.backend.shared.person.PersonSearch;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Person persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Service
public class PersonService implements IPersonService
{
    /**
     * Person repository.
     */
    @Autowired
    @Getter
    private PersonRepository personRepository;

    /**
     * Email address repository.
     */
    @Autowired
    @Getter
    private EmailAddressRepository emailAddressRepository;

    /**
     * Document service.
     */
    @Autowired
    @Getter
    private DocumentService documentService;

//    /**
//     * Postal address service.
//     */
//    @Autowired
//    @Getter
//    private PostalAddressService postalAddressService;
//
//    /**
//     * Phone number service.
//     */
//    @Autowired
//    @Getter
//    private PhoneNumberService phoneNumberService;

    @Override
    public Long count()
    {
        return personRepository.count();
    }

    @Override
    public boolean existId(final @NonNull UUID id)
    {
        return personRepository.existsById(id);
    }

    @Override
    public PersonServer findById(UUID id)
    {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackOn = DocumentException.class)
    public PersonServer save(PersonServer person) throws DocumentException
    {
        if (person.getId() == null)
        {
            person = personRepository.save(person);
        }

        // REMINDER Important to save the underlying collections of entities that hold documents!
        if (person.getDocuments() != null)
        {
            // Save the documents directly attached to the person.
            for (DocumentServer document : person.getDocuments())
            {
                try
                {
                    documentService.save(document);
                }
                catch (Exception e)
                {
                    throw new DocumentException(e.getMessage());
                }
            }
        }

//        // Save the email addresses directly attached to the person.
//        for (ServerEmailAddressEntity email : person.getEmailAddresses())
//        {
//            try
//            {
//                emailAddressService.save(email);
//            }
//            catch (Exception e)
//            {
//                throw new DocumentException(e.getMessage());
//            }
//        }

        return person;
    }

    @Override
    public List<DocumentServer> getDocuments(@NonNull ServerEntity entity)
    {
        return entity.getDocuments();
    }

    @Override
    public PersonServer saveAndFlush(@NonNull PersonServer person)
    {
        return personRepository.saveAndFlush(person);
    }

    @Override
    public void deleteById(UUID id)
    {
        personRepository.deleteById(id);
    }

    @Override
    public List<PersonServer> findAll()
    {
        return personRepository.findAll();
    }

    @Override
    public List<PersonServer> search(@NonNull PersonSearch person)
    {
        GenericSpecification<PersonServer> specification = new GenericSpecification<>();

        // Inherited fields
        if (person.getCreatedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_CREATED_BY,
                    person.getCreatedBy(),
                    SearchOperation.MATCH));
        }

        if (person.getModifiedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_MODIFIED_BY,
                    person.getModifiedBy(),
                    SearchOperation.MATCH));
        }

        if (person.getId() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServer.FIELD_ID,
                    person.getId(),
                    SearchOperation.EQUAL));
        }

        if (person.getPersonType() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServer.FIELD_PERSON_TYPE,
                    person.getPersonType(),
                    SearchOperation.EQUAL));
        }

        if (person.getStatusType() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerStatusEntity.FIELD_STATUS_TYPE,
                    person.getStatusType(),
                    SearchOperation.EQUAL));
        }

        if (person.getGenderType() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServer.FIELD_GENDER_TYPE,
                    person.getGenderType(),
                    SearchOperation.EQUAL));
        }

        if (person.getBirthDate() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServer.FIELD_BIRTHDATE,
                    person.getBirthDate(),
                    SearchOperation.EQUAL));
        }

        if (person.getLastName() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServer.FIELD_LASTNAME,
                    person.getLastName(),
                    SearchOperation.MATCH));
        }

        if (person.getFirstName() != null)
        {
            specification.add(new SearchCriteria(
                    PersonServer.FIELD_FIRSTNAME,
                    person.getFirstName(),
                    SearchOperation.MATCH));
        }

        return personRepository.findAll(specification);
    }

    @Override
    public List<EmailAddressServer> getEmailAddresses(final @NonNull PersonServer person)
    {
        return emailAddressRepository.findByParentId(person.getId());
    }

//    @Override
//    public ServerPersonEntity loadEmailAddresses(final @NonNull ServerPersonEntity person)
//    {
//        person.setEmailAddresses(getEmailAddresses(person));
//
//        return person;
//    }

//    private void saveDocument(final @NonNull DocumentServerEntity document) throws DocumentException
//    {
//        try
//        {
//            if (document.getContentId() == null) // Not stored in the content store.
//            {
//                documentService.save(document);
//            }
//        }
//        catch (Exception e)
//        {
//            throw new DocumentException(e.getMessage());
//        }
//    }
}

