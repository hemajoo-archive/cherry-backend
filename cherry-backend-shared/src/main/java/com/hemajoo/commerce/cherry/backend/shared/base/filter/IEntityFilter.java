package com.hemajoo.commerce.cherry.backend.shared.base.filter;

import lombok.NonNull;

public interface IEntityFilter
{
    /**
     * Returns if the filters contains the given property name.
     * @param propertyName Property name.
     * @return {@code True} if the filters contains the given property name, {@code false} otherwise.
     */
    boolean contains(final @NonNull String propertyName);
}
