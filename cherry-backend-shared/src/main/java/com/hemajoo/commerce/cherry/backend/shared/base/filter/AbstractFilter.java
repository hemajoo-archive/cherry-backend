package com.hemajoo.commerce.cherry.backend.shared.base.filter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFilter implements IEntityFilter
{
    /**
     * List of filters.
     */
    @JsonIgnore
    protected final List<String> filters = new ArrayList<>();

    @Override
    public final boolean contains(final @NonNull String propertyName)
    {
        return filters.contains(propertyName);
    }
}
