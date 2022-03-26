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
package com.hemajoo.commerce.cherry.backend.shared.document;

import com.hemajoo.commerce.cherry.backend.commons.entity.EntityIdentity;
import com.hemajoo.commerce.cherry.backend.commons.type.EntityType;
import com.hemajoo.commerce.cherry.backend.shared.base.search.BaseSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Search object for the <b>document</b> entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Schema(name = "DocumentSearch", description = "Specification object used to search for documents.")
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentSearch extends BaseSearch
{
    /**
     * Document extension.
     */
    @Schema(description = "Document extension")
    private String extension;

    /**
     * Document tags.
     */
    @Schema(description = "Document tags")
    private String tags;

    /**
     * Document filename.
     */
    @Schema(description = "Document filename")
    private String filename;

    /**
     * Document filename.
     */
    @Schema(description = "Document content length")
    private Long contentLength;

    /**
     * Document mime type.
     */
    @Schema(description = "Document mime type")
    private String mimeType;

    /**
     * Document content path.
     */
    @Schema(description = "Document content path")
    private String contentPath;

    /**
     * Document owner identity.
     */
    @Schema(description = "Document owner identity")
    private EntityIdentity ownerIdentity;

    /**
     * Creates a new document search instance.
     */
    public DocumentSearch()
    {
        super(EntityType.DOCUMENT);
    }
}
