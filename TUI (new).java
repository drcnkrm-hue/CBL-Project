import java.util.Scanner;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;

public class TUI {
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("valid-wordle-words.txt");

        Boolean IsAWord = false;

        Boolean WonOrNot = false;

        Scanner fileScanner = new Scanner(file);

        char[] LetterList = new char[5];

        char[] ActualWordList = new char[5];

        String[] ColorCode = new String[5];

        Random random = new Random();

        int WordNumber = random.nextInt(14856);

        for (int i = 0; i < WordNumber; i++) {
            fileScanner.nextLine();
        }

        String ActualWord = fileScanner.nextLine().toLowerCase();

        System.out.println(ActualWord);

        for (int i = 0; i < 5; i++) {

            ActualWordList[i] = ActualWord.charAt(i);

        }

        Scanner scanner = new Scanner(System.in);

        int attempts = 0;

        while (attempts < 6 && !WonOrNot) {

            System.out.println("Enter a five letter word");
            
             String TheWord = scanner.nextLine().toLowerCase();

            while (fileScanner.hasNextLine()) {

                String WordCheck = fileScanner.nextLine();

                if (TheWord.equals(WordCheck)) {

                    IsAWord = true;
                    break;
                }
            }

            if (TheWord.length() != 5) {

                System.out.println("Not 5 letters!");

            } else if (IsAWord) {

                for (int i = 0; i < 5; i++) {

                    LetterList[i] = TheWord.charAt(i);

                }

                for (int a = 0; a < 5; a++) {

                    if (LetterList[a] == ActualWordList[a]) {

                        ColorCode[a] = "Green";

                    } else {

                        boolean found = false;

                        for (int b = 0; b < 5; b++) {

                            if (a != b && LetterList[a] == ActualWordList[b]) {
                                found = true;
                            }
                        }

                        if (found) {
                            ColorCode[a] = "Yellow";
                        } else {
                            ColorCode[a] = "Red";
                        }
                    }
                }

                for (int i = 0; i < 5; i++) {

                    if (ColorCode[i].equals("Green")) {
                        System.out.print("\u001B[32m" + LetterList[i] + "\u001B[0m");

                    } else if (ColorCode[i].equals("Yellow")) {
                        System.out.print("\u001B[33m" + LetterList[i] + "\u001B[0m");

                    } else if (ColorCode[i].equals("Red")) {
                        System.out.print("\u001B[31m" + LetterList[i] + "\u001B[0m");
                    }
                }

                System.out.println();

            } else {

                System.out.println("Not a valid word!");

            }
        }

    }
}