package com.schubec.libs.filemaker.results;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents a FileMaker portal record, providing map-like access to field data.
 * Extends FMBaseRecord and implements Map for convenience.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FMPortalRecord extends FMBaseRecord implements Map<String, String> {

    /**
     * Default constructor. Initializes the field data map.
     */
    public FMPortalRecord() {
        fieldData = new HashMap<>();
    }

    /**
     * Returns the number of fields in this portal record.
     * @return The number of fields.
     */
    @Override
    public int size() {
        return fieldData.size();
    }

    /**
     * Returns true if this portal record contains no fields.
     * @return true if empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return fieldData.isEmpty();
    }

    /**
     * Returns true if this portal record contains the given field name.
     * @param key The field name.
     * @return true if the field exists, false otherwise.
     */
    @Override
    public boolean containsKey(Object key) {
        return fieldData.containsKey(key);
    }

    /**
     * Returns true if this portal record contains the given value.
     * @param value The value to check.
     * @return true if the value exists, false otherwise.
     */
    @Override
    public boolean containsValue(Object value) {
        return fieldData.containsKey(value);
    }

    /**
     * Returns the value for the given field name.
     * @param key The field name.
     * @return The field value, or null if not present.
     */
    @Override
    public String get(Object key) {
        return fieldData.get(key);
    }

    /**
     * Associates the given value with the given field name.
     * @param key The field name.
     * @param value The value to associate.
     * @return The previous value, or null if none.
     */
    @Override
    public String put(String key, String value) {
        return fieldData.put(key, value);
    }

    /**
     * Removes the field with the given name.
     * @param key The field name.
     * @return The previous value, or null if none.
     */
    @Override
    public String remove(Object key) {
        return fieldData.remove(key);
    }

    /**
     * Copies all mappings from the given map to this portal record.
     * @param m The map of field names and values.
     */
    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        fieldData.putAll(m);

    }

    /**
     * Removes all fields from this portal record.
     */
    @Override
    public void clear() {
        fieldData.clear();

    }

    /**
     * Returns the set of field names in this portal record.
     * @return The set of field names.
     */
    @Override
    public Set<String> keySet() {
        return fieldData.keySet();
    }

    /**
     * Returns the collection of field values in this portal record.
     * @return The collection of field values.
     */
    @Override
    public Collection<String> values() {
        return fieldData.values();
    }

    /**
     * Returns the set of field entries in this portal record.
     * @return The set of entries.
     */
    @Override
    public Set<Entry<String, String>> entrySet() {
        return fieldData.entrySet();
    }

}
