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
		if (OperationsWithAverage.commutivity[operationIndex]) { // put the operands in order
			Collections.sort(this.operands);
		}
	}

	//CHANGE FOR FEATURE
	public String infix2OperandResult() {
		if(OperationsWithAverage.operationSymbols[operationIndex].equals("avg")) {
			return formatAverage();
		} else {
			return formatNormalOp();
		}
	}

	private String formatAverage() {
		return "avg(" + tryRounding(operands.get(0)) + ", " + tryRounding(operands.get(1)) + ")" + " = " + tryRounding(result);
	}

	private String formatNormalOp() {
		return tryRounding(operands.get(0)) + " " + OperationsWithAverage.operationSymbols[operationIndex] + " " +
				tryRounding(operands.get(1)) + " " + "=" + " " + tryRounding(result);
	}

	//END CHANGE

	private String tryRounding(Float x) { // idea is that, if they are really integers, show without .0
		Integer closestInt = (int)(x + RunOperations.closeToInteger);
		if (Math.abs(x - closestInt) < RunOperations.closeToInteger) { // close enough to call it an int
			return closestInt.toString();
		} else {
			return x.toString();
		}
	}
	
}
