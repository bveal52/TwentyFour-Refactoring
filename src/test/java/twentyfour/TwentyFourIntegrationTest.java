package twentyfour;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TwentyFourIntegrationTest {

	@Test
	public void testTwentyFour_CurrentConfiguration() throws IOException {

		TwentyFour.runTest(4, 1, 6, 12, true,true, false);
		// Compare the output to the expected output
		String actualOutput = FileUtil.readFileContent("./datafiles/Solutions12for4from1to6-2.txt");

		String expectedOutput = FileUtil.readFileContent("./datafiles/Solutions12for4from1to6-1.txt");

		assertEquals(expectedOutput, actualOutput);

		//delete file from actual when done with test
		//get relative path to file in project
		FileUtil.deleteFile("./datafiles/Solutions12for4from1to6-2.txt");
		}

	@Test
	public void testTwentyFour_OriginalOperationsOutputFiles2() throws IOException {

		//Run TwentyFour
		TwentyFour.runTest(4, 1, 9, 24, true, true, false);

		// Compare the output to the expected output
		String actualOutput = FileUtil.readFileContent("./datafiles/Solutions24for4from1to9-2.txt");

		String expectedOutput = FileUtil.readFileContent("./datafiles/Solutions24for4from1to9-1.txt");

		assertEquals(expectedOutput, actualOutput);

		//delete file from actual when done with test
		FileUtil.deleteFile("./datafiles/Solutions24for4from1to9-2.txt");
	}

	@Test
	public void testTwentyFour_withAverageOperations() throws IOException {

		//Run TwentyFour
		TwentyFour.runTest(4, 1, 4, 24, true, true, true);

		// Compare the output to the expected output
		String actualOutput = FileUtil.readFileContent("datafiles/Solutions24for4from1to4withAverage-2.txt");

		String expectedOutput = FileUtil.readFileContent("datafiles/Solutions24for4from1to4withAverage-1.txt");

		assertEquals(expectedOutput, actualOutput);

		//delete file from actual when done with test
		FileUtil.deleteFile("datafiles/Solutions24for4from1to4withAverage-2.txt");
	}

}
