package twentyfour;

public class Timing {

	private static long startTime;
	private static long lastReportTime;
	private static double reportIntervalSeconds = 60.0;
	private static double runTime;
	
	public static void startRun() {
		startTime = System.nanoTime();
		lastReportTime = startTime;
	}
	
	public static void checkCurrentTime(String progress) {
		double elapsedTime = (System.nanoTime() - lastReportTime)/1000000000.0;
		if (elapsedTime > reportIntervalSeconds) { // Heartbeat report
			lastReportTime = System.nanoTime();
			System.out.println(reportTime("Current", progress));
		}
	}
	
	public static String reportTime(String descriptor, String progress) {
		runTime = ((System.nanoTime()-startTime)/1000000000.0); // in seconds
		String returnValue = ""; 
		if (runTime > 3600) {
			int totalSeconds = (int)runTime;
			int hours = totalSeconds / 3600;
			int remainingSeconds = totalSeconds % 3600;
			int minutes = remainingSeconds / 60;
			int seconds = remainingSeconds % 60;
			returnValue = String.format(descriptor+" runtime = %d:%2d:%2d", hours, minutes, seconds);
		} else if (runTime > 60) {
			int totalSeconds = (int)runTime;
			int minutes = totalSeconds / 60;
			int seconds = totalSeconds % 60;
			returnValue = String.format(descriptor+" runtime = %d:%2d", minutes, seconds);
		} else if (runTime > 10) {
			returnValue =  String.format(descriptor+" runtime = %d seconds", (int)runTime);
		} else if (runTime > 1) {
			returnValue = String.format(descriptor+" runtime = %.1f seconds", runTime);
		} else {
			returnValue = String.format(descriptor+" runtime = %.2f seconds", runTime);
		}
		if (!progress.equals("")) {
			return returnValue + ", progressed through "+progress;
		} else {
			return returnValue;
		}
	}
}
