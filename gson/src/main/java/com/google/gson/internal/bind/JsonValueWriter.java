package com.google.gson.internal.bind;

import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

public class JsonValueWriter {
    private final JsonStructureManager structureManager;

    public JsonValueWriter(JsonStructureManager structureManager) {
        this.structureManager = structureManager;
    }

    public void writeValue(String value) {
        structureManager.put(new JsonPrimitive(value), null);
    }

    public void writeValue(boolean value) {
        structureManager.put(new JsonPrimitive(value), null);
    }

    public void writeValue(Boolean value) {
        if (value == null) {
            structureManager.put(JsonNull.INSTANCE, null);
        } else {
            structureManager.put(new JsonPrimitive(value), null);
        }
    }

    public void writeValue(float value) {
        if (Float.isNaN(value) || Float.isInfinite(value)) {
            throw new IllegalArgumentException("JSON forbids NaN and infinities: " + value);
        }
        structureManager.put(new JsonPrimitive(value), null);
    }

    public void writeValue(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("JSON forbids NaN and infinities: " + value);
        }
        structureManager.put(new JsonPrimitive(value), null);
    }

    public void writeValue(long value) {
        structureManager.put(new JsonPrimitive(value), null);
    }

    public void writeValue(Number value) {
        if (value == null) {
            structureManager.put(JsonNull.INSTANCE, null);
        } else {
            structureManager.put(new JsonPrimitive(value), null);
        }
    }
}

