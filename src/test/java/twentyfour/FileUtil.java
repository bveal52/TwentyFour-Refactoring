package twentyfour;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

//CHANGE FOR FEATURE TESTING
public class FileUtil {

	public static String readFileContent(String filePath) throws IOException {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append(System.lineSeparator());
			}
		}
		return content.toString();
	}

	public static void deleteFile(String relativeFilePath) {
		String basePath = System.getProperty("user.dir");
		String absoluteFilePath = basePath + File.separator + relativeFilePath;
		File file = new File(absoluteFilePath);
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("File deleted successfully: " + absoluteFilePath);
			} else {
				System.err.println("Failed to delete the file: " + absoluteFilePath);
			}
		} else {
			System.err.println("File does not exist: " + absoluteFilePath);
		}
	}

	//END CHANGE
}
