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
package com.hemajoo.commerce.cherry.backend.shared.base.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Represents an <b>abstract client audit entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractEntityAuditClient implements IEntityAudit
{
    /**
     * Entity creation date.
     */
    //@JsonProperty("createdDate")
    @Schema(hidden = true)
    private Date createdDate;

    /**
     * Entity modification date.
     */
    //@JsonProperty("modifiedDate")
    @Schema(hidden = true)
    private Date modifiedDate;

    /**
     * Entity creation author.
     */
    //@JsonProperty("createdBy")
//    @Schema(name = "createdBy", description = "User being the initial author of this entity.", required = false, hidden = true)
    @Schema(hidden = true)
    private String createdBy;

    /**
     * Entity modification author.
     */
    //@JsonProperty("modifiedBy")
//    @Schema(name = "modifiedBy", description = "User being the last who has modified this entity", required = false, hidden = true)
    @Schema(hidden = true)
    private String modifiedBy;
}
