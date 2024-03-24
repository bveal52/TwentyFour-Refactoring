package twentyfour;

public class MillisecondsStrategy implements TimingStrategy {

	@Override
	public String formatTime(double runTime, String descriptor, String progress) {
		return String.format(descriptor+" runtime = %.2f seconds", runTime);
	}
}
