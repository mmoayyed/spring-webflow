/*
 * Copyright 2004-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.binding.collection;

import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map;

/**
 * A simple, generic decorator for getting attributes out of a map. May be instantiated directly or used as a base class
 * as a convenience.
 *
 * @author Keith Donald
 */
public class MapAccessor<K, V> implements MapAdaptable<K, V> {

    /**
     * The target map.
     */
    private Map<K, V> map;

    /**
     * Creates a new attribute map accessor.
     *
     * @param map the map
     */
    public MapAccessor(Map<K, V> map) {
        Assert.notNull(map, "The map to decorate is required");
        this.map = map;
    }

    // implementing MapAdaptable

    public Map<K, V> asMap() {
        return map;
    }

    /**
     * Returns a value in the map, returning null if the attribute is not present.
     *
     * @param key the key
     * @return the value
     */
    public V get(Object key) {
        return map.get(key);
    }

    /**
     * Returns a value in the map, returning the defaultValue if no value was found.
     *
     * @param key          the key
     * @param defaultValue the default
     * @return the attribute value
     */
    public V get(Object key, V defaultValue) {
        if (!map.containsKey(key)) {
            return defaultValue;
        }
        return map.get(key);
    }

    /**
     * Returns a value in the map, asserting it is of the required type if present and returning <code>null</code> if
     * not found.
     *
     * @param key          the key
     * @param requiredType the required type
     * @param <T>
     * @param <T>
     * @return the value
     * @throws IllegalArgumentException if the key is present but the value is not of the required type
     */
    public <T extends V> T get(Object key, Class<T> requiredType) throws IllegalArgumentException {
        return get(key, requiredType, null);
    }

    /**
     * Returns a value in the map of the specified type, returning the defaultValue if no value is found.
     *
     * @param key          the key
     * @param requiredType the required type
     * @param defaultValue the default
     * @param <T>
     * @param <T>
     * @return the attribute value
     * @throws IllegalArgumentException if the key is present but the value is not of the required type
     */
    public <T extends V> T get(Object key, Class<T> requiredType, T defaultValue) {
        if (!map.containsKey(key)) {
            return defaultValue;
        }
        return assertKeyValueOfType(key, requiredType);
    }

    /**
     * Returns a value in the map, throwing an exception if the attribute is not present and of the correct type.
     *
     * @param key the key
     * @return the value
     * @throws IllegalArgumentException
     * @throws IllegalArgumentException
     */
    public V getRequired(Object key) throws IllegalArgumentException {
        assertContainsKey(key);
        return map.get(key);
    }

    /**
     * Returns an value in the map, asserting it is present and of the required type.
     *
     * @param key          the key
     * @param requiredType the required type
     * @param <T>
     * @param <T>
     * @return the value
     * @throws IllegalArgumentException
     * @throws IllegalArgumentException
     */
    public <T extends V> T getRequired(Object key, Class<T> requiredType) throws IllegalArgumentException {
        assertContainsKey(key);
        return assertKeyValueOfType(key, requiredType);
    }

    /**
     * Returns a string value in the map, returning <code>null</code> if no value was found.
     *
     * @param key the key
     * @return the string value
     * @throws IllegalArgumentException if the key is present but the value is not a string
     */
    public String getString(Object key) throws IllegalArgumentException {
        return getString(key, null);
    }

    /**
     * Returns a string value in the map, returning the defaultValue if no value was found.
     *
     * @param key          the key
     * @param defaultValue the default
     * @return the string value
     * @throws IllegalArgumentException if the key is present but the value is not a string
     */
    public String getString(Object key, String defaultValue) throws IllegalArgumentException {
        if (!map.containsKey(key)) {
            return defaultValue;
        }
        return assertKeyValueOfType(key, String.class);
    }

    /**
     * Returns a string value in the map, throwing an exception if the attribute is not present and of the correct type.
     *
     * @param key the key
     * @return the string value
     * @throws IllegalArgumentException if the key is not present or present but the value is not a string
     */
    public String getRequiredString(Object key) throws IllegalArgumentException {
        assertContainsKey(key);
        return assertKeyValueOfType(key, String.class);
    }

    /**
     * Returns a collection value in the map, returning <code>null</code> if no value was found.
     *
     * @param key the key
     * @return the collection value
     * @throws IllegalArgumentException if the key is present but the value is not a collection
     */
    @SuppressWarnings("unchecked")
    public Collection<V> getCollection(Object key) throws IllegalArgumentException {
        if (!map.containsKey(key)) {
            return null;
        }
        return assertKeyValueOfType(key, Collection.class);
    }

    /**
     * Returns a collection value in the map, asserting it is of the required type if present and returning
     * <code>null</code> if not found.
     *
     * @param key          the key
     * @param requiredType
     * @param requiredType
     * @param <T>
     * @param <T>
     * @return the collection value
     * @throws IllegalArgumentException if the key is present but the value is not a collection
     */
    public <T extends Collection<V>> T getCollection(Object key, Class<T> requiredType) throws IllegalArgumentException {
        if (!map.containsKey(key)) {
            return null;
        }
        assertAssignableTo(Collection.class, requiredType);
        return assertKeyValueOfType(key, requiredType);
    }

    /**
     * Returns a collection value in the map, throwing an exception if not found.
     *
     * @param key the key
     * @return the collection value
     * @throws IllegalArgumentException if the key is not present or present but the value is not a collection
     */
    @SuppressWarnings("unchecked")
    public Collection<V> getRequiredCollection(Object key) throws IllegalArgumentException {
        assertContainsKey(key);
        return assertKeyValueOfType(key, Collection.class);
    }

    /**
     * Returns a collection value in the map, asserting it is of the required type if present and throwing an exception
     * if not found.
     *
     * @param key          the key
     * @param requiredType
     * @param requiredType
     * @param <T>
     * @param <T>
     * @return the collection value
     * @throws IllegalArgumentException if the key is not present or present but the value is not a collection of the
     *                                  required type
     */
    public <T extends Collection<V>> T getRequiredCollection(Object key, Class<T> requiredType)
        throws IllegalArgumentException {
        assertContainsKey(key);
        assertAssignableTo(Collection.class, requiredType);
        return assertKeyValueOfType(key, requiredType);
    }

    /**
     * Returns a array value in the map, asserting it is of the required type if present and returning <code>null</code>
     * if not found.
     *
     * @param key          the key
     * @param requiredType
     * @param requiredType
     * @param <T>
     * @param <T>
     * @return the array value
     * @throws IllegalArgumentException if the key is present but the value is not an array of the required type
     */
    public <T extends V> T[] getArray(Object key, Class<? extends T[]> requiredType) throws IllegalArgumentException {
        assertAssignableTo(Object[].class, requiredType);
        if (!map.containsKey(key)) {
            return null;
        }
        return assertKeyValueOfType(key, requiredType);
    }

    /**
     * Returns an array value in the map, asserting it is of the required type if present and throwing an exception if
     * not found.
     *
     * @param key          the key
     * @param requiredType
     * @param requiredType
     * @param <T>
     * @param <T>
     * @return the array value
     * @throws IllegalArgumentException if the key is not present or present but the value is not a array of the
     *                                  required type
     */
    public <T extends V> T[] getRequiredArray(Object key, Class<? extends T[]> requiredType)
        throws IllegalArgumentException {
        assertContainsKey(key);
        assertAssignableTo(Object[].class, requiredType);
        return assertKeyValueOfType(key, requiredType);
    }

    /**
     * Returns a number value in the map that is of the specified type, returning <code>null</code> if no value was
     * found.
     *
     * @param key          the key
     * @param requiredType the required number type
     * @param <T>
     * @param <T>
     * @return the number value
     * @throws IllegalArgumentException if the key is present but the value is not a number of the required type
     */
    public <T extends Number> T getNumber(Object key, Class<T> requiredType) throws IllegalArgumentException {
        return getNumber(key, requiredType, null);
    }

    /**
     * Returns a number attribute value in the map of the specified type, returning the defaultValue if no value was
     * found.
     *
     * @param key          the attribute name
     * @param requiredType
     * @param requiredType
     * @param defaultValue the default
     * @param <T>
     * @param <T>
     * @return the number value
     * @throws IllegalArgumentException if the key is present but the value is not a number of the required type
     */
    public <T extends Number> T getNumber(Object key, Class<T> requiredType, T defaultValue)
        throws IllegalArgumentException {
        if (!map.containsKey(key)) {
            return defaultValue;
        }
        assertAssignableTo(Number.class, requiredType);
        return assertKeyValueOfType(key, requiredType);
    }

    /**
     * Returns a number value in the map, throwing an exception if the attribute is not present and of the correct type.
     *
     * @param key          the key
     * @param requiredType
     * @param requiredType
     * @param <T>
     * @param <T>
     * @return the number value
     * @throws IllegalArgumentException if the key is not present or present but the value is not a number of the
     *                                  required type
     */
    public <T extends Number> T getRequiredNumber(Object key, Class<T> requiredType) throws IllegalArgumentException {
        assertContainsKey(key);
        return assertKeyValueOfType(key, requiredType);
    }

    /**
     * Returns an integer value in the map, returning <code>null</code> if no value was found.
     *
     * @param key the key
     * @return the integer value
     * @throws IllegalArgumentException if the key is present but the value is not an integer
     */
    public Integer getInteger(Object key) throws IllegalArgumentException {
        return getInteger(key, null);
    }

    /**
     * Returns an integer value in the map, returning the defaultValue if no value was found.
     *
     * @param key          the key
     * @param defaultValue the default
     * @return the integer value
     * @throws IllegalArgumentException if the key is present but the value is not an integer
     */
    public Integer getInteger(Object key, Integer defaultValue) throws IllegalArgumentException {
        return getNumber(key, Integer.class, defaultValue);
    }

    /**
     * Returns an integer value in the map, throwing an exception if the value is not present and of the correct type.
     *
     * @param key the attribute name
     * @return the integer attribute value
     * @throws IllegalArgumentException if the key is not present or present but the value is not an integer
     */
    public Integer getRequiredInteger(Object key) throws IllegalArgumentException {
        return getRequiredNumber(key, Integer.class);
    }

    /**
     * Returns a long value in the map, returning <code>null</code> if no value was found.
     *
     * @param key the key
     * @return the long value
     * @throws IllegalArgumentException if the key is present but not a long
     */
    public Long getLong(Object key) throws IllegalArgumentException {
        return getLong(key, null);
    }

    /**
     * Returns a long value in the map, returning the defaultValue if no value was found.
     *
     * @param key          the key
     * @param defaultValue the default
     * @return the long attribute value
     * @throws IllegalArgumentException if the key is present but the value is not a long
     */
    public Long getLong(Object key, Long defaultValue) throws IllegalArgumentException {
        return getNumber(key, Long.class, defaultValue);
    }

    /**
     * Returns a long value in the map, throwing an exception if the value is not present and of the correct type.
     *
     * @param key the key
     * @return the long attribute value
     * @throws IllegalArgumentException if the key is not present or present but the value is not a long
     */
    public Long getRequiredLong(Object key) throws IllegalArgumentException {
        return getRequiredNumber(key, Long.class);
    }

    /**
     * Returns a boolean value in the map, returning <code>null</code> if no value was found.
     *
     * @param key the key
     * @return the boolean value
     * @throws IllegalArgumentException if the key is present but the value is not a boolean
     */
    public Boolean getBoolean(Object key) throws IllegalArgumentException {
        return getBoolean(key, null);
    }

    /**
     * Returns a boolean value in the map, returning the defaultValue if no value was found.
     *
     * @param key          the key
     * @param defaultValue the default
     * @return the boolean value
     * @throws IllegalArgumentException if the key is present but the value is not a boolean
     */
    public Boolean getBoolean(Object key, Boolean defaultValue) throws IllegalArgumentException {
        if (!map.containsKey(key)) {
            return defaultValue;
        }
        return assertKeyValueOfType(key, Boolean.class);
    }

    /**
     * Returns a boolean value in the map, throwing an exception if the value is not present and of the correct type.
     *
     * @param key the attribute
     * @return the boolean value
     * @throws IllegalArgumentException if the key is not present or present but the value is not a boolean
     */
    public Boolean getRequiredBoolean(Object key) throws IllegalArgumentException {
        assertContainsKey(key);
        return assertKeyValueOfType(key, Boolean.class);
    }

    /**
     * Asserts that the attribute is present in the attribute map.
     *
     * @param key the key
     * @throws IllegalArgumentException if the key is not present
     */
    public void assertContainsKey(Object key) throws IllegalArgumentException {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Required attribute '" + key
                                               + "' is not present in map; attributes present are [" + asMap() + "]");
        }
    }

    /**
     * Indicates if the attribute is present in the attribute map and of the required type.
     *
     * @param key          the attribute name
     * @param requiredType
     * @param requiredType
     * @return true if present and of the required type, false if not present.
     * @throws IllegalArgumentException
     * @throws IllegalArgumentException
     */
    public boolean containsKey(Object key, Class<?> requiredType) throws IllegalArgumentException {
        if (map.containsKey(key)) {
            assertKeyValueOfType(key, requiredType);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Assert that value of the map key, if non-null, is of the required type.
     *
     * @param key          the attribute name
     * @param requiredType the required attribute value type
     * @param <T>
     * @param <T>
     * @return the attribute value
     */
    public <T> T assertKeyValueOfType(Object key, Class<T> requiredType) {
        return assertKeyValueInstanceOf(key, map.get(key), requiredType);
    }

    /**
     * Assert that the key value, if non null, is an instance of the required type.
     *
     * @param key          the key
     * @param value        the value
     * @param requiredType the required type
     * @param <T>
     * @param <T>
     * @return the value
     */
    @SuppressWarnings("unchecked")
    public <T> T assertKeyValueInstanceOf(Object key, Object value, Class<T> requiredType) {
        Assert.notNull(requiredType, "The required type to assert is required");
        if (value != null && !requiredType.isInstance(value)) {
            throw new IllegalArgumentException("Map key '" + key + "' has value [" + value
                                               + "] that is not of expected type [" + requiredType + "], instead it is of type ["
                                               + value.getClass().getName() + "]");
        }
        return (T) value;
    }

    private void assertAssignableTo(Class<?> clazz, Class<?> requiredType) {
        Assert.isTrue(clazz.isAssignableFrom(requiredType), "The provided required type must be assignable to ["
                                                            + clazz + "]");
    }
}
