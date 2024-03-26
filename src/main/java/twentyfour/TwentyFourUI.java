package twentyfour;

public class TwentyFourUI {

	public void displayGameStart(GameParameter params) {
		System.out.println("Starting TwentyFour game with "+params.getNumberOfIntegers()+" integers, ranging from "+
				params.getStartingAt()+" to "+params.getEndingAt() +", looking for "+params.getMagicNumber());
	}

	// Since the user can change the parameters shown at the top of this file, we check that they make sense
	public boolean validateParameters(GameParameter params) {
		if (params.getNumberOfIntegers() < 2 || params.getNumberOfIntegers() > 6) {
			System.out.println(">> Unlikely we can solve a problem with numberOfIntegers = "+params.getNumberOfIntegers());
			return false;
		}
		return true;
	}

	public String generateClosingMessage(GameParameter params, int totalNumberCombinations) {
		//String closingMessage = Timing.reportTime("Total time", "")+"\n";
		String closingMessage = "Results for magic number "+params.getMagicNumber()+", with "+params.getNumberOfIntegers()+" integers "+
				" ranging from "+params.getStartingAt()+" to "+params.getEndingAt()+",\n negative intermediate answers = "+(!params.getNoNegativeAnswers())+
				", equivalent intermediate results in different orders = "+(!params.getNoEquivalentsExceptForOrder())+"\n";
		closingMessage += "Total number combinations = "+totalNumberCombinations+", solutions found = "+RunOperations.solutionCount+
				", non-duplicated = "+RunOperations.nonDuplicatedSolutionCount;

		return closingMessage;
	}

	public String generateAnalysisMessage(GameParameter params) {
		String analysisMessage = "How many no. combos with each no. of answers:\nno. answers,how many\n";
		analysisMessage += CountSolutions.showSolutionCounts();

		return analysisMessage;
	}


	public void printClosingAndAnalysis(String closingMessage, String analysisMessage) {
		System.out.println(closingMessage);
		System.out.println(analysisMessage);
	}
}
