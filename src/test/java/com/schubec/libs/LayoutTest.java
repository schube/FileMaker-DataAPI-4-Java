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
import com.schubec.libs.filemaker.implementation.FMLayoutCommand;
import com.schubec.libs.filemaker.results.FMLayoutResponse;
import com.schubec.libs.filemaker.results.FMRecord;
import com.schubec.libs.filemaker.results.FMResult;
import com.schubec.libs.filemaker.results.FMValuelist;

public class LayoutTest {

	private static final String LAYOUT_LOG = "Log";

	@Test
	public void simpleTest() throws Exception {
		try (FMSession fmSession = FMSession.login(TestConfig.HOST,
				TestConfig.DATABASE,
				TestConfig.USERNAME,
				TestConfig.PASSWORD)) {

			FMLayoutCommand fmAdd = new FMLayoutCommand(LAYOUT_LOG);
			// fmAdd.setScriptPreSort(new FMScript("WEB Test Script"));
			FMResult<FMLayoutResponse> result = fmSession.execute(fmAdd);
			List<FMValuelist> valueLists = result.getResponse().getValueLists();
			System.out.println(valueLists.get(0).getName());

		} catch (FileMakerException e) {
			fail("Should not have thrown any exception", e);
		}
	}

}