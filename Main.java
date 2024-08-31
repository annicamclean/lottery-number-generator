import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    /*
    TODO: strip the HTML file to content
    TODO: get only the numbers and none of the dates and find the frequency
    TODO: Create a class to hold the number frequency
    TODO: Create a class to find what numbers show up the most during a month
    TODO: Create a class to find what numbers show up the most together
        - so find the most common number, find its most common pair, then find the most common after that one with
            either number, and keep going until you have all 5 numbers
     */
    public static void main(String[] args) {
        File html = new File("lotterySite.txt");
        ArrayList<String> everything = new ArrayList<>();
        ArrayList<String> numbersAndDates = new ArrayList<>();
        ArrayList<Drawing> drawings = new ArrayList<>();
        ArrayList<Integer> numbersOnly = new ArrayList<>();
        ArrayList<Integer> numbersOnlyPowerBall = new ArrayList<>();
        Hashtable<Integer, Integer> numbers = new Hashtable<Integer, Integer>();
        Hashtable<Integer, Integer> powerBall = new Hashtable<Integer, Integer>();

        //getting everything from txt file
        try {
            Scanner htmlFile = new Scanner(html);

            while (htmlFile.hasNextLine()) {
                String line = htmlFile.nextLine();
                everything.add(line);
            }
        } catch (IOException var8) {
            var8.printStackTrace();
            System.out.println("This file does not exist");
        }

        for (int i = 0; i < everything.size(); i++) {
            String newString = removeThings(everything.get(i));
            everything.set(i, newString);
        }

        for (int i = 0; i < everything.size(); i++) {
            if (everything.get(i) != "") {
                //System.out.println(everything.get(i));
                numbersAndDates.add(everything.get(i));
            }
        }

        for (int i = 0; i < numbersAndDates.size(); i += 7) {
            Drawing drawing = new Drawing();
            drawing.setDate(numbersAndDates.get(i));
            int number = Integer.parseInt(numbersAndDates.get(i+1));
            numbersOnly.add(number);
            drawing.setNumberOne(number);
            number = Integer.parseInt(numbersAndDates.get(i+2));
            numbersOnly.add(number);
            drawing.setNumberTwo(number);
            number = Integer.parseInt(numbersAndDates.get(i+3));
            numbersOnly.add(number);
            drawing.setNumberThree(number);
            number = Integer.parseInt(numbersAndDates.get(i+4));
            numbersOnly.add(number);
            drawing.setNumberFour(number);
            number = Integer.parseInt(numbersAndDates.get(i+5));
            numbersOnly.add(number);
            drawing.setNumberFive(number);
            number = Integer.parseInt(numbersAndDates.get(i+6));
            numbersOnlyPowerBall.add(number);
            drawing.setPowerball(number);

            drawings.add(drawing);
        }


        /*for (Drawing draw: drawings) {
            System.out.printf("%s: %d, %d, %d, %d, %d, P: %d\n", draw.getDate(), draw.getNumberOne(), draw.getNumberTwo(),
                    draw.getNumberThree(), draw.getNumberFour(), draw.getNumberFive(), draw.getPowerball());
        }*/

        for (int i = 0; i < numbersOnly.size(); i++) {
            if (numbers.containsKey(numbersOnly.get(i))) {
                int newNumber = numbers.get(numbersOnly.get(i));
                numbers.replace(numbersOnly.get(i), newNumber + 1);
            } else {
                numbers.put(numbersOnly.get(i), 1);
            }
        }


        for (int i = 0; i < numbersOnlyPowerBall.size(); i++) {
            if (powerBall.containsKey(numbersOnlyPowerBall.get(i))) {
                int newNumber = powerBall.get(numbersOnlyPowerBall.get(i));
                powerBall.replace(numbersOnlyPowerBall.get(i), newNumber + 1);
            } else {
                powerBall.put(numbersOnlyPowerBall.get(i), 1);
            }
        }

        //System.out.println(numberTreeMap.entrySet());


        ArrayList<Ball> top5numbers = new ArrayList<>();

        //trying to find the top 5
        for (int i = 1; i <= 5; i++) {
            Ball newBall = new Ball();
            int frequency = numbers.get(i);
            newBall.setFrequency(frequency);
            newBall.setNumber(i);
            top5numbers.add(i-1, newBall);
        }

        quickSort(top5numbers, 0, top5numbers.size()-1);

        for (int i = 5; i <= numbers.size(); i++) {
            int frequency = numbers.get(i);
            if (frequency > top5numbers.get(0).getFrequency()) {
                Ball newBall = new Ball(i, frequency);
                top5numbers.set(0, newBall);
                quickSort(top5numbers, 0, top5numbers.size() - 1);
            }
        }

        System.out.println("THE MOST FREQUENTLY PLAYED NUMBERS: ");
        for (int i = 0; i < 5; i++) {
            System.out.print(top5numbers.get(i).getNumber() + " "/*+ " = " + top5numbers[i].getFrequency()*/);
        }

        Ball topPowerBall = new Ball(1, powerBall.get(1));

        for (int i = 2; i <= powerBall.size(); i++) {
            if (powerBall.get(i) > topPowerBall.getFrequency()) {
                topPowerBall = new Ball(i, powerBall.get(i));
            }
        }

        System.out.println("P/M:" + topPowerBall.getNumber() /*+ " = " + topPowerBall.getFrequency()*/);


        //an array might be better
        ArrayList<Month> months = new ArrayList<>();
        //want to travers through drawing
        for (Drawing draw: drawings) {
            String month = draw.getDate().substring(5,8);
            if (hasMonth(months, month)) {
                int monthIndex = findMonthIndex(month, months);
                Month currentMonth = months.get(monthIndex);
                //first number
                if (monthContainsNumber(currentMonth, draw.getNumberOne())) {
                    int ballIndex = findBallIndex(draw.getNumberOne(), currentMonth.getNumbers());
                    Ball ball = currentMonth.getNumbers().get(ballIndex);
                    ball.increaseFrequency();
                } else {
                    //switch the 2 statements and need to find the other ball that already exist
                    Ball newBall = new Ball(draw.getNumberOne(), 1);
                    currentMonth.getNumbers().add(newBall);
                }
                //second number
                if (monthContainsNumber(currentMonth, draw.getNumberTwo())) {
                    int ballIndex = findBallIndex(draw.getNumberTwo(), currentMonth.getNumbers());
                    Ball ball = currentMonth.getNumbers().get(ballIndex);
                    ball.increaseFrequency();
                } else {
                    //switch the 2 statements and need to find the other ball that already exist
                    Ball newBall = new Ball(draw.getNumberTwo(), 1);
                    currentMonth.getNumbers().add(newBall);
                }
                //third number
                if (monthContainsNumber(currentMonth, draw.getNumberThree())) {
                    int ballIndex = findBallIndex(draw.getNumberThree(), currentMonth.getNumbers());
                    Ball ball = currentMonth.getNumbers().get(ballIndex);
                    ball.increaseFrequency();
                } else {
                    //switch the 2 statements and need to find the other ball that already exist
                    Ball newBall = new Ball(draw.getNumberThree(), 1);
                    currentMonth.getNumbers().add(newBall);
                }
                //fourth number
                if (monthContainsNumber(currentMonth, draw.getNumberFour())) {
                    int ballIndex = findBallIndex(draw.getNumberFour(), currentMonth.getNumbers());
                    Ball ball = currentMonth.getNumbers().get(ballIndex);
                    ball.increaseFrequency();
                } else {
                    //switch the 2 statements and need to find the other ball that already exist
                    Ball newBall = new Ball(draw.getNumberFour(), 1);
                    currentMonth.getNumbers().add(newBall);
                }
                //fifth number
                if (monthContainsNumber(currentMonth, draw.getNumberFive())) {
                    int ballIndex = findBallIndex(draw.getNumberFive(), currentMonth.getNumbers());
                    Ball ball = currentMonth.getNumbers().get(ballIndex);
                    ball.increaseFrequency();
                } else {
                    //switch the 2 statements and need to find the other ball that already exist
                    Ball newBall = new Ball(draw.getNumberFive(), 1);
                    currentMonth.getNumbers().add(newBall);
                }
                //powerball number
                if (monthContainsNumber(currentMonth, draw.getPowerball())) {
                    int ballIndex = findBallIndex(draw.getPowerball(), currentMonth.getNumbers());
                    Ball ball = currentMonth.getNumbers().get(ballIndex);
                    ball.increaseFrequency();
                } else {
                    //switch the 2 statements and need to find the other ball that already exist
                    Ball newBall = new Ball(draw.getPowerball(), 1);
                    currentMonth.getNumbers().add(newBall);
                }

            } else {
                Month newMonth = new Month();
                newMonth.setName(month);
                //switch the 2 statements and need to find the other ball that already exist
                Ball newBall1 = new Ball(draw.getNumberOne(), 1);
                newMonth.getNumbers().add(newBall1);
                Ball newBall2 = new Ball(draw.getNumberTwo(), 1);
                newMonth.getNumbers().add(newBall2);
                Ball newBall3 = new Ball(draw.getNumberThree(), 1);
                newMonth.getNumbers().add(newBall3);
                Ball newBall4 = new Ball(draw.getNumberFour(), 1);
                newMonth.getNumbers().add(newBall4);
                Ball newBall5 = new Ball(draw.getNumberFive(), 1);
                newMonth.getNumbers().add(newBall5);
                Ball newBallP = new Ball(draw.getPowerball(), 1);
                newMonth.getPowerBallNumbers().add(newBallP);
                months.add(newMonth);
            }
        }

        Scanner input = new Scanner(System.in);
        System.out.println("Which month? (First 3 letters)");
        String monthAbbr = input.next();

        String numsForMonth = numsForMonth(months, monthAbbr);

        System.out.println(numsForMonth);

        /*for (Month month: months) {
            System.out.println(month.getName() + "\n White Balls:");

            for (Ball ball:month.getNumbers()) {
                System.out.print(ball.getNumber() + " = " + ball.getFrequency());
            }

            System.out.println("Power Ball:");

            for (Ball ball: month.getPowerBallNumbers()) {
                System.out.print(ball.getNumber() + " = " + ball.getFrequency());
            }
            System.out.println();
        }*/
    }

    public static boolean monthContainsNumber (Month currentMonth, int number) {
        for (int i = 0; i < currentMonth.getNumbers().size(); i++) {
            if (currentMonth.getNumbers().get(i).getNumber() == number) {
                return true;
            }
        }
        return false;
    }

    public static String numsForMonth(ArrayList<Month> months, String monthAbbr) {
        int monthNum = 0;
        for (int i = 0; i < months.size(); i++) {
            if (months.get(i).getName().equalsIgnoreCase(monthAbbr)) {
                monthNum = i;
            }
        }

        //find the numbers based on the month like you did up above
        quickSort(months.get(monthNum).getNumbers(),0, months.get(monthNum).getNumbers().size() - 1);

        System.out.println("THE TOP 5 NUMBERS FOR " + monthAbbr.toUpperCase());
        String numbers = "";
        for (int i = months.get(monthNum).getNumbers().size() - 1; i >= months.get(monthNum).getNumbers().size() - 5; i--) {
            numbers += months.get(monthNum).getNumbers().get(i).getNumber() + " ";
        }

        quickSort(months.get(monthNum).getPowerBallNumbers() ,0, months.get(monthNum).getPowerBallNumbers().size() - 1);

        numbers += "P/M: " + months.get(monthNum).getPowerBallNumbers().get(months.get(monthNum).getPowerBallNumbers().size() - 1).getNumber();

        return numbers;
    }

    public static boolean hasMonth(ArrayList<Month> months, String month) {
        for (int i = 0; i < months.size(); i++) {
            if (months.get(i).getName().equals(month)) {
                return true;
            }
        }
        return false;
    }

    public static void addNumbers() {

    }

    //QUICK SORTING ALGO FROM GEEKSFORGEEKS
    public static void swap(ArrayList<Ball> arr, int i, int j)
    {
        Ball temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    // This function takes last element as pivot,
    // places the pivot element at its correct position
    // in sorted array, and places all smaller to left
    // of pivot and all greater elements to right of pivot
    public static int partition(ArrayList<Ball> arr, int low, int high)
    {
        // Choosing the pivot
        int pivot = arr.get(high).getFrequency();

        // Index of smaller element and indicates
        // the right position of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // If current element is smaller than the pivot
            if (arr.get(j).getFrequency() < pivot) {

                // Increment index of smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    // The main function that implements QuickSort
    // arr[] --> Array to be sorted,
    // low --> Starting index,
    // high --> Ending index
    public static void quickSort(ArrayList<Ball> arr, int low, int high)
    {
        if (low < high) {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(arr, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    //END OF QUICKSORT ALGORITHM


    public static String removeThings(String str) {
        String newString = "";
        if (str.contains(",")) {
            newString = str.substring(removeTabs(str));
            newString = removeTags(newString);
        } else if(str.contains("<b>") && !str.contains("btn-success")) {
            newString = str.substring(removeTabs(str));
            newString = removeTags(newString);
        } else {
            newString = "";
        }
        return newString;
    }


    public static int removeTabs(String str) {
        int spaceIndex = 0;
        boolean foundNonSpace = false;

        for (int i = 0; i < str.length() && !foundNonSpace; i++) {
            if (str.charAt(i) != '\t') {
                spaceIndex = i;
                foundNonSpace = true;
            }
        }
        return spaceIndex;
    }

    public static String removeTags(String str) {
        String removeTagString = "";
        if (str.contains("<button")) {
            removeTagString = str.substring(59);
            removeTagString = removeTagString.substring(0, removeTagString.length() - 18);
        } else if (str.contains("class")) {
            removeTagString = str.substring(22);
            removeTagString = removeTagString.substring(0, removeTagString.length() - 7);
        } else {
            removeTagString = str.substring(7);
            removeTagString = removeTagString.substring(0, removeTagString.length() - 9);
        }
        return removeTagString;
    }

    public static int findMonthIndex(String month, ArrayList<Month> months) {
        for (int i = 0; i < months.size(); i++) {
            if (months.get(i).getName().equals(month)) {
                return i;
            }
        }
        return 0;
    }

    public static int findBallIndex(int ball, ArrayList<Ball> balls) {
        for (int i = 0; i < balls.size(); i++) {
            if (balls.get(i).getNumber() == ball) {
                return i;
            }
        }
        return 0;
    }
}
