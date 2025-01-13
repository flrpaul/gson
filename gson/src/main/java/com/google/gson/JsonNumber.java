package com.google.gson;

import java.util.Objects;

public class JsonNumber extends JsonElement {
    private final Number value;

    public JsonNumber(Number value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public JsonNumber deepCopy() {
        return this;
    }

    public Number getAsNumber() {
        return value;
    }

    @Override
    public String getAsString() {
        return value.toString();
    }

    @Override
    public double getAsDouble() {
        return value.doubleValue();
    }

    @Override
    public long getAsLong() {
        return value.longValue();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JsonNumber that = (JsonNumber) obj;
        return value.equals(that.value);
    }
}

