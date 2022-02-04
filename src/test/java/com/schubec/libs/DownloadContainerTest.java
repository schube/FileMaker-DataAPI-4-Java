package com.schubec.libs;

import java.io.File;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.schubec.libs.filemaker.FMSession;
import com.schubec.libs.filemaker.base.FMCommandBase;
import com.schubec.libs.filemaker.implementation.FMGetRecordByIdCommand;
import com.schubec.libs.filemaker.results.FMRecordsResponse;
import com.schubec.libs.filemaker.results.FMResult;

public class DownloadContainerTest {
	private static final String LAYOUT_LOG = "Log";

	@Test
	public void simpleTest() throws Exception {
		try (FMSession fmSession = FMSession.login(TestConfig.HOST,
				TestConfig.DATABASE,
				TestConfig.USERNAME,
				TestConfig.PASSWORD)) {

			FMCommandBase fmGetbyId = new FMGetRecordByIdCommand(LAYOUT_LOG, 46l);
			FMResult<FMRecordsResponse> result4 = fmSession.execute(fmGetbyId);
			if (result4.getResponse().getFirstRecord().isPresent()) {
				String urlToContainer = result4.getResponse().getFirstRecord().get().getField("Testcontainer");

				Optional<byte[]> containerdata = FMSession.getContainerdata(urlToContainer);

				if (containerdata.isPresent()) {
					File targetFile = new File("test.tmp");
					FileUtils.writeByteArrayToFile(targetFile, containerdata.get());
				}
			}
		}
	}

}