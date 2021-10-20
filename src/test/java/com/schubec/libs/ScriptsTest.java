package com.schubec.libs;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.implementation.FMListScriptsCommand;
import com.schubec.libs.filemaker.results.FMScriptsResult;
import com.schubec.libs.filemaker.results.Script;

public class ScriptsTest {

	@Test
	public void simpleTest() {
		try {
			FMSession fmSession = FMSession.login(TestConfig.HOST,
					TestConfig.DATABASE,
					TestConfig.USERNAME,
					TestConfig.PASSWORD);
			FMListScriptsCommand fmScripts = new FMListScriptsCommand();
			FMScriptsResult result = fmSession.execute(fmScripts);
			for (Script script : result.getResponse().getScripts()) {
				System.out.println(script.getName());
			}

		} catch (Exception e) {
			fail("Should not have thrown any exception", e);
		}
	}

}