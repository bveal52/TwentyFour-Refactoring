package twentyfour;

import java.util.ArrayList;


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
	private int endingAt = 4;
	private int magicNumber = 24;
	private boolean noNegativeAnswers = true; // If magicNumber is positive, these are essentially duplicate solutions
	private boolean noEquivalentsExceptForOrder = true; // If the only difference is which is done first, weed these out

	private boolean withAverage = true; // If true, the average of two numbers is also considered a number to use
	GameParameter gameParameter;
	
	private final WriteFile writeFile;

	private final TwentyFourUI twentyFourUI;
	private int totalNumberCombinations = 0;

	private Operations operations;
		
	public static Integer debugMode = 0; // 0 = normal. 1 = use just one number set and do some special analysis
	private int[] testSet = {1,2,3,4};

	//CHANGE FOR FEATURE
	public TwentyFour() {
		gameParameter = new GameParameter(magicNumber, numberOfIntegers, startingAt, endingAt, noNegativeAnswers, noEquivalentsExceptForOrder, withAverage);
		//FIXED CODE SMELL - Long Parameter List
		writeFile = new WriteFile(gameParameter);
		//END CODE SMELL
		twentyFourUI = new TwentyFourUI();

		if(withAverage) {
			operations = new Operations(true);
		} else {
			operations = new Operations(false);
		}
	}

	//INTRODUCED FOR TESTING ONLY
	public TwentyFour(int numberOfIntegers, int startingAt, int endingAt, int magicNumber, boolean noNegativeAnswers, boolean noEquivalentsExceptForOrder, boolean withAverage) {
		gameParameter = new GameParameter(magicNumber, numberOfIntegers, startingAt, endingAt, noNegativeAnswers, noEquivalentsExceptForOrder, withAverage);
		writeFile = new WriteFile(gameParameter);
		twentyFourUI = new TwentyFourUI();

		if(gameParameter.getWithAverage()) {
			operations = new Operations(true);
		} else {
			operations = new Operations(false);
		}
	}

	//END CHANGE


	//FIX CODE SMELL - LARGE CLASS
	// Picks the successive number sets and calls the process to run those, below.
	private void runGame() {
		ArrayList<Integer> numbersToUse = new ArrayList<Integer>();
		if (debugMode == 1) {
			for (int i=0; i<gameParameter.getNumberOfIntegers();i++) {
				numbersToUse.add(testSet[i]);
			}
		} else { // normal mode
			for (int i=0; i<gameParameter.getNumberOfIntegers();i++) {
				numbersToUse.add(gameParameter.getStartingAt());
			}
		}
		boolean done = false;
		while (!done) {
			processNumberChoices(numbersToUse);
			done = updateNumberSet(done, numbersToUse);
		}
	}

	private boolean updateNumberSet(boolean done, ArrayList<Integer> numbersToUse) {
		if (debugMode == 1) {
			done = true; // just run the designated set of numbers
		} else {
			for (int posn = gameParameter.getNumberOfIntegers()-1; posn >= 0; posn--) { // Go on to next number combination
				int currentInt = numbersToUse.get(posn);
				if (posn == 0) { // no additional restriction on what value can be
					if (currentInt < gameParameter.getEndingAt()) {
						currentInt++;
						numbersToUse.set(posn, currentInt);
						break;
					} else {
						numbersToUse.set(posn, gameParameter.getStartingAt());
						if (posn == 0) done = true;
					}
				} else { // additional restriction is that the values are in non-ascending order, to avoid duplication
					if (currentInt < gameParameter.getEndingAt() && currentInt < numbersToUse.get(posn-1)) {
						currentInt++;
						numbersToUse.set(posn, currentInt);
						break;
					} else {
						numbersToUse.set(posn, gameParameter.getStartingAt());
						if (posn == 0) done = true;
					}
				}
			}
		}
		return done;
	}

	// Processes a particular number set, including writing it to a common file.
	private void processNumberChoices(ArrayList<Integer> numbersToUse) {
		totalNumberCombinations++;
		String nextNumber = "> ";
		for (int number : numbersToUse) {
			nextNumber += number+" ";
		}
		RunOperations nextSet = new RunOperations(gameParameter.getMagicNumber(), operations, gameParameter.getNoNegativeAnswers(), gameParameter.getNoEquivalentsExceptForOrder());
		ArrayList<Result> results = new ArrayList<Result>();
		ArrayList<Float> workingList = new ArrayList<Float>();
		for(int i = 0; i < numbersToUse.size(); i++) {
			workingList.add((float)numbersToUse.get(i));			
		}
		nextSet.runAllAnswers(workingList, results);
		Timing.checkCurrentTime(nextNumber);
		CountSolutions.addToSolutions(nextSet.numberOfAnswers());
		//FIXED CODE SMELL - LARGE CLASS
		writeFile.writeNextNumberAndNextSet(nextNumber, nextSet);
		//END CODE SMELL
	}


	// Initializes file writing and starts the successive number-set action, above.
	public static void main(String[] args) {
		run();
	}


	//CHANGE FOR FEATURE TESTING
	public static void run() {
		Timing.startRun();
		TwentyFour myGame = new TwentyFour();
		if (!myGame.twentyFourUI.validateParameters(myGame.gameParameter)) System.exit(1); // see if parameters make sense
		myGame.writeFile.openFile();
		myGame.runGame();

		String closingMessage = myGame.twentyFourUI.generateClosingMessage(myGame.gameParameter, myGame.totalNumberCombinations);
		myGame.writeFile.writeToFile(closingMessage);

		String analysisMessage = myGame.twentyFourUI.generateAnalysisMessage(myGame.gameParameter);
		myGame.writeFile.writeToFile(analysisMessage);
		myGame.writeFile.closeFile();

		myGame.twentyFourUI.printClosingAndAnalysis(closingMessage, analysisMessage);
	}

	public static void runTest(int numberOfIntegers, int startingAt, int endingAt, int magicNumber, boolean noNegativeAnswers, boolean noEquivalentsExceptForOrder, boolean withAverage) {
		Timing.startRun();
		TwentyFour myGame = new TwentyFour(numberOfIntegers, startingAt, endingAt, magicNumber, noNegativeAnswers, noEquivalentsExceptForOrder, withAverage);
		if (!myGame.twentyFourUI.validateParameters(myGame.gameParameter)) System.exit(1); // see if parameters make sense
		myGame.writeFile.openFile();
		myGame.runGame();

		String closingMessage = myGame.twentyFourUI.generateClosingMessage(myGame.gameParameter, myGame.totalNumberCombinations);
		myGame.writeFile.writeToFile(closingMessage);

		String analysisMessage = myGame.twentyFourUI.generateAnalysisMessage(myGame.gameParameter);
		myGame.writeFile.writeToFile(analysisMessage);
		myGame.writeFile.closeFile();

		myGame.twentyFourUI.printClosingAndAnalysis(closingMessage, analysisMessage);
	}

	//END FIX BAD CODE SMELL - Large Class

	//END CHANGE

}
