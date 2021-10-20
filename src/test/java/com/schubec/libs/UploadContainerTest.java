package com.schubec.libs;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.implementation.FMUploadContainerCommand;
import com.schubec.libs.filemaker.results.FMResult;

public class UploadContainerTest {

	@Test
	public void simpleTest() throws Exception {
		FMSession fmSession = FMSession.login(TestConfig.HOST, 
				 TestConfig.DATABASE,
				 TestConfig.USERNAME,
				 TestConfig.PASSWORD);
		
		FMUploadContainerCommand uc = new FMUploadContainerCommand("Log", 130, "Testcontainer");
		File f = new File("/Users/schube/Downloads/4501673966 EKG 014 an schubec GmbH.pdf");
		uc.setFile(f, "Test.pdf");

		FMResult result = fmSession.execute(uc);
		if (result.isSuccess()) {
			long recordId = result.getFirstRecord().getRecordId();
			System.out.println("Document uploaded, recordid: " + recordId);

		} else {
			System.out.println("Document NOT uploaded, because: " + result.getMessagesAsString());
		}

	}

}