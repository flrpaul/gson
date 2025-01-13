/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 *
 * package com.google.gson.internal.bind;
 *
 * import com.google.errorprone.annotations.CanIgnoreReturnValue; import
 * com.google.gson.JsonArray; import com.google.gson.JsonElement; import
 * com.google.gson.JsonNull; import com.google.gson.JsonObject; import
 * com.google.gson.JsonPrimitive; import com.google.gson.stream.JsonWriter;
 * import java.io.IOException; import java.io.Writer; import
 * java.util.ArrayList; import java.util.List; import java.util.Objects;
 *
 *//** This writer creates a JsonElement. */
/*
 * public final class JsonTreeWriter extends JsonWriter { private static final
 * Writer UNWRITABLE_WRITER = new Writer() {
 *
 * @Override public void write(char[] buffer, int offset, int counter) { throw
 * new AssertionError(); }
 *
 * @Override public void flush() { throw new AssertionError(); }
 *
 * @Override public void close() { throw new AssertionError(); } };
 *
 *//**
 * Added to the top of the stack when this writer is closed to cause following
 * ops to fail.
 */
/*
 * private static final JsonPrimitive SENTINEL_CLOSED = new
 * JsonPrimitive("closed");
 *
 *//**
 * The JsonElements and JsonArrays under modification, outermost to innermost.
 */
/*
 * private final List<JsonElement> stack = new ArrayList<>();
 *
 *//**
 * The name for the next JSON object value. If non-null, the top of the stack is
 * a JsonObject.
 */
/*
 * private String pendingName;
 *
 *//** the JSON element constructed by this writer. */
/*
 * private JsonElement product = JsonNull.INSTANCE; // TODO: is this really what
 * we want?;
 *
 * public JsonTreeWriter() { super(UNWRITABLE_WRITER); }
 *
 *//** Returns the top level object produced by this writer. *//*
 * public JsonElement get() { if (!stack.isEmpty()) {
 * throw new
 * IllegalStateException("Expected one JSON element but was "
 * + stack); } return product; }
 *
 * private JsonElement peek() { return
 * stack.get(stack.size() - 1); }
 *
 * private void put(JsonElement value) { if (pendingName
 * != null) { if (!value.isJsonNull() ||
 * getSerializeNulls()) { JsonObject object =
 * (JsonObject) peek(); object.add(pendingName, value);
 * } pendingName = null; } else if (stack.isEmpty()) {
 * product = value; } else { JsonElement element =
 * peek(); if (element instanceof JsonArray) {
 * ((JsonArray) element).add(value); } else { throw new
 * IllegalStateException(); } } }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter beginArray() throws
 * IOException { JsonArray array = new JsonArray();
 * put(array); stack.add(array); return this; }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter endArray() throws
 * IOException { if (stack.isEmpty() || pendingName !=
 * null) { throw new IllegalStateException(); }
 * JsonElement element = peek(); if (element instanceof
 * JsonArray) { stack.remove(stack.size() - 1); return
 * this; } throw new IllegalStateException(); }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter beginObject() throws
 * IOException { JsonObject object = new JsonObject();
 * put(object); stack.add(object); return this; }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter endObject() throws
 * IOException { if (stack.isEmpty() || pendingName !=
 * null) { throw new IllegalStateException(); }
 * JsonElement element = peek(); if (element instanceof
 * JsonObject) { stack.remove(stack.size() - 1); return
 * this; } throw new IllegalStateException(); }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter name(String name) throws
 * IOException { Objects.requireNonNull(name,
 * "name == null"); if (stack.isEmpty() || pendingName
 * != null) { throw new
 * IllegalStateException("Did not expect a name"); }
 * JsonElement element = peek(); if (element instanceof
 * JsonObject) { pendingName = name; return this; }
 * throw new
 * IllegalStateException("Please begin an object before writing a name."
 * ); }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter value(String value)
 * throws IOException { if (value == null) { return
 * nullValue(); } put(new JsonPrimitive(value)); return
 * this; }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter value(boolean value)
 * throws IOException { put(new JsonPrimitive(value));
 * return this; }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter value(Boolean value)
 * throws IOException { if (value == null) { return
 * nullValue(); } put(new JsonPrimitive(value)); return
 * this; }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter value(float value) throws
 * IOException { if (!isLenient() && (Float.isNaN(value)
 * || Float.isInfinite(value))) { throw new
 * IllegalArgumentException("JSON forbids NaN and infinities: "
 * + value); } put(new JsonPrimitive(value)); return
 * this; }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter value(double value)
 * throws IOException { if (!isLenient() &&
 * (Double.isNaN(value) || Double.isInfinite(value))) {
 * throw new
 * IllegalArgumentException("JSON forbids NaN and infinities: "
 * + value); } put(new JsonPrimitive(value)); return
 * this; }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter value(long value) throws
 * IOException { put(new JsonPrimitive(value)); return
 * this; }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter value(Number value)
 * throws IOException { if (value == null) { return
 * nullValue(); }
 *
 * if (!isLenient()) { double d = value.doubleValue();
 * if (Double.isNaN(d) || Double.isInfinite(d)) { throw
 * new
 * IllegalArgumentException("JSON forbids NaN and infinities: "
 * + value); } }
 *
 * put(new JsonPrimitive(value)); return this; }
 *
 * @CanIgnoreReturnValue
 *
 * @Override public JsonWriter nullValue() throws
 * IOException { put(JsonNull.INSTANCE); return this; }
 *
 * @Override public JsonWriter jsonValue(String value)
 * throws IOException { throw new
 * UnsupportedOperationException(); }
 *
 * @Override public void flush() throws IOException {}
 *
 * @Override public void close() throws IOException { if
 * (!stack.isEmpty()) { throw new
 * IOException("Incomplete document"); }
 * stack.add(SENTINEL_CLOSED); } }
 */


// Refactor code

package com.google.gson.internal.bind;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/** This writer creates a JsonElement. */
public final class JsonTreeWriter extends JsonWriter {
  private static final Writer UNWRITABLE_WRITER =
          new Writer() {
            @Override
            public void write(char[] buffer, int offset, int counter) {
              throw new AssertionError();
            }

            @Override
            public void flush() {
              throw new AssertionError();
            }

            @Override
            public void close() {
              throw new AssertionError();
            }
          };

  /** Added to the top of the stack when this writer is closed to cause following ops to fail. */
  private static final JsonPrimitive SENTINEL_CLOSED = new JsonPrimitive("closed");

  private final JsonStructureManager structureManager = new JsonStructureManager();
  private final JsonValueWriter valueWriter = new JsonValueWriter(structureManager);
  private String pendingName;

  public JsonTreeWriter() {
    super(UNWRITABLE_WRITER);
  }

  /** Returns the top level object produced by this writer. */
  public JsonElement get() {
    if (!structureManager.isEmpty()) {
      throw new IllegalStateException("Expected one JSON element but was " + structureManager.peek());
    }
    return structureManager.getProduct();
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter beginArray() throws IOException {
    JsonArray array = new JsonArray();
    structureManager.put(array, pendingName);
    structureManager.push(array);
    pendingName = null;
    return this;
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter endArray() throws IOException {
    if (pendingName != null) {
      throw new IllegalStateException("Expected no pending name but found one.");
    }
    JsonElement element = structureManager.peek();
    if (element instanceof JsonArray) {
      structureManager.pop();
      return this;
    }
    throw new IllegalStateException("Expected a JsonArray but was " + element);
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter beginObject() throws IOException {
    JsonObject object = new JsonObject();
    structureManager.put(object, pendingName);
    structureManager.push(object);
    pendingName = null;
    return this;
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter endObject() throws IOException {
    if (pendingName != null) {
      throw new IllegalStateException("Expected no pending name but found one.");
    }
    JsonElement element = structureManager.peek();
    if (element instanceof JsonObject) {
      structureManager.pop();
      return this;
    }
    throw new IllegalStateException("Expected a JsonObject but was " + element);
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter name(String name) throws IOException {
    Objects.requireNonNull(name, "name == null");
    if (pendingName != null) {
      throw new IllegalStateException("Pending name already set.");
    }
    pendingName = name;
    return this;
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter value(String value) throws IOException {
    if (value == null) {
      return nullValue();
    }
    valueWriter.writeValue(value);
    return this;
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter value(boolean value) throws IOException {
    valueWriter.writeValue(value);
    return this;
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter value(Boolean value) throws IOException {
    valueWriter.writeValue(value);
    return this;
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter value(float value) throws IOException {
    valueWriter.writeValue(value);
    return this;
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter value(double value) throws IOException {
    valueWriter.writeValue(value);
    return this;
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter value(long value) throws IOException {
    valueWriter.writeValue(value);
    return this;
  }

  @CanIgnoreReturnValue
  @Override
  public JsonWriter value(Number value) throws IOException {
    valueWriter.writeValue(value);
    return this;
  }

  @Override
  public JsonWriter nullValue() throws IOException {
    structureManager.put(JsonNull.INSTANCE, pendingName);
    return this;
  }

  @Override
  public void flush() throws IOException {}

  @Override
  public void close() throws IOException {
    if (!structureManager.isEmpty()) {
      throw new IOException("Incomplete document");
    }
    structureManager.push(SENTINEL_CLOSED);
  }
}
