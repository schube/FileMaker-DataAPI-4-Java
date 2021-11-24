package com.schubec.libs;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.implementation.FMUploadContainerCommand;
import com.schubec.libs.filemaker.results.FMResult;

public class UploadContainerTest {

	@Test
	public void simpleTest() throws Exception {
		try (FMSession fmSession = FMSession.login(TestConfig.HOST,
				TestConfig.DATABASE,
				TestConfig.USERNAME,
				TestConfig.PASSWORD)) {

			FMUploadContainerCommand uc = new FMUploadContainerCommand("Log", 130, "Testcontainer");
			File f = new File("/Users/schube/Documents/myworkspace/FileMaker-DataAPI-4-Java/README.md");
			uc.setFile(f, "README.md");
			fmSession.setDebug(true);
			FMResult result = fmSession.execute(uc);
			System.out.println("URI: " + result.getRequestUri().toString());
			System.out.println("URI: " + result.getHttpBody());

			String recordId = result.getResponse().getModId();
			System.out.println("Document uploaded, modid: " + recordId);

		}
	}
}