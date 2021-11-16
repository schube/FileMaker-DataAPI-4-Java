package com.schubec.libs.filemaker.results;

import java.util.List;
import java.util.Set;

public class FMPortal {
	private List<FMPortalRecord> portalRecords;
	private FMPortalDataInfo portalInfo;

	public List<FMPortalRecord> getPortalRecords() {
		return portalRecords;
	}

		
	public void setPortalRecords(List<FMPortalRecord> portalRecords) {
		this.portalRecords = portalRecords;
	}

	public FMPortalDataInfo getPortalInfo() {
		return portalInfo;
	}

	public void setPortalInfo(FMPortalDataInfo portalInfo) {
		this.portalInfo = portalInfo;
	}
}
