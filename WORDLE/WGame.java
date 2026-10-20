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

public class WGame {

    private String actualWord;
    private int attempts;
    private boolean won;

    public WGame(String actualWord) {
        this.actualWord = actualWord;
        this.attempts = 0;
        this.won = false;
    }

    public String[] evaluateGuess(String TheWord) {
        this.attempts++;

        if (TheWord.equals(actualWord)) {
            this.won = true;
        }

        boolean[] usedInActual = new boolean[5];

        char[] LetterList = TheWord.toCharArray();

        char[] ActualWordList = actualWord.toCharArray();

        String[] ColorCode = new String[5];

        for (int i = 0; i < 5; i++) {
            if (LetterList[i] == ActualWordList[i]) {
                ColorCode[i] = "Green";
                usedInActual[i] = true;
            }

        }

        for (int i = 0; i < 5; i++) {
            if ("Green".equals(ColorCode[i]))
                continue;

            for (int a = 0; a < 5; a++) {
                if (!usedInActual[a] && LetterList[i] == ActualWordList[a]) {
                    ColorCode[i] = "Yellow";
                    usedInActual[a] = true;
                    break;

                }
            }
            if (ColorCode[i] == null) {
                ColorCode[i] = "Red";
            }
        }

        return ColorCode;
    }

    public boolean GameOver() {
        return this.won || this.attempts >=6;
    }

    public boolean isWon() {
        return this.won;
    }

    public String getActualWord() {
        return this.actualWord;
    }

}
