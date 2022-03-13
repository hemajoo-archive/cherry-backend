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
package com.hemajoo.commerce.cherry.backend.persistence.document.content;

import com.hemajoo.commerce.cherry.backend.persistence.document.entity.ServerDocumentEntity;
import org.springframework.content.rest.StoreRestResource;
import org.springframework.content.s3.store.S3ContentStore;

/**
 * Content store repository with {@code Amazon S3} support.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@StoreRestResource
public interface S3DocumentStore extends S3ContentStore<ServerDocumentEntity, String>
{
    // Empty.
}