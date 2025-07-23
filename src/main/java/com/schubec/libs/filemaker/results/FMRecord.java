package com.schubec.libs.filemaker.results;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a FileMaker record, including portal data and metadata.
 * Extends FMBaseRecord for field access and type conversion.
 */
public class FMRecord extends FMBaseRecord {

	@JsonProperty("recordId")
	private long recordId;

	@JsonProperty("modId")
	private long modId;

	@JsonProperty("portalData")
	private Map<String, List<FMPortalRecord>> portalData;

	@JsonProperty("portalDataInfo")
	private List<FMPortalDataInfo> portalDataInfo;

	/**
	 * Returns the record ID for this record.
	 * @return The record ID.
	 */
	public long getRecordId() {
		return recordId;
	}

	/**
	 * Sets the record ID for this record.
	 * @param recordId The record ID.
	 */
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	/**
	 * Returns the modification ID for this record.
	 * @return The modification ID.
	 */
	public long getModId() {
		return modId;
	}

	/**
	 * Sets the modification ID for this record.
	 * @param modId The modification ID.
	 */
	public void setModId(long modId) {
		this.modId = modId;
	}

	private FMPortalDataInfo getPortalDataInfo(String portalName) {
		for (FMPortalDataInfo portalInfo : portalDataInfo) {
			if (portalInfo.getTable().equals(portalName)) {
				return portalInfo;
			}
		}

		return null;
	}

	/**
	 * Returns the set of available portal names for this record.
	 * @return An Optional containing the set of portal names, or empty if none.
	 */
	public Optional<Set<String>> getAvailablePortals() {
		if (portalData == null || portalData.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(portalData.keySet());
	}

	/**
	 * Returns the portal object for the given portal name.
	 * @param portalName The portal name.
	 * @return An Optional containing the FMPortal, or empty if not found.
	 */
	public Optional<FMPortal> getPortal(String portalName) {
		if (!portalData.containsKey(portalName)) {
			return Optional.empty();
		}
		FMPortal portal = new FMPortal();
		portal.setPortalRecords(portalData.get(portalName));
		portal.setPortalInfo(getPortalDataInfo(portalName));
		return Optional.of(portal);
	}

}
