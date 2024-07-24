public class Ball {
    private int number = 0;
    private int frequency = 0;

    public Ball(int number, int frequency) {
        this.number = number;
        this.frequency = frequency;
    }

    public Ball() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void increaseFrequency() {
        this.frequency++;
    }
}
