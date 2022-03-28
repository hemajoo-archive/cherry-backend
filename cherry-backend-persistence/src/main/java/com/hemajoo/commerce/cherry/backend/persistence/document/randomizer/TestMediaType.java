/*
 * (C) Copyright Kyndryl Corp. 2021 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.backend.persistence.document.randomizer;

import lombok.Getter;
import lombok.NonNull;

/**
 * Enumeration providing the possible values for a test <b>media</b> type.
 * @author <a href="mailto:christophe.resse@kyndryl.com">Christophe Resse</a>
 * @version 1.0.0
 */
public enum TestMediaType
{
    /**
     * Jpeg media file.
     */
    JPG("./media/android-10.jpg"),

    /**
     * Svg media file.
     */
    SVG("./media/canvas.svg"),

    /**
     * Pdf media file.
     */
    PDF("./media/java-8-streams-cheat-sheet.pdf"),

    /**
     * Html media file.
     */
    HTML("./media/license.html"),

    /**
     * Txt media file.
     */
    TXT("./media/sample.txt"),

    /**
     * Png media file.
     */
    PNG("./media/telephone.png");

    /**
     * Media file path.
     */
    @Getter
    private final String path;

    /**
     * Creates a new test media file given its path.
     * @param path Media file path.
     */
    TestMediaType(final @NonNull String path)
    {
        this.path = path;
    }

    /**
     * Creates a new test media file given its path.
     * @param path Media file path.
     * @return Test media file.
     * @throws IllegalArgumentException Thrown in case an error occurred while trying to determine the media file from the given path.
     */
    public static TestMediaType from(final @NonNull String path) throws IllegalArgumentException
    {
        for (TestMediaType type : values())
        {
            if (type.getPath().equals(path))
            {
                return type;
            }
        }

        throw new IllegalArgumentException(String.format("Cannot instantiate test media type from path: %s!", path));
    }
}
