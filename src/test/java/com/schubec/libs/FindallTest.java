package com.schubec.libs;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.implementation.FMFindallCommand;
import com.schubec.libs.filemaker.results.FMRecord;
import com.schubec.libs.filemaker.results.FMResult;

public class FindallTest {

	private static final String LAYOUT_LOG = "Log";

	@Test
	public void findallTest() {
		try {
			FMSession fmSession = FMSession.login(TestConfig.HOST,
					TestConfig.DATABASE,
					TestConfig.USERNAME,
					TestConfig.PASSWORD);
			
			FMFindallCommand fmFindall = new FMFindallCommand(LAYOUT_LOG);
			fmFindall.addSortRule("Kategorie", "descend");
			fmFindall.setLimit(10l);
			fmSession.setDebug(true);
			FMResult result = fmSession.execute(fmFindall);
			long recordId = result.getFirstRecord().getRecordId();
			System.out.println("record recevied recordid: " + recordId);
			System.out.println("URI: "+ result.getRequestUri().toString());
			
			for (FMRecord records : result.getRecords()) {
				System.out.println("Get frage: " + records.getFieldData().get("Kategorie") + ": " + records.getFieldData().get("Status"));
			}

			if (fmSession.logout()) {
				System.out.println("Erfolgreich ausgeloggt");
			}
		} catch (Exception e) {
			fail("Should not have thrown any exception", e);
		}
	}

}