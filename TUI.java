import java.util.Scanner;

public class TUI {
    public static void main(String[] args) {

        char[] LetterList = new char[5];

        char[] ActualWordList = new char[5];

        String[] ColorCode = new String[5];

        String ActualWord = "hello";

        for (int i = 0; i < 5; i++) {

            ActualWordList[i] = ActualWord.charAt(i);

        }

        System.out.println("Enter a five letter word");

        Scanner scanner = new Scanner(System.in);

        WordChecker checker = new WordChecker();

        String TheWord = scanner.nextLine();

        if (TheWord.length() != 5) {

            System.out.println("Not 5 letters!");

        } else if (checker.exists(TheWord)) {

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