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
package com.hemajoo.commerce.cherry.backend.shared.base.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a search object for audit entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractAuditSearch implements Serializable
{
    /**
     * Creation date.
     */
    @JsonProperty("createdDate")
    @Schema(description = "Creation date", format = "(YYYY-MM-DD)", /*allowEmptyValue = true,*/ hidden = true)
    private Date createdDate;

    /**
     * Modification date.
     */
    @JsonProperty("modifiedDate")
    @Schema(description = "Last modification date", format = "(YYYY-MM-DD)", /*allowEmptyValue = true,*/ hidden = true)
    private Date modifiedDate;

    /**
     * Creation author.
     */
    @JsonProperty("createdBy")
    @Schema(description = "Created by"/*, allowEmptyValue = true*/)
    private String createdBy;

    /**
     * Modification author.
     */
    @JsonProperty("modifiedBy")
    @Schema(description = "Modified by"/*, allowEmptyValue = true*/)
    private String modifiedBy;
}
