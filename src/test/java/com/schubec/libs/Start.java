package com.schubec.libs;

import org.junit.jupiter.api.Test;

import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.base.FMCommandBase;
import com.schubec.libs.filemaker.base.FMCommandWithData;
import com.schubec.libs.filemaker.implementation.FMAddCommand;
import com.schubec.libs.filemaker.implementation.FMDeleteCommand;
import com.schubec.libs.filemaker.implementation.FMEditCommand;
import com.schubec.libs.filemaker.implementation.FMFindCommand;
import com.schubec.libs.filemaker.implementation.FMFindCriterion;
import com.schubec.libs.filemaker.implementation.FMGetRecordByIdCommand;
import com.schubec.libs.filemaker.results.Data;
import com.schubec.libs.filemaker.results.FMResult;

public class Start {

	@Test
	public void simpleTest() throws Exception {

		FMSession fmSession = FMSession.login("xxx", "xxx", "xxx", "xxx");

		FMCommandWithData fmAdd = new FMAddCommand("Log")
				.setField("Details", "3")
				.setField("Kategorie", "Test");

		FMResult result = fmSession.execute(fmAdd);
		if (result.isSuccess()) {
			long recordId = result.getResponse().getRecordId();
			System.out.println("record created, recordid: " + recordId);

			FMCommandBase fmEdit = new FMEditCommand("Log", recordId)
					.setField("Details", "3")
					.setField("Kategorie", "Test Edit");
			FMResult result2 = fmSession.execute(fmEdit);
			if (!result2.isSuccess()) {
				System.out.println("reccord NOT created, because: " + result2.getMessagesAsString());
			}
			FMCommandBase fmDelete = new FMDeleteCommand("Log", recordId);

			FMResult result3 = fmSession.execute(fmDelete);
		} else {
			System.out.println("reccord NOT created, because: " + result.getMessagesAsString());
		}

		FMCommandBase fmGetbyId = new FMGetRecordByIdCommand("Log", 23l);
		FMResult result4 = fmSession.execute(fmGetbyId);
		if (result4.isSuccess()) {
			long recordId = result4.getResponse().getRecordId();
			System.out.println("record recevied recordid: " + recordId);

			Data[] data = result4.getResponse().getData();
			System.out.println("record recevied recordid: " + data[0].getFieldData().get("Status"));

		}

		FMFindCommand fmFind = new FMFindCommand("FAQs");
		FMFindCriterion c = new FMFindCriterion("Sprache", "DE");
		fmFind.addFindCriterion(c).addCriterion("Antwort", "schreiben");
		fmFind.addFindCriterion("pkFAQ", 22);
		fmFind.setLimit(13l);
		FMResult result5 = fmSession.execute(fmFind);
		if (result5.isSuccess()) {
			long recordId = result5.getResponse().getRecordId();
			System.out.println("record recevied recordid: " + recordId);

			for (Data data : result5.getResponse().getData()) {
				System.out.println("record recevied recordid: " + data.getFieldData().get("Frage"));
			}
		}

		if (fmSession.logout()) {
			System.out.println("Erfolgreich ausgeloggt");
		}

	}

}