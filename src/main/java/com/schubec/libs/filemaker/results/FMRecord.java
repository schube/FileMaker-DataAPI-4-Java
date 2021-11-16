package com.schubec.libs.filemaker.results;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FMRecord extends FMBaseRecord {

	@JsonProperty("recordId")
	private long recordId;

	@JsonProperty("modId")
	private long modId;

	@JsonProperty("portalData")
	private Map<String, List<FMPortalRecord>> portalData;

	@JsonProperty("portalDataInfo")
	private List<FMPortalDataInfo> portalDataInfo;

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public long getModId() {
		return modId;
	}

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

	public Optional<Set<String>> getAvailablePortals() {
		if (portalData == null || portalData.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(portalData.keySet());
	}

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
