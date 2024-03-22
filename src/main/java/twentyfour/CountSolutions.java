package twentyfour;

import java.util.HashMap;

public class CountSolutions {

	private static HashMap<Integer,Integer> solutionCounts = new HashMap<Integer,Integer>();
	private static int[] countsInOrder;
	private static int maxKey = 0;
	
	public static void addToSolutions(int howMany) {
		if (solutionCounts.containsKey(howMany)) {
			int thisCount = solutionCounts.get(howMany);
			thisCount++;
			solutionCounts.put(howMany, thisCount);
		} else {
			solutionCounts.put(howMany, 1);	
			if (howMany > maxKey) {
				maxKey = howMany;
			}
		}		
	}

	public static String showSolutionCounts() {
		countsInOrder = new int[maxKey+1];
		for (Integer countKey : solutionCounts.keySet()) {
			countsInOrder[countKey] = solutionCounts.get(countKey);			
		}
		String results = "";
		for (int i = 0; i < maxKey; i++) {
			int nextCount = countsInOrder[i];
			if (nextCount != 0) {
				results += i+","+nextCount+"\n";
			}
		}
		return results;
	}
	
}
