package twentyfour;

public class MinutesStrategy implements TimingStrategy {

	@Override
	public String formatTime(double runTime, String descriptor, String progress) {
		int totalSeconds = (int)runTime;
		int minutes = totalSeconds / 60;
		int seconds = totalSeconds % 60;
		return String.format(descriptor+" runtime = %d:%2d", minutes, seconds);
	}
}
