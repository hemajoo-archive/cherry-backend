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

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Component;

/**
 * Document store.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@Component
public class DocumentStore
{
    @Getter
    @Value("${spring.content.storage.type}")
    private String springContentStoreType;

    /**
     * Content store type to use.
     */
    private DocumentStoreType storeType = DocumentStoreType.UNKNOWN;

    /**
     * <b>File system</b> content store.
     */
    @Autowired
    private IDocumentStoreFileSystem storeFileSystem;

    /**
     * <b>Amazon S3</b> content store.
     */
    @Autowired(required = false)
    private IDocumentStoreS3 storeS3;

    /**
     * Returns the content store.
     * @return Content store.
     */
    @SuppressWarnings("java:S3740")
    public final ContentStore getStore()
    {
        if (storeType == DocumentStoreType.UNKNOWN)
        {
            computeDocumentStoreSelection();
        }

        return storeType == DocumentStoreType.FILESYSTEM ? storeFileSystem : storeS3;
    }

    /**
     * Computes the document store type to use according to the configuration.
     */
    private void computeDocumentStoreSelection()
    {
        // If not set in configuration, then defaulting to FileSystem.
        if (springContentStoreType == null)
        {
            LOGGER.info("Defaulting to a content store type: FileSystem");
            storeType = DocumentStoreType.FILESYSTEM;
        }
        else
        {
            if (springContentStoreType.equals("s3"))
            {
                LOGGER.info("Using a content store of type: S3");
                storeType = DocumentStoreType.S3;
            }
            else if (springContentStoreType.equals("filesystem"))
            {
                LOGGER.info("Using a content store of type: FileSystem");
                storeType = DocumentStoreType.FILESYSTEM;
            }
        }
    }
}
