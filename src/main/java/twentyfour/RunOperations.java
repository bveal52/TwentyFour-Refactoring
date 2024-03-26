package twentyfour;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class RunOperations {

	private int magicNumber;
	private boolean noNegativeAnswers;
	private boolean noEquivalentsExceptForOrder;
	private static int DBStoppingPoint = 0;  // Does not stop game
//	private static int DBStoppingPoint = 800000000;  // Allows for 5 numbers 0-9 in 24 game
	private static int DBPoint = 0;
	public static int solutionCount = 0;
	public static int nonDuplicatedSolutionCount = 0;
	public static float closeToInteger = .0001f;

	//CHANGE FOR FEATURE
	private Operations myOps;

	//END CHANGE
	protected ArrayList<String> answers = new ArrayList<String>(); // answers as strings for this set of numbers
	public ArrayList<String> sortedAnswers = new ArrayList<String>(); // with operations sorted -- Not the correct answers, just to check for dup's	

	//CHANGE FOR FEATURE
	public RunOperations(int magicNumber, Operations myOps, boolean noNegativeAnswers, boolean noEquivalentsExceptForOrder) {
		this.magicNumber = magicNumber;
		this.myOps = myOps;
		this.noNegativeAnswers = noNegativeAnswers;
		this.noEquivalentsExceptForOrder = noEquivalentsExceptForOrder;
	}
	//END CHANGE

	// Initiates the action for the co-routines -- this method picks the next operation to try and does it.
	public void runAllAnswers(ArrayList<Float> numbersToUse, ArrayList<Result> results) {
		for(int i = 0; i < numbersToUse.size(); i++) {
			for(int j = 0; j < numbersToUse.size(); j++) {
				if (j != i) {
					ArrayList<Float> workingList = new ArrayList<Float>();
					for(int k = 0; k < numbersToUse.size(); k++) {
						if (k != i && k != j) {
							workingList.add(numbersToUse.get(k));
						}
					}
					tryAllOperations(numbersToUse.get(i), numbersToUse.get(j), workingList, results);
				}
			}
		}
	}

	//CHANGE FOR FEATURE

//	 The other co-routine.  This one decides if we have a solution and also saves results, final or intermediate.
private void tryAllOperations(float a, float b, ArrayList<Float> workingList, ArrayList<Result> results) {
	Class<?> classObj = myOps.getClass();
	Class[] arg = new Class[2];
	arg[0] = Float.class;
	arg[1] = Float.class;
	Method nextMethod = null;
	for (int i=0; i< myOps.myOperations.length; i++) {
		String nextOp = myOps.myOperations[i];
		try {
			nextMethod = classObj.getDeclaredMethod(nextOp, arg);
		} catch (NoSuchMethodException | SecurityException e1) {
			System.out.println(">> Check operations list in Operations.java versus the op number called here = #"+i);
			e1.printStackTrace();
			System.exit(1);
		}
		try {
			// invoke the function using this class obj
			// pass in the class object
			nextMethod.invoke(myOps, a, b);
//				System.out.print("DB: At "+DBPoint+", Result for "+a+myOps.operationSymbols[i]+b+"="+myOps.answer+", workingList.size() = "+workingList.size()+", contents:");
//				for (float nextNumber : workingList) {
//					System.out.print(" "+nextNumber);
//				}
//				System.out.println();
			DBPoint++;
			if (DBStoppingPoint != 0 && DBPoint >= DBStoppingPoint) {
				System.out.println("DB: Hit stopping point of "+DBStoppingPoint);
				System.out.println("DB: Solution count is "+solutionCount);
				System.exit(1);
			}
			if (workingList.size() == 0) { // This is the final operation
				if (Math.abs(myOps.answer-magicNumber) < closeToInteger) { // we have a correct answer
					saveAndIncrementCorrectAnswer(a, b, results, i, myOps);
				} else { // a wrong answer
//						results.add("Wrong answer");
//						printAResult(results);
					continue;
				}
			} else { // we're not done yet, trying this combination
				if (!(noNegativeAnswers && myOps.answer < 0)) { // exclude negative intermediate answers if flagged to do so
					excludeNegativeIntermediateAnswers(a, b, workingList, results, myOps, i);
				}
			}
		}
		catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e)
		{
			System.out.println(">> We failed to invoke method #"+i);
			System.out.println(e.getCause());
			System.exit(1);
		}
	}
}



	//END CHANGE

	private void saveAndIncrementCorrectAnswer(float a, float b, ArrayList<Result> results, int i, Operations myOps) {
		//FIXED CODE SMELL
		addNewResultToResultList(a, b, results, i, myOps);
		//END CODE SMELL
		saveAResult(results);
		results.remove(results.size()-1);
		solutionCount++;
	}

	private void excludeNegativeIntermediateAnswers(float a, float b, ArrayList<Float> workingList, ArrayList<Result> results, Operations myOps, int i) {
		workingList.add(myOps.answer);
		//FIXED CODE SMELL
		addNewResultToResultList(a, b, results, i, myOps);
		//END CODE SMELL
		runAllAnswers(workingList, results);
		workingList.remove(workingList.size()-1);
		results.remove(results.size()-1);
	}

	//FIXED CODE SMELL - Duplicated Code
	private static void addNewResultToResultList(float a, float b, ArrayList<Result> results, int i, Operations myOps) {
		ArrayList<Float> operands = new ArrayList<Float>();
		operands.add(a);
		operands.add(b);
		results.add(new Result(operands, i, myOps.answer));
	}
	//END CODE SMELL

	// Called by the above to save correct solutions -- also weeds out duplicates.
	private void saveAResult(ArrayList<Result> results) {
		String stringToSave = "";
		for (Result nextResult : results) {
			stringToSave += nextResult.infix2OperandResult() +"\n";
		}
		for (String answer : answers) { // check for duplication
			if (stringToSave.equals(answer)) return;
		}
		if (noEquivalentsExceptForOrder) { // Need to see if same as another except for ordering of operations
			ArrayList<String> sortedResults = new ArrayList<String>();
			for (Result nextResult : results) { // Make a list of operations in this result, as strings
				sortedResults.add(nextResult.infix2OperandResult() +"\n");
			}
			Collections.sort(sortedResults); // Sort those, so we can compare with prior ones
			String sortedStringToSave = "";
			for (String nextString : sortedResults) { // Turn this result into one long string
				sortedStringToSave += nextString;
			}
			for (String nextAnswer : sortedAnswers) { // Now see if this is a duplicate
				if (sortedStringToSave.equals(nextAnswer)) return;
			}
			sortedAnswers.add(sortedStringToSave);
		}
		answers.add(stringToSave);
		nonDuplicatedSolutionCount++;
	}
	
	// Creates all the answers for one number set
	public String buildAnswers() {
		String returnValue = "";
		for (String answer : answers) {
			returnValue += answer+"------------\n";
		}
		return returnValue;
	}

	public int numberOfAnswers() {
		return answers.size();
	}

	// For debugging -- just to see what these are
	public String buildSortedAnswers() {
		String returnValue = "";
		for (String answer : sortedAnswers) {
			returnValue += answer+"------------\n";
		}
		return returnValue;
	}

}

