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


	//FIXED CODE SMELL - Switch Statement
	public static String reportTime(String descriptor, String progress) {
		TimingStrategy strategy;
		runTime = ((System.nanoTime()-startTime)/1000000000.0); // in seconds
		String returnValue = ""; 
		if (runTime > 3600) {
			strategy = new HoursStrategy();
		} else if (runTime > 60) {
			strategy = new MinutesStrategy();
		} else if (runTime > 10) {
			strategy = new SecondsStrategy();
		} else if (runTime > 1) {
			strategy = new TenthSecondsStrategy();
		} else {
			strategy = new MillisecondsStrategy();
		}
		if (!progress.equals("")) {
			return strategy.formatTime(runTime, descriptor, progress) + ", progressed through "+ progress;
		} else {
			return strategy.formatTime(runTime, descriptor, progress);
		}
	}

	//END CODE SMELL - Switch Statement
}
