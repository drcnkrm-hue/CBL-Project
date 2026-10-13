import java.util.Scanner;

public class ExecuteWordle {

    public static void main(String[] args) {

        StatsTracker stats = new StatsTracker("stats");
        stats.statsDisplay();

        Dictionary dictionary = new Dictionary("/C://Users//drcnk//OneDrive//Masaüstü//New folder//valid-wordle-words.txt/");
        String targetWord = dictionary.getRandom();
        WGame game = new WGame(targetWord);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a 5 letter word.");

        while (!game.GameOver()) {

            String guess = scanner.nextLine().trim().toLowerCase();

            if (guess.length() != 5) {

                System.out.println("Not 5 letters!");
                
                continue;
            }
            if (!dictionary.isValidWord(guess)) {
                System.out.println("Not a valid word");
                
                continue;
            }

            String[] colors = game.evaluateGuess(guess);
            char[] guessChars = guess.toCharArray();

            for (int i = 0; i < 5; i++) {

                    if (colors[i].equals("Green")) {
                        System.out.print("\u001B[32m" + guessChars[i] + "\u001B[0m");

                    } else if (colors[i].equals("Yellow")) {
                        System.out.print("\u001B[33m" + guessChars[i] + "\u001B[0m");

                    } else if (colors[i].equals("Red")) {
                        System.out.print("\u001B[31m" + guessChars[i] + "\u001B[0m");
                    }
                }
            System.out.println();

        }

        if(game.isWon()) {
            System.out.println("You won, congrats!");
        } else {
            System.out.println("Game over! The word was: " + game.getActualWord());
        }


        stats.recordGameStats(game.isWon());
        stats.statsDisplay();

        scanner.close();


    }
}
