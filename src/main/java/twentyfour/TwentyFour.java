package twentyfour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/*
 *  This is a new and improved "TwentyFour" game, written in June, 2021.  It has better logic and OO properties
 *  than the original, written about 15 years ago.  Basically, it solves the "24" math card game, which has 4
 *  numbers on each card that must be used to get 24 arithmetically, using each number exactly once.
 *  
 *  This new version of the program will handle any number of numbers (versus 4), and total they need to add
 *  up to (versus 24), and any starting and ending point for the numbers (instead of their being just 1
 *  through 9).
 *  
 *  How to play the real game - see https://www.24game.com/t-about-howtoplay.aspx
 *  
 *  The last time I counted, this program was about 675 lines long, so fairly compact and possibly even
 *  elegant in the way it solves this problem.  It allows for fractional intermediate answers, and so can find
 *  solutions for number combinations like the infamous singular solution for 7 7 3 3 : 
 *  3 / 7 = 0.42857143
 *  0.42857143 + 3 = 3.4285715
 *  3.4285715 * 7 = 24
 *  
 *  For the standard 24 game using 4 numbers 1 - 9 with just + - * and /, the program declares that, for the
 *  495 unique number combinations, there are 3652 non-duplicated solutions.
 */

public class TwentyFour {
	
	// These parameters are set by the user to make this logic solve slightly different problems:
	private int numberOfIntegers = 4;
	private int startingAt = 1;
	private int endingAt = 10;
	private int magicNumber = 24;
	private boolean noNegativeAnswers = true; // If magicNumber is positive, these are essentially duplicate solutions
	private boolean noEquivalentsExceptForOrder = true; // If the only difference is which is done first, weed these out
	
	private WriteFile writeFile;
	private int totalNumberCombinations = 0;
		
	public static Integer debugMode = 0; // 0 = normal. 1 = use just one number set and do some special analysis
	private int[] testSet = {3, 3, 2, 1};
	
	// Picks the successive number sets and calls the process to run those, below.
	private void runGame() {
		ArrayList<Integer> numbersToUse = new ArrayList<Integer>();
		if (debugMode == 1) {
			for (int i=0; i<numberOfIntegers;i++) {
				numbersToUse.add(testSet[i]);
			}
		} else { // normal mode
			for (int i=0; i<numberOfIntegers;i++) {
				numbersToUse.add(startingAt);
			}
		}
		boolean done = false;
		while (!done) {
			processNumberChoices(numbersToUse);
			if (debugMode == 1) {
				done = true; // just run the designated set of numbers
			} else {
				for (int posn = numberOfIntegers-1; posn >= 0; posn--) { // Go on to next number combination
					int currentInt = numbersToUse.get(posn);
					if (posn == 0) { // no additional restriction on what value can be
						if (currentInt < endingAt) {
							currentInt++;
							numbersToUse.set(posn, currentInt);
							break;
						} else {
							numbersToUse.set(posn, startingAt);
							if (posn == 0) done = true;
						}
					} else { // additional restriction is that the values are in non-ascending order, to avoid duplication
						if (currentInt < endingAt && currentInt < numbersToUse.get(posn-1)) {
							currentInt++;
							numbersToUse.set(posn, currentInt);
							break;
						} else {
							numbersToUse.set(posn, startingAt);
							if (posn == 0) done = true;
						}
					}
				}
			}
		}
	}

	// Processes a particular number set, including writing it to a common file.
	private void processNumberChoices(ArrayList<Integer> numbersToUse) {
		totalNumberCombinations++;
		String nextNumber = "> ";
		for (int number : numbersToUse) {
			nextNumber += number+" ";
		}
		RunOperations nextSet = new RunOperations(magicNumber, noNegativeAnswers, noEquivalentsExceptForOrder);
		ArrayList<Result> results = new ArrayList<Result>();
		ArrayList<Float> workingList = new ArrayList<Float>();
		for(int i = 0; i < numbersToUse.size(); i++) {
			workingList.add((float)numbersToUse.get(i));			
		}
		nextSet.runAllAnswers(workingList, results);
		Timing.checkCurrentTime(nextNumber);
		CountSolutions.addToSolutions(nextSet.numberOfAnswers());
		writeFile.writeToFile(nextNumber+": "+nextSet.numberOfAnswers()+" answers");
		writeFile.writeToFile(nextSet.buildAnswers());
		if (debugMode == 1) {
			writeFile.writeToFile("---------------\nSorted Answers:\n");
			writeFile.writeToFile(nextSet.buildSortedAnswers());			
		}
	}

	// Initializes file writing and starts the successive number-set action, above.
	public static void main(String[] args) {
		Timing.startRun();
		TwentyFour myGame = new TwentyFour();
		if (!myGame.validateParameters()) System.exit(1); // see if parameters make sense
		myGame.writeFile = new WriteFile(new GameParameter(myGame.magicNumber, myGame.numberOfIntegers, myGame.startingAt, myGame.endingAt));
		myGame.writeFile.openFile();
		myGame.runGame();
		String closingMessage = Timing.reportTime("Total time", "")+"\n";
		closingMessage += "Summary results for magic number "+myGame.magicNumber+", with "+myGame.numberOfIntegers+" integers "+
				" ranging from "+myGame.startingAt+" to "+myGame.endingAt+",\n negative intermediate answers = "+(!myGame.noNegativeAnswers)+
				", equivalent intermediate results in different orders = "+(!myGame.noEquivalentsExceptForOrder)+" :\n";
		closingMessage += "Total number combinations = "+myGame.totalNumberCombinations+", solutions found = "+RunOperations.solutionCount+
				", non-duplicated = "+RunOperations.nonDuplicatedSolutionCount;
		myGame.writeFile.writeToFile(closingMessage);
		String analysisMessage = "How many no. combos with each no. of answers:\nno. answers,how many\n";
		analysisMessage += CountSolutions.showSolutionCounts();
		myGame.writeFile.writeToFile(analysisMessage);
		myGame.writeFile.closeFile();
		System.out.println(closingMessage);
		System.out.println(analysisMessage);
	}

	// Since the user can change the parameters shown at the top of this file, we check that they make sense
	private boolean validateParameters() {
		if (this.numberOfIntegers < 2 || this.numberOfIntegers > 6) {
			System.out.println(">> Unlikely we can solve a problem with numberOfIntegers = "+this.numberOfIntegers);
			return false;
		}
		return true;
	}
}
