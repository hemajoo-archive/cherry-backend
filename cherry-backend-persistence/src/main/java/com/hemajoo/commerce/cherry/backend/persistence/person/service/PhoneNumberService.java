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
import com.hemajoo.commerce.cherry.backend.persistence.person.repository.PhoneNumberRepository;
import com.hemajoo.commerce.cherry.backend.shared.base.query.condition.QueryConditionException;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberCategoryType;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberQuery;
import com.hemajoo.commerce.cherry.backend.shared.person.phone.PhoneNumberType;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    public List<PhoneNumberServer> search(@NonNull PhoneNumberQuery search) throws QueryConditionException
    {
        return phoneNumberRepository.findAll((Specification<PhoneNumberServer>) search.getSpecification());
    }
}
