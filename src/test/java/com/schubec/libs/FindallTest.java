package com.schubec.libs;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.implementation.FMFindallCommand;
import com.schubec.libs.filemaker.results.FMPortal;
import com.schubec.libs.filemaker.results.FMPortalRecord;
import com.schubec.libs.filemaker.results.FMRecord;
import com.schubec.libs.filemaker.results.FMRecordsResponse;
import com.schubec.libs.filemaker.results.FMResult;

public class FindallTest {

	private static final String LAYOUT_LOG = "Log";

	@Test
	public void findallTest() {
		try (FMSession fmSession = FMSession.login(TestConfig.HOST,
				TestConfig.DATABASE,
				TestConfig.USERNAME,
				TestConfig.PASSWORD)) {

			FMFindallCommand fmFindall = new FMFindallCommand(LAYOUT_LOG);
			fmFindall.addSortRule("Kategorie", "descend");
			fmFindall.setLimit(10l);
			fmSession.setDebug(true);
			FMResult<FMRecordsResponse> result = fmSession.execute(fmFindall);
			if(result.getResponse().getFirstRecord().isPresent()) {
				long recordId = result.getResponse().getFirstRecord().get().getRecordId();
				System.out.println("record recevied recordid: " + recordId);
				System.out.println("URI: " + result.getRequestUri().toString());
			}
			for (FMRecord record : result.getResponse().getRecords()) {
				System.out.println("Get frage: " + record.getFieldData().get("Kategorie") + ": " + record.getFieldData().get("Status"));
				System.out.println("   portals: " + record.getAvailablePortals().get());
				Optional<FMPortal> portalJahre = record.getPortal("Log.TestJahre");
				if (portalJahre.isPresent()) {
					System.out.println("Found portal records: " + portalJahre.get().getPortalInfo().getFoundCount());
					System.out.println("portal size: " + portalJahre.get().getPortalRecords().size());
					for (FMPortalRecord pr : portalJahre.get().getPortalRecords()) {
						System.out.println("   fiels: " + pr.getAvailableFields());
						System.out.println("   portal records: " + pr.getField("Log.TestJahre::Jahr"));
					}
				}
				Optional<FMPortal> portalLatexJobs = record.getPortal("Log.TestLatexjobs");
				if (portalLatexJobs.isPresent()) {
					System.out.println("Found portal records: " + portalLatexJobs.get().getPortalInfo().getFoundCount());
					System.out.println("portal size: " + portalLatexJobs.get().getPortalRecords().size());
					for (FMPortalRecord pr : portalLatexJobs.get().getPortalRecords()) {
						System.out.println("   fiels: " + pr.getAvailableFields());
						System.out.println("   portal records: " + pr.getField("Log.TestLatexjobs::Command"));
						System.out.println("   portal records: " + pr.getField("Log.TestLatexjobs::Latexfile"));
					}
				}

			}

			if (fmSession.logout()) {
				System.out.println("Erfolgreich ausgeloggt");
			}
		} catch (Exception e) {
			fail("Should not have thrown any exception", e);
		}
	}

}