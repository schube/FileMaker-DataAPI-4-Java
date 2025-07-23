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
import com.schubec.libs.filemaker.implementation.FMScript;
import com.schubec.libs.filemaker.results.FMRecord;
import com.schubec.libs.filemaker.results.FMRecordsResponse;
import com.schubec.libs.filemaker.results.FMResult;

public class ExecuteScriptTest {

	private static final String LAYOUT_LOG = "Log";

	@Test
	public void simpleTest() throws Exception {
		try (FMSession fmSession = FMSession.login(TestConfig.HOST,
				TestConfig.DATABASE,
				TestConfig.USERNAME,
				TestConfig.PASSWORD)) {

			FMCommandWithData fmAdd = new FMAddCommand(LAYOUT_LOG).setField("Details", "3").setField("Kategorie", "Test");
			fmAdd.setScript(new FMScript("FM-API-JAVA-Unittest"));
			FMResult<FMRecordsResponse> result = fmSession.execute(fmAdd);

			String recordId = result.getResponse().getRecordId();
			System.out.println("record created, recordid: " + recordId);

			
		} catch (FileMakerException e) {
			fail("Should not have thrown any exception", e);
		}
	}

}