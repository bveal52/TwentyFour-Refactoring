package twentyfour;

public class Operations {

	public static String[] myOperations;
	public static String[] operationSymbols;
	public static boolean[] commutivity = {true, false, true, false};
	public float answer = (float)0;

	public Operations(boolean average) {

		//lambda for average inclusion
		if(average) {
			myOperations = new String[] {"plus", "minus", "times", "divide", "average"};
			operationSymbols = new String[] {"+", "-", "*", "/", "avg"};
			commutivity = new boolean[] {true, false, true, false, true};
		} else {
			myOperations = new String[] {"plus", "minus", "times", "divide"};
			operationSymbols = new String[] {"+", "-", "*", "/"};
			commutivity = new boolean[] {true, false, true, false};
		}

	}
	public void plus(Float a, Float b) {
		answer = a+b;
	}

	public void minus(Float a, Float b) {
		answer = a-b;
	}

	public void times(Float a, Float b) {
		answer = a*b;
	}

	public void divide(Float a, Float b) {
		answer = a/b;
	}

	public void average(Float a, Float b) {
		answer = (a+b)/2;
	}


}
