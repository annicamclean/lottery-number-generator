public class Drawing {
    private String date = "";
    private int numberOne = 0;
    private int numberTwo = 0;
    private int numberThree = 0;
    private int numberFour = 0;
    private int numberFive = 0;
    private int powerball = 0;


    public Drawing(String date, int numberOne, int numberTwo, int numberThree, int numberFour, int numberFive, int powerball) {
        this.date = date;
        this.numberOne = numberOne;
        this.numberTwo = numberTwo;
        this.numberThree = numberThree;
        this.numberFour = numberFour;
        this.numberFive = numberFive;
        this.powerball = powerball;
    }

    public Drawing() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOne() {
        return numberOne;
    }

    public void setNumberOne(int numberOne) {
        this.numberOne = numberOne;
    }

    public int getNumberTwo() {
        return numberTwo;
    }

    public void setNumberTwo(int numberTwo) {
        this.numberTwo = numberTwo;
    }

    public int getNumberThree() {
        return numberThree;
    }

    public void setNumberThree(int numberThree) {
        this.numberThree = numberThree;
    }

    public int getNumberFour() {
        return numberFour;
    }

    public void setNumberFour(int numberFour) {
        this.numberFour = numberFour;
    }

    public int getNumberFive() {
        return numberFive;
    }

    public void setNumberFive(int numberFive) {
        this.numberFive = numberFive;
    }

    public int getPowerball() {
        return powerball;
    }

    public void setPowerball(int powerball) {
        this.powerball = powerball;
    }
}
