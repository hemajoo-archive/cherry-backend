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
import com.hemajoo.commerce.cherry.backend.persistence.base.specification.GenericSpecification;
import com.hemajoo.commerce.cherry.backend.persistence.person.entity.PhoneNumberServer;
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.PhoneNumberRepository;
import com.hemajoo.commerce.cherry.backend.shared.base.search.criteria.SearchCriteria;
import com.hemajoo.commerce.cherry.backend.shared.base.search.criteria.SearchOperation;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberSearch;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberType;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Phone number persistence service.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Service
public class PhoneNumberService implements IPhoneNumberService
{
    /**
     * Repository for the phone numbers.
     */
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;


    @Override
    public Long count()
    {
        return phoneNumberRepository.count();
    }

    @Override
    public PhoneNumberServer findById(UUID id)
    {
        return phoneNumberRepository.findById(id).orElse(null);
    }

    @Override
    public PhoneNumberServer save(PhoneNumberServer phoneNumber)
    {
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public PhoneNumberServer saveAndFlush(PhoneNumberServer phoneNumber)
    {
        return phoneNumberRepository.saveAndFlush(phoneNumber);
    }

    @Override
    public void deleteById(UUID id)
    {
        phoneNumberRepository.deleteById(id);
    }

    @Override
    public List<PhoneNumberServer> findAll()
    {
        return phoneNumberRepository.findAll();
    }

    @Override
    public List<PhoneNumberServer> findByPhoneType(PhoneNumberType type)
    {
        return phoneNumberRepository.findByPhoneType(type);
    }

    @Override
    public List<PhoneNumberServer> findByCategoryType(PhoneNumberCategoryType category)
    {
        return phoneNumberRepository.findByCategoryType(category);
    }

    @Override
    public List<PhoneNumberServer> findByStatus(StatusType status)
    {
        return phoneNumberRepository.findByStatusType(status);
    }

    @Override
    public List<PhoneNumberServer> findByIsDefault(boolean isDefault)
    {
        return phoneNumberRepository.findByIsDefault(isDefault);
    }

    @Override
    public List<PhoneNumberServer> findByPersonId(long personId)
    {
        return phoneNumberRepository.findByPersonId(personId);
    }

    @Override
    public List<PhoneNumberServer> search(@NonNull PhoneNumberSearch phoneNumber)
    {
        GenericSpecification<PhoneNumberServer> specification = new GenericSpecification<>();

        // Inherited fields
        if (phoneNumber.getCreatedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_CREATED_BY,
                    phoneNumber.getCreatedBy(),
                    SearchOperation.MATCH));
        }

        if (phoneNumber.getModifiedBy() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerAuditEntity.FIELD_MODIFIED_BY,
                    phoneNumber.getModifiedBy(),
                    SearchOperation.MATCH));
        }

        if (phoneNumber.getStatusType() != null)
        {
            specification.add(new SearchCriteria(
                    AbstractServerStatusEntity.FIELD_STATUS_TYPE,
                    phoneNumber.getStatusType(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getId() != null)
        {
            specification.add(new SearchCriteria(
                    PhoneNumberServer.FIELD_ID,
                    phoneNumber.getId(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getIsDefault() != null)
        {
            specification.add(new SearchCriteria(
                    PhoneNumberServer.FIELD_IS_DEFAULT,
                    phoneNumber.getIsDefault(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getPhoneType() != null)
        {
            specification.add(new SearchCriteria(
                    PhoneNumberServer.FIELD_PHONE_TYPE,
                    phoneNumber.getPhoneType(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getPersonId() != null)
        {
            specification.add(new SearchCriteria(
                    PhoneNumberServer.FIELD_PERSON_ID,
                    phoneNumber.getPersonId(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getCategoryType() != null)
        {
            specification.add(new SearchCriteria(
                    PhoneNumberServer.FIELD_PHONE_CATEGORY_TYPE,
                    phoneNumber.getCategoryType(),
                    SearchOperation.EQUAL));
        }

        if (phoneNumber.getCountryCode() != null)
        {
            specification.add(new SearchCriteria(
                    PhoneNumberServer.FIELD_COUNTRY_CODE,
                    phoneNumber.getCountryCode(),
                    SearchOperation.MATCH));
        }

        if (phoneNumber.getNumber() != null)
        {
            specification.add(new SearchCriteria(
                    PhoneNumberServer.FIELD_NUMBER,
                    phoneNumber.getNumber(),
                    SearchOperation.MATCH));
        }

        return phoneNumberRepository.findAll(specification);
    }
}
