import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Scanner;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;


public class Dictionary {
    private ArrayList<String> wordList;

    public Dictionary(String filepath) {
        wordList = new ArrayList<>();
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                wordList.add(scanner.nextLine().toLowerCase().trim());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.print("Could not execute, dictionary file not found.");

        }
            
        }
        public String getRandom() {
            if (wordList.isEmpty()) {
                return "error";
            }
            Random random = new Random();
            int idx = random.nextInt(wordList.size());
            return wordList.get(idx);

        }

        public boolean isValidWord(String word) {
            return wordList.contains(word);
        }
    }

