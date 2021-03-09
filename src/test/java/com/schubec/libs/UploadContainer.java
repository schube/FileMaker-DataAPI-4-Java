package com.schubec.libs;

import java.io.File;

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
import com.schubec.libs.filemaker.implementation.FMUploadContainerCommand;
import com.schubec.libs.filemaker.results.Data;
import com.schubec.libs.filemaker.results.FMResult;

public class UploadContainer {

	@Test
	public void simpleTest() throws Exception {

		FMSession fmSession = FMSession.login("fmserver.schubec.com", "xxxxx.fmp12", "xxxxx", "xxxx", "https", 443);
		FMUploadContainerCommand uc = new FMUploadContainerCommand("ImtectRechnungen", 13, "TestContainer1");
		File f = new File("/Users/schube/Downloads/Download Problem.docx");
		uc.setFile(f, "Johnny2.doc");
				

		FMResult result = fmSession.execute(uc);
		if (result.isSuccess()) {
			long recordId = result.getResponse().getRecordId();
			System.out.println("Document uploaded, recordid: " + recordId);

		
		} else {
			System.out.println("Document NOT uploaded, because: " + result.getMessagesAsString());
		}

	}

}