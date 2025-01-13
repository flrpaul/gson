package com.google.gson;

import java.util.Objects;

public class JsonCharacter extends JsonElement {
    private final String value;

    public JsonCharacter(Character value) {
        this.value = Objects.requireNonNull(value).toString();
    }

    @Override
    public JsonCharacter deepCopy() {
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
        JsonCharacter that = (JsonCharacter) obj;
        return value.equals(that.value);
    }
}

