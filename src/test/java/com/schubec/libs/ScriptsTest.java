package com.schubec.libs;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.implementation.FMListScriptsCommand;
import com.schubec.libs.filemaker.results.FMScriptsResult;

public class ScriptsTest {

	@Test
	public void simpleTest() {
		try {
			FMSession fmSession = FMSession.login("XXX", "XXX", "XXX", "XXX");
			FMListScriptsCommand fmScripts = new FMListScriptsCommand();
			FMScriptsResult result = fmSession.execute(fmScripts);

		} catch (Exception e) {
			fail("Should not have thrown any exception", e);
		}
	}

}