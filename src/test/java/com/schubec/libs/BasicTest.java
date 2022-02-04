package com.schubec.libs;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.base.FMCommandBase;
import com.schubec.libs.filemaker.base.FMCommandWithData;
import com.schubec.libs.filemaker.exceptions.FileMakerException;
import com.schubec.libs.filemaker.implementation.FMAddCommand;
import com.schubec.libs.filemaker.implementation.FMDeleteCommand;
import com.schubec.libs.filemaker.implementation.FMEditCommand;
import com.schubec.libs.filemaker.implementation.FMFindCommand;
import com.schubec.libs.filemaker.implementation.FMFindCriterion;
import com.schubec.libs.filemaker.implementation.FMGetRecordByIdCommand;
import com.schubec.libs.filemaker.results.FMRecord;
import com.schubec.libs.filemaker.results.FMRecordsResponse;
import com.schubec.libs.filemaker.results.FMResult;

public class BasicTest {

	private static final String LAYOUT_LOG = "Log";

	@Test
	public void simpleTest() throws Exception {
		try (FMSession fmSession = FMSession.login(TestConfig.HOST,
				TestConfig.DATABASE,
				TestConfig.USERNAME,
				TestConfig.PASSWORD)) {

			FMCommandWithData fmAdd = new FMAddCommand(LAYOUT_LOG).setField("Details", "3").setField("Kategorie", "Test");
			// fmAdd.setScriptPreSort(new FMScript("WEB Test Script"));
			FMResult<FMRecordsResponse> result = fmSession.execute(fmAdd);

			String recordId = result.getResponse().getRecordId();
			System.out.println("record created, recordid: " + recordId);

			FMCommandBase fmEdit = new FMEditCommand(LAYOUT_LOG, Long.parseLong(recordId)).setField("Details", "3").setField("Kategorie",
					"Test Edit");
			fmSession.execute(fmEdit);

			FMCommandBase fmDelete = new FMDeleteCommand(LAYOUT_LOG, Long.parseLong(recordId));
			fmSession.execute(fmDelete);
			FMCommandBase fmGetbyId = new FMGetRecordByIdCommand(LAYOUT_LOG, 46l);
			FMResult<FMRecordsResponse> result4 = fmSession.execute(fmGetbyId);
			FMRecordsResponse response = result4.getResponse();
			long recordId2 = response.getFirstRecord().get().getRecordId();
			System.out.println("record recevied recordid: " + recordId2);

			System.out.println("XXXX=>" + response.getFirstRecord().get().getField("TEST"));
			System.out.println("XXXX=>" + response.getFirstRecord().get().getFieldAsDate("Date"));
			System.out.println("XXXX=>" + response.getFirstRecord().get().getFieldAsTimestamp("Änderungszeitstempel"));

			List<FMRecord> records = response.getRecords();
			System.out.println("get status: " + records.get(0).getFieldData().get("Status"));

			FMFindCommand fmFind = new FMFindCommand("FAQs");
			FMFindCriterion c = new FMFindCriterion("Sprache", "DE");
			fmFind.addFindCriterion(c).addCriterion("Antwort", "schreiben");
			fmFind.addFindCriterion("pkFAQ", 22);
			fmFind.setLimit(13l);

			FMResult<FMRecordsResponse> result5 = fmSession.execute(fmFind);
			if (result5.getResponse().getFirstRecord().isPresent()) {
				long recordId3 = result5.getResponse().getFirstRecord().get().getRecordId();
				System.out.println("record recevied recordid: " + recordId3);

				for (FMRecord records2 : result5.getResponse().getRecords()) {
					System.out.println("Get frage: " + records2.getFieldData().get("Frage"));
				}
			}

		} catch (FileMakerException e) {
			fail("Should not have thrown any exception", e);
		}
	}

}