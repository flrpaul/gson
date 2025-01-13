package com.google.gson.internal.bind;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

public class JsonStructureManager {
    private final List<JsonElement> stack = new ArrayList<>();
    private JsonElement product = JsonNull.INSTANCE;

    public JsonElement peek() {
        return stack.get(stack.size() - 1);
    }

    public void push(JsonElement element) {
        stack.add(element);
    }

    public void pop() {
        if (!stack.isEmpty()) {
            stack.remove(stack.size() - 1);
        } else {
            throw new IllegalStateException("No element to pop from stack.");
        }
    }

    public void put(JsonElement value, String pendingName) {
        if (pendingName != null) {
            if (!value.isJsonNull()) {
                JsonObject object = (JsonObject) peek();
                object.add(pendingName, value);
            }
        } else if (stack.isEmpty()) {
            product = value;
        } else {
            JsonElement element = peek();
            if (element instanceof JsonArray) {
                ((JsonArray) element).add(value);
            } else {
                throw new IllegalStateException("Element is neither JsonArray nor JsonObject.");
            }
        }
    }

    public JsonElement getProduct() {
        return product;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
