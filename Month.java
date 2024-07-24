import java.util.ArrayList;

public class Month {
    private String name = "";

    private ArrayList<Ball> numbers = new ArrayList<>();

    private ArrayList<Ball> powerBallNumbers = new ArrayList<>();

    public Month(String name, ArrayList<Ball> numbers, ArrayList<Ball> powerBallNumbers) {
        this.name = name;
        this.numbers = numbers;
        this.powerBallNumbers = powerBallNumbers;
    }

    public Month() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ball> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<Ball> numbers) {
        this.numbers = numbers;
    }

    public ArrayList<Ball> getPowerBallNumbers() {
        return powerBallNumbers;
    }

    public void setPowerBallNumbers(ArrayList<Ball> powerBallNumbers) {
        this.powerBallNumbers = powerBallNumbers;
    }
}
