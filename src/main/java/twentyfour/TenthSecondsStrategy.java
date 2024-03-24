package twentyfour;

public class TenthSecondsStrategy implements TimingStrategy{

	@Override
	public String formatTime(double runTime, String descriptor, String progress) {
		return String.format(descriptor+" runtime = %.1f seconds", runTime);
	}
}
