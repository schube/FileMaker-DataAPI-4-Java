package com.schubec.libs.filemaker.results;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FMPortalRecord extends FMBaseRecord implements Map<String, String> {


	public FMPortalRecord() {
		fieldData = new HashMap<>();
	}
	
	@Override
	public int size() {
		return fieldData.size();
	}

	@Override
	public boolean isEmpty() {
		return fieldData.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return fieldData.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return fieldData.containsKey(value);
	}

	@Override
	public String get(Object key) {
		return fieldData.get(key);
	}

	@Override
	public String put(String key, String value) {
		return fieldData.put(key, value);
	}

	@Override
	public String remove(Object key) {
		return fieldData.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		fieldData.putAll(m);

	}

	@Override
	public void clear() {
		fieldData.clear();

	}

	@Override
	public Set<String> keySet() {
		return fieldData.keySet();
	}

	@Override
	public Collection<String> values() {
		return fieldData.values();
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		return fieldData.entrySet();
	}

}
