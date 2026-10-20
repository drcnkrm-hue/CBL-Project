import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordList {

    private ArrayList<String> validWords = new ArrayList<>();

    // Load all valid words from the text file
    public WordList() throws FileNotFoundException {

        File file = new File("valid-wordle-words.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {

            String word = scanner.nextLine()
                    .trim()
                    .toLowerCase();

            if (word.matches("[a-z]{5}")) {
                validWords.add(word);
            }
        }

        scanner.close();

        if (validWords.isEmpty()) {
            throw new IllegalStateException(
                    "No valid words found!");
        }
    }

    // Check whether a word is in the dictionary
    public boolean isValidWord(String word) {
        return validWords.contains(word);
    }

    // Choose a random word from the dictionary
    public String chooseWord() {

        Random random = new Random();

        int index = random.nextInt(validWords.size());

        return validWords.get(index);
    }
}