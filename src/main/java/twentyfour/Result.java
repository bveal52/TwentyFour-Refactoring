package twentyfour;

import java.util.ArrayList;
import java.util.Collections;

public class Result {

	public ArrayList<Float> operands = new ArrayList<Float>();
	public int operationIndex = -1;
	public Float result = (float)0.0;
	
	public Result(ArrayList<Float> operands, int operationIndex, Float result) {
		this.operands = operands;
		this.operationIndex = operationIndex;
		this.result = result;
		if (Operations.commutivity[operationIndex]) { // put the operands in order
			Collections.sort(this.operands);
		}
	}
	
	public String infix2OperandResult() {
		return tryRounding(operands.get(0))+" "+Operations.operationSymbols[operationIndex]+" "+
				tryRounding(operands.get(1))+" "+"="+" "+tryRounding(result);
	}
	
	private String tryRounding(Float x) { // idea is that, if they are really integers, show without .0
		Integer closestInt = (int)(x + RunOperations.closeToInteger);
		if (Math.abs(x - closestInt) < RunOperations.closeToInteger) { // close enough to call it an int
			return closestInt.toString();
		} else {
			return x.toString();
		}
	}
	
}
