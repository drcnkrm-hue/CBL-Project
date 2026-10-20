
import java.io.FileNotFoundException;

public class Game {

    private String secretWord;
    private int guesses = 0;
    private boolean finished = false;

    private WordList wordList;
    private WordleLogic logic;
    private GameGUI gui;

    public Game() throws FileNotFoundException {

        wordList = new WordList();
        logic = new WordleLogic();

        secretWord = wordList.chooseWord();

        gui = new GameGUI();

        gui.setEnterAction(() -> checkGuess());
        gui.setHomeAction(() -> goHome());

        gui.startTimer();
    }

    private void checkGuess() {

        // Do nothing if the game has finished
        if (finished) {
            return;
        }

        String guess = gui.getGuess();

        // Check that the guess has five letters
        if (guess.length() != 5) {
            gui.setMessage("Enter exactly five letters!");
            return;
        }

        // Check that the guess contains only English letters
        if (!guess.matches("[a-z]{5}")) {
            gui.setMessage("Use English letters only!");
            return;
        }

        // Check that the word exists in the dictionary
        if (!wordList.isValidWord(guess)) {
            gui.setMessage("Not a valid word!");
            return;
        }

        // Display the colors for this guess
        String[] feedback = logic.getFeedback(secretWord, guess);
        gui.showGuess(guesses, guess, feedback);

        // Count this valid guess
        guesses++;

        // Check if the player won
        if (guess.equals(secretWord)) {
            finishGame(true);
            return;
        }

        // Check if the player used all six guesses
        if (guesses >= 6) {
            finishGame(false);
            return;
        }

        // Tell the player how many guesses remain
        gui.setMessage((6 - guesses) + " guesses remaining");

        // Clear the input field
        gui.clearInput();
    }

    private void finishGame(boolean won) {

        // Mark the game as finished
        finished = true;

        // Stop the timer and disable further input
        gui.stopTimer();
        gui.disableInput();

        if (won) {

            long elapsed = gui.getElapsedTime();

            // Save only winning results
            LeaderBoard.add(secretWord, elapsed);

            gui.showWin(elapsed);

        } else {

            // Show the correct word after losing
            gui.showLoss(secretWord);
        }
    }

    private void goHome() {

        gui.stopTimer();
        gui.dispose();

        new Main();
    }
}
