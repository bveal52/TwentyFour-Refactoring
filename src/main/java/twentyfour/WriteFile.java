package twentyfour;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {

	private String directoryName = "datafiles";
	private BufferedWriter writer;
	private int magicNumber = 0;
	private int numberOfIntegers;
	private int startingAt;
	private int endingAt;
	private String baseFileName = "Solutions";
	private String extension = ".txt";
	public static ArrayList<String> fileTable = new ArrayList<String>(); // List to reach for in post-run validations
	public static int fileIndex = 0;
	private String fullFileName;
	
	public WriteFile(int magicNumber, int numberOfIntegers, int startingAt, int endingAt) {
		this.magicNumber = magicNumber;
		this.numberOfIntegers = numberOfIntegers;
		this.startingAt = startingAt;
		this.endingAt = endingAt;
	}
	
	public void openFile() {
		String debugInfo;
		if (TwentyFour.debugMode > 0) {
			debugInfo = "DB"+TwentyFour.debugMode.toString();
		} else {
			debugInfo = "";
		}
		String fileName = baseFileName+magicNumber+debugInfo+"for"+numberOfIntegers+"from"+startingAt+"to"+endingAt;
		int appendix = returnNextFileNameAppendix(fileName);
		fullFileName = directoryName+"/"+fileName+"-"+appendix+extension;
		try {
			writer = new BufferedWriter(new FileWriter(fullFileName));
		} catch (IOException e) {
			System.out.println(">> Error opening file "+fullFileName);
			e.printStackTrace();
			System.exit(1);
		}
		try {
			writer.write("Solutions for magic number "+magicNumber+"\n");
		} catch (IOException e) {
			System.out.println(">> Error writing to file"+completeErrorMessage());
			e.printStackTrace();
			System.exit(1);
		}		
	}

	public void writeToFile(String data) {
		try {
			writer.write(data+"\n");
		} catch (IOException e) {
			System.out.println(">> Error writing to file"+completeErrorMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void closeFile() {
		try {
			writer.close();
			System.out.println("Results are in file "+fullFileName);
		} catch (IOException e) {
			System.out.println(">> Error closing file"+completeErrorMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private String completeErrorMessage() {
		return " -- check contents of "+fullFileName;
	}

	public int returnNextFileNameAppendix(String fileNameStem) {
//		System.out.println("DB: In WriteFile.returnNextFileNameAppendix with fileNameStem="+fileNameStem);
		readDirectory(directoryName);
		int nameIndexNo = 0;
		for (int i=0; i<fileIndex; i++) {
			String nextName = fileTable.get(i);
//			System.out.println("DB: Next file name is "+nextName);
			int hyphenLocation = nextName.lastIndexOf('-');
			int extraSpaceLocation = nextName.indexOf(' ',hyphenLocation+1); 
			// check for duplicate file name suffixes added by OS
			if (hyphenLocation != -1) { // It's a real file name, not just a directory name
				String nameIndex;
				if (extraSpaceLocation == -1) { // This is a normal fileName
					int periodLocation = nextName.indexOf('.',hyphenLocation+1);
					nameIndex = nextName.substring(hyphenLocation+1,periodLocation);
//					System.out.println("DB: nameIndex = "+nameIndex);
				} else { // duplicate file with space and suffix number -- count it to skip past
					nameIndex = nextName.substring(hyphenLocation+1,extraSpaceLocation);					
				}
				String nextNameStem = nextName.substring(0,hyphenLocation);
//				System.out.println("DB: nextNameStem = "+nextNameStem);
				if (nextNameStem.equals(fileNameStem)) {
					int thisAppendixNo = Integer.parseInt(nameIndex);
					if (thisAppendixNo > nameIndexNo) {
						nameIndexNo = thisAppendixNo;
//						System.out.println("DB: nameIndexNo now = "+nameIndexNo);
					}
				}
			}
		}
//		System.out.println("DB: and final nameIndexNo is = "+nameIndexNo);
//		System.out.println("DB: and new nameIndexNo+1 = "+(nameIndexNo+1));
		return nameIndexNo+1;
	}

	public void readDirectory(String directoryFile) {
		fileIndex = 0;
		File f = new File(directoryFile); // start here
		File[] files = f.listFiles();
		for (File file : files) {
			if (file.getName().charAt(0) !='.') { // skip hidden Eclipse files
				fileTable.add(file.getName());
				fileIndex++;
			}
		}
	}

	
}
