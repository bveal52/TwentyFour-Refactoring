package twentyfour;

public class GameParameter {

    private int magicNumber = 0;
    private int numberOfIntegers;
    private int startingAt;
    private int endingAt;

    public GameParameter(int magicNumber, int numberOfIntegers, int startingAt, int endingAt) {
        this.magicNumber = magicNumber;
        this.numberOfIntegers = numberOfIntegers;
        this.startingAt = startingAt;
        this.endingAt = endingAt;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public int getNumberOfIntegers() {
        return numberOfIntegers;
    }

    public int getStartingAt() {
        return startingAt;
    }

    public int getEndingAt() {
        return endingAt;
    }
}
