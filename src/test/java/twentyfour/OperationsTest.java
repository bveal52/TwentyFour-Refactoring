package twentyfour;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationsTest {
	@Test
	public void testAddition() {
		Operations operations = new Operations(false);
		operations.plus((float)1, (float)2);

		assertEquals(3, operations.answer, 0.01);
	}

	@Test
	public void testSubtraction() {
		Operations operations = new Operations(false);
		operations.minus((float)1, (float)2);

		assertEquals(-1, operations.answer, 0.01);
	}

	@Test
	public void testMultiplication() {
		Operations operations = new Operations(false);
		operations.times((float)2, (float)3);

		assertEquals(6, operations.answer, 0.01);
	}

	@Test
	public void testDivision() {
		Operations operations = new Operations(false);
		operations.divide((float)1, (float)2);

		assertEquals(0.5, operations.answer, 0.01);
	}
}
