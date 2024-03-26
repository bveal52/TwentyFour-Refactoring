package twentyfour;

public class GameParameter {

    private int magicNumber = 0;
    private int numberOfIntegers;
    private int startingAt;
    private int endingAt;

    private boolean withAverage = false; // If true, include the average of the numbers in the solution
    private boolean noNegativeAnswers = true; // If magicNumber is positive, these are essentially duplicate solutions
    private boolean noEquivalentsExceptForOrder = true; // If the only difference is which is done first, weed these out

    public GameParameter(int magicNumber, int numberOfIntegers, int startingAt, int endingAt, boolean noNegativeAnswers, boolean noEquivalentsExceptForOrder, boolean withAverage) {
        this.magicNumber = magicNumber;
        this.numberOfIntegers = numberOfIntegers;
        this.startingAt = startingAt;
        this.endingAt = endingAt;
        this.noNegativeAnswers = noNegativeAnswers;
        this.noEquivalentsExceptForOrder = noEquivalentsExceptForOrder;
        this.withAverage = withAverage;
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

    public boolean getNoNegativeAnswers() {
        return noNegativeAnswers;
    }

    public boolean getNoEquivalentsExceptForOrder() {
        return noEquivalentsExceptForOrder;
    }

    public boolean getWithAverage() {
        return withAverage;
    }
}
