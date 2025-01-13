package com.google.gson;

import java.util.Objects;

public class JsonString extends JsonElement {
    private final String value;

    public JsonString(String value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public JsonString deepCopy() {
        return this;
    }

    @Override
    public String getAsString() {
        return value;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JsonString that = (JsonString) obj;
        return value.equals(that.value);
    }
}

