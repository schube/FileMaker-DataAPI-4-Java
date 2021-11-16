package com.schubec.libs;

import static org.junit.jupiter.api.Assertions.fail;

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
import com.schubec.libs.filemaker.implementation.FMScript;
import com.schubec.libs.filemaker.results.FMRecord;
import com.schubec.libs.filemaker.results.FMResult;

public class BasicTest {

	private static final String LAYOUT_LOG = "Log";

	@Test
	public void simpleTest() {
		 try(FMSession fmSession = FMSession.login(TestConfig.HOST, 
					 TestConfig.DATABASE,
					 TestConfig.USERNAME,
					 TestConfig.PASSWORD)) {

			FMCommandWithData fmAdd = new FMAddCommand(LAYOUT_LOG).setField("Details", "3").setField("Kategorie", "Test");
			//fmAdd.setScriptPreSort(new FMScript("WEB Test Script"));
			FMResult result = fmSession.execute(fmAdd);
			
			String recordId = result.getResponse().getRecordId();
			System.out.println("record created, recordid: " + recordId);

			FMCommandBase fmEdit = new FMEditCommand(LAYOUT_LOG, Long.parseLong(recordId)).setField("Details", "3").setField("Kategorie",
					"Test Edit");
			FMResult result2 = fmSession.execute(fmEdit);
			if (!result2.isSuccess()) {
				System.out.println("reccord NOT created, because: " + result2.getMessagesAsString());
			}
			FMCommandBase fmDelete = new FMDeleteCommand(LAYOUT_LOG, Long.parseLong(recordId));

			FMResult result3 = fmSession.execute(fmDelete);

			FMCommandBase fmGetbyId = new FMGetRecordByIdCommand(LAYOUT_LOG, 46l);
			FMResult result4 = fmSession.execute(fmGetbyId);
			long recordId2 = result4.getFirstRecord().getRecordId();
			System.out.println("record recevied recordid: " + recordId2);

			System.out.println("XXXX=>"+result4.getFirstRecord().getField("TEST"));
			System.out.println("XXXX=>"+result4.getFirstRecord().getFieldAsDate("Date"));
			System.out.println("XXXX=>"+result4.getFirstRecord().getFieldAsTimestamp("Änderungszeitstempel"));
			
			FMRecord[] records = result4.getRecords();
			System.out.println("get status: " + records[0].getFieldData().get("Status"));

			FMFindCommand fmFind = new FMFindCommand("FAQs");
			FMFindCriterion c = new FMFindCriterion("Sprache", "DE");
			fmFind.addFindCriterion(c).addCriterion("Antwort", "schreiben");
			fmFind.addFindCriterion("pkFAQ", 22);
			fmFind.setLimit(13l);
			FMResult result5 = fmSession.execute(fmFind);
			long recordId3 = result5.getFirstRecord().getRecordId();
			System.out.println("record recevied recordid: " + recordId3);

			for (FMRecord records2 : result5.getRecords()) {
				System.out.println("Get frage: " + records2.getFieldData().get("Frage"));
			}

			if (fmSession.logout()) {
				System.out.println("Erfolgreich ausgeloggt");
			}
		 }
		   catch(Exception e){
		      fail("Should not have thrown any exception", e);
		   }
	}

}