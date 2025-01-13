package com.google.gson;

import java.util.Objects;

public class JsonBoolean extends JsonElement {
    private final Boolean value;

    public JsonBoolean(Boolean value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public JsonBoolean deepCopy() {
        return this;
    }

    public boolean getAsBoolean() {
        return value;
    }

    @Override
    public String getAsString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JsonBoolean that = (JsonBoolean) obj;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

