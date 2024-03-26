package twentyfour;

public class SecondsStrategy implements TimingStrategy{

	@Override
	public String formatTime(double runTime, String descriptor, String progress) {
		return String.format(descriptor+" runtime = %d seconds", (int)runTime);
	}
}
