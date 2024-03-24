package twentyfour;

import static java.lang.String.format;

public class HoursStrategy implements TimingStrategy {

	@Override
	public String formatTime(double runTime, String descriptor, String progress) {
		int totalSeconds = (int)runTime;
		int hours = totalSeconds / 3600;
		int remainingSeconds = totalSeconds % 3600;
		int minutes = remainingSeconds / 60;
		int seconds = remainingSeconds % 60;
		return String.format(descriptor+" runtime = %d:%2d:%2d", hours, minutes, seconds);
	}
}
