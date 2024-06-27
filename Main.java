import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /*
    TODO: strip the HTML file to content
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
            drawing.setNumberOne(number);
            number = Integer.parseInt(numbersAndDates.get(i+2));
            drawing.setNumberTwo(number);
            number = Integer.parseInt(numbersAndDates.get(i+3));
            drawing.setNumberThree(number);
            number = Integer.parseInt(numbersAndDates.get(i+4));
            drawing.setNumberFour(number);
            number = Integer.parseInt(numbersAndDates.get(i+5));
            drawing.setNumberFive(number);
            number = Integer.parseInt(numbersAndDates.get(i+6));
            drawing.setPowerball(number);

            drawings.add(drawing);
        }

        for (Drawing draw: drawings) {
            System.out.printf("%s: %d, %d, %d, %d, %d, P: %d\n", draw.getDate(), draw.getNumberOne(), draw.getNumberTwo(),
                    draw.getNumberThree(), draw.getNumberFour(), draw.getNumberFive(), draw.getPowerball());
        }

    }

    public static String removeThings(String str) {
        String newString = "";
        if (str.contains(",")) {
            newString = str.substring(removeTabs(str));
            newString = removeTags(newString);
        } else if(str.contains("<b>")) {
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
}
