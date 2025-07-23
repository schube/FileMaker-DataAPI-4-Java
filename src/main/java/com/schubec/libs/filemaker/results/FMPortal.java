package com.schubec.libs.filemaker.results;

import java.util.List;
import java.util.Set;

/**
 * Represents a FileMaker portal, containing related records and portal info.
 */
public class FMPortal {
	private List<FMPortalRecord> portalRecords;
	private FMPortalDataInfo portalInfo;

	/**
	 * Returns the list of portal records.
	 * @return The list of FMPortalRecord objects.
	 */
	public List<FMPortalRecord> getPortalRecords() {
		return portalRecords;
	}

		
	/**
	 * Sets the list of portal records.
	 * @param portalRecords The list of FMPortalRecord objects.
	 */
	public void setPortalRecords(List<FMPortalRecord> portalRecords) {
		this.portalRecords = portalRecords;
	}

	/**
	 * Returns the portal info object.
	 * @return The FMPortalDataInfo object.
	 */
	public FMPortalDataInfo getPortalInfo() {
		return portalInfo;
	}

	/**
	 * Sets the portal info object.
	 * @param portalInfo The FMPortalDataInfo object.
	 */
	public void setPortalInfo(FMPortalDataInfo portalInfo) {
		this.portalInfo = portalInfo;
	}
}
