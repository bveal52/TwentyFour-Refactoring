package twentyfour;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RunOperationsTest {

	@BeforeEach
	public void setUp() {
		RunOperations.solutionCount = 0;
	}

	//Basic Integration Tests For Each Operation
	@Test
	public void testIntegrationRunAllAnswers_AddSolutionsEqualTwo() {
		Operations operations = new Operations(false);
		RunOperations runOperations = new RunOperations(3, operations, true, true);

		ArrayList<Float> numbersToUse = new ArrayList<>();
		ArrayList<Result> results = new ArrayList<>();
		numbersToUse.add((float)1);
		numbersToUse.add((float)2);
		runOperations.runAllAnswers(numbersToUse, results);

		assertEquals(2, RunOperations.solutionCount);
	}

	@Test
	public void testIntegrationRunAllAnswers_SubtractSolutionsEqualOne() {
		Operations operations = new Operations(false);
		RunOperations runOperations = new RunOperations(-1, operations, true, true);

		ArrayList<Float> numbersToUse = new ArrayList<>();
		ArrayList<Result> results = new ArrayList<>();
		numbersToUse.add((float)1);
		numbersToUse.add((float)2);
		runOperations.runAllAnswers(numbersToUse, results);

		assertEquals(1, RunOperations.solutionCount);
	}

	@Test
	public void testIntegrationRunAllAnswers_TimesSolutionsEqualTwo() {
		Operations operations = new Operations(false);
		RunOperations runOperations = new RunOperations(12, operations, true, true);

		ArrayList<Float> numbersToUse = new ArrayList<>();
		ArrayList<Result> results = new ArrayList<>();
		numbersToUse.add((float)3);
		numbersToUse.add((float)4);
		runOperations.runAllAnswers(numbersToUse, results);

		assertEquals(2, RunOperations.solutionCount);
	}

	@Test
	public void testIntegrationRunAllAnswers_DivideSolutionsEqualOne() {
		Operations operations = new Operations(false);
		RunOperations runOperations = new RunOperations(2, operations, true, true);

		ArrayList<Float> numbersToUse = new ArrayList<>();
		ArrayList<Result> results = new ArrayList<>();
		numbersToUse.add((float)2);
		numbersToUse.add((float)1);
		runOperations.runAllAnswers(numbersToUse, results);

		assertEquals(3, RunOperations.solutionCount);
	}

	@Test
	public void testIntegrationRunAllAnswers_AverageSolutionsEqualTwo() {
		Operations operations = new Operations(true);
		RunOperations runOperations = new RunOperations(5, operations, true, true);

		ArrayList<Float> numbersToUse = new ArrayList<>();
		ArrayList<Result> results = new ArrayList<>();
		numbersToUse.add((float)4);
		numbersToUse.add((float)6);
		runOperations.runAllAnswers(numbersToUse, results);

		assertEquals(2, RunOperations.solutionCount);
	}

}
