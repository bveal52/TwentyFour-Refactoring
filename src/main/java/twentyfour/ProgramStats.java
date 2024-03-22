package twentyfour;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/*
 * This little program is designed to be loaded into other projects to see length and structure.
 * It duplicates the logic in main of WriteFile.java.
 * 5-22-2022 Total of 8 files and 676 lines, 59 attributes and 41 methods
 */

public class ProgramStats {
	
	  public static void main(String[] args) throws IOException {  
		  buildSourceList(new File("src"));
		  System.out.println("Analysis of source files:");
		  int i;
		  for (i=0; i<sourceList.size(); i++) {
			  System.out.print(sourceList.get(i).getName());
			  int nLines = readNextFile(sourceList.get(i));
			  System.out.println(" "+nLines+" lines");
			  totalLines = totalLines+nLines;
			  if (nLines > largestLines) {
				  largestLines = nLines;
				  largestFile = sourceList.get(i).getName();
			  }
		  }
		  System.out.println("--------------------------------------");
		  System.out.println("Total of "+i+" files and "+totalLines+" lines, "+nTotalAttributes+
				  " attributes and "+nTotalMethods+" methods");
		  System.out.println("Largest file is "+largestFile+" with "+largestLines+" lines");
		  System.out.println("Class with most attributes is "+classWithMostAttributes+", with "+nMostAttributes);
		  System.out.println("Class with most methods is "+classWithMostMethods+", with "+nMostMethods);
		  System.out.println("Directories and number of contents (file or dir):");
		  for (i=0; i<dirList.size();i++) {
			  System.out.println(dirList.get(i).getPath()+": "+dirContentsCount.get(i));
		  }
		  System.out.println("(ProgramStats.java itself is 13 attributes and 4 methods, 108 lines of this)");
	  } 

	  private static void buildSourceList(File dirName) {
			dirList.add(dirName);
			File[] files = dirName.listFiles();
			dirContentsCount.add(files.length);
			for (File file : files) {
				if (file.getName().charAt(0) !='.') { // skip hidden Eclipse files
					if (file.isDirectory()) {
						buildSourceList(file);
					} else {
						sourceList.add(file);
					}
				}
			}
	  }
	  
	  private static ArrayList<File> sourceList = new ArrayList<File>();
	  private static ArrayList<File> dirList = new ArrayList<File>();
	  private static ArrayList<Integer> dirContentsCount = new ArrayList<Integer>();
	  private static int totalLines = 0;
	  private static int largestLines = 0;
	  private static String largestFile = "";
	  private static String[] privacyChoices = {"public", "private", "protected"};
	  private static int nTotalMethods = 0;
	  private static int nTotalAttributes = 0;
	  private static String classWithMostAttributes = "";
	  private static int nMostAttributes = 0;
	  private static String classWithMostMethods = "";
	  private static int nMostMethods = 0;
	  
	  // Counts lines in a file
	  public static int readNextFile(File file) throws IOException {
		  int nMethods = 0;
		  int nAttributes = 0;
		  int lineNo = 0;
		  //System.out.println("Using file "+file);
		  BufferedReader br = new BufferedReader(new FileReader(file)); 
		  String st = br.readLine();
		  while ((st = br.readLine()) != null) {
			  lineNo++;
			  for (String privacyChoice : privacyChoices) {
				  if (st.indexOf(privacyChoice) >=0) {
					  if (st.indexOf(';') >= 0 || (st.indexOf('{') >= 0 && st.indexOf('(') == 0)) { // an attribute
						  nAttributes++;
						  nTotalAttributes++;
					  } else {
						  nMethods++;
						  nTotalMethods++;
					  }
					  break;
				  }
			  }
		  }
		  System.out.print(" with "+nAttributes+" attributes and "+nMethods+" methods, ");
		  if (nAttributes > nMostAttributes) {
			  nMostAttributes = nAttributes;
			  classWithMostAttributes = file.getName();
		  }
		  if (nMethods > nMostMethods) {
			  nMostMethods = nMethods;
			  classWithMostMethods = file.getName();
		  }
		  return lineNo;
		  
	  }
}