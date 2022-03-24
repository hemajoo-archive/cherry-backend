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

import com.hemajoo.commerce.cherry.backend.shared.base.entity.IAuditEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Represents an <b>abstract server audit entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractServerAuditEntity implements IAuditEntity
{
    /**
     * Property used to set a search criteria for the <b>created date</b> field.
     */
    public static final String FIELD_CREATED_DATE = "createdDate";

    /**
     * Property used to set a search criteria for the <b>modified date</b> field.
     */
    public static final String FIELD_MODIFIED_DATE = "modifiedDate";

    /**
     * Property used to set a search criteria for the <b>created by</b> field.
     */
    public static final String FIELD_CREATED_BY = "createdBy";

    /**
     * Property used to set a search criteria for the <b>modified by</b> field.
     */
    public static final String FIELD_MODIFIED_BY = "modifiedBy";

    /**
     * Entity creation date.
     */
    @Getter
    @Setter
    @Column(name = "CREATED_DATE", length = 26)
    @CreatedDate
    private Date createdDate;

    /**
     * Entity modification date.
     */
    @Getter
    @Setter
    @Column(name = "MODIFIED_DATE", length = 26)
    @LastModifiedDate
    private Date modifiedDate;

    /**
     * Entity author.
     */
    @Getter
    @Setter
    @Column(name = "CREATED_BY", length = 50)
    @CreatedBy
    private String createdBy;

    /**
     * Entity last modification author.
     */
    @Getter
    @Setter
    @Column(name = "MODIFIED_BY", length = 50)
    @LastModifiedBy
    private String modifiedBy;
}
