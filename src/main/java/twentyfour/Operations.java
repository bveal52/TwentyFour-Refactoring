package twentyfour;

public class Operations {

	public static String[] myOperations = {"plus", "minus", "times", "divide"};
	public static String[] operationSymbols = {"+", "-", "*", "/"};
	public static boolean[] commutivity = {true, false, true, false};
	
	public float answer = (float)0;
	
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
}
