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
package com.hemajoo.commerce.cherry.backend.shared.document.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hemajoo.commerce.cherry.backend.shared.base.filter.AbstractFilter;

/**
 * Represents a <b>document content</b> filter object.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class DocumentFilterContent extends AbstractFilter
{
    /**
     * Filter for the document content extension.
     */
    @JsonIgnore
    public static final String DOCUMENT_CONTENT_EXTENSION = "extension";

    /**
     * Filter for the document content filename.
     */
    @JsonIgnore
    public static final String DOCUMENT_CONTENT_FILENAME = "filename";

    /**
     * Filter for the document content path.
     */
    @JsonIgnore
    public static final String DOCUMENT_CONTENT_PATH = "contentPath";

    /**
     * Filter for the document content length.
     */
    @JsonIgnore
    public static final String DOCUMENT_CONTENT_LENGTH = "contentLength";



    @JsonIgnore
    public static final String DOCUMENT_CONTENT_MIMETYPE = "mimeType";

    /**
     * Creates a new <b>document content</b> filter object.
     */
    public DocumentFilterContent()
    {
        filters.add(DOCUMENT_CONTENT_FILENAME);
        filters.add(DOCUMENT_CONTENT_MIMETYPE);
        filters.add(DOCUMENT_CONTENT_LENGTH);
        filters.add(DOCUMENT_CONTENT_PATH);
        filters.add(DOCUMENT_CONTENT_EXTENSION);
    }
}
