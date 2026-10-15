
import java.awt.*; // Imports GUI tools such as Color, Font, BorderLayout, and GridLayout.
import java.io.File; // Allows Java to represent the word-list file on your computer.
import java.io.FileNotFoundException; // Represents the error that occurs when the word-list file cannot be found.
import java.util.ArrayList; // Provides a resizable list that we can use to select a random word.
import java.util.HashSet; // Provides a collection that stores unique words and supports fast membership checks.
import java.util.Random; // Allows us to select a random secret word.
import java.util.Scanner; // Allows us to read the words from the text file.
import java.util.Set; // Defines the general interface used by our collection of valid words.
import javax.swing.*; // Imports Swing components such as JFrame, JPanel, JLabel, JButton, and JTextField.
import javax.swing.border.EmptyBorder; // Allows us to create empty space around components.

// GUI extends JFrame, meaning that a GUI object is also a graphical application window.
public class GUI extends JFrame {

    // Stores whether the player has already guessed the correct word.
    // It starts as false because the player has not made any guesses yet.
    private boolean WonOrNot = false;

    // Stores the secret five-letter word selected at the beginning of the game.
    private String ActualWord;

    // Stores the five characters entered by the player for the current guess.
    private char[] LetterList = new char[5];

    // Stores the five characters belonging to the secret word.
    // This allows us to compare each guessed letter with the corresponding answer
    // letter.
    private char[] ActualWordList = new char[5];

    // Stores the result for each guessed letter: "Green", "Yellow", or "Red".
    // In this GUI, "Red" represents an incorrect letter and will be displayed in
    // gray.
    private String[] ColorCode = new String[5];

    // Stores references to all 30 graphical tiles: six rows and five columns.
    // We keep these references so we can change their letters and colors later.
    private JLabel[][] tiles = new JLabel[6][5];

    // Creates the text field where the player types their guess.
    private JTextField input = new JTextField(10);

    // Displays messages such as "Not a valid word!" or "You win!".
    private JLabel message = new JLabel(
            "Enter a five-letter word", SwingConstants.CENTER);

    // Tracks which row should receive the next valid guess.
    // Row 0 is the first guess, row 1 is the second, and so on.
    private int currentRow = 0;

    // Stores every valid five-letter word loaded from the text file.
    // A Set avoids storing the same word more than once.
    private Set<String> validWords = new HashSet<>();

    // Defines the background color used for letters in the correct position.
    private final Color GREEN = new Color(83, 141, 78);

    // Defines the background color used for letters that occur elsewhere in the
    // answer.
    private final Color YELLOW = new Color(201, 180, 88);

    // Defines the background color used for letters that are not present in the
    // answer.
    private final Color GRAY = new Color(120, 124, 126);

    // Defines the dark background color used throughout the application.
    private final Color DARK = new Color(18, 18, 19);

    // This constructor runs when we create a new GUI object using new GUI().
    // It builds the game window and prepares the game before the player starts
    // guessing.
    public GUI() throws FileNotFoundException {

        // Reads the word-list file and saves its valid words in the validWords Set.
        loadWords();

        // Selects one of the valid words and fills ActualWordList with its letters.
        chooseWord();

        // Sets the text displayed in the window's title bar.
        setTitle("Wordle");

        // Makes the application close when the player closes the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sets the initial width and height of the application window in pixels.
        setSize(420, 620);

        // Places the window in the center of the screen.
        setLocationRelativeTo(null);

        // Changes the main window's background color to our dark color.
        getContentPane().setBackground(DARK);

        // Uses BorderLayout to divide the window into North, Center, and South areas.
        // We will place the title at the top, the grid in the middle, and input
        // controls at the bottom.
        setLayout(new BorderLayout(10, 10));

        // Creates a label for the title and horizontally centers its text.
        JLabel title = new JLabel("WORDLE", SwingConstants.CENTER);

        // Makes the title text white so it is visible against the dark background.
        title.setForeground(Color.WHITE);

        // Makes the title bold and 30 points in size.
        title.setFont(new Font("Arial", Font.BOLD, 30));

        // Adds empty space around the title so it does not touch the window edges.
        title.setBorder(new EmptyBorder(15, 0, 5, 0));

        // Places the title at the top of the main window.
        add(title, BorderLayout.NORTH);

        // Creates a panel with six rows and five columns.
        // The final two numbers specify five-pixel horizontal and vertical gaps between
        // tiles.
        JPanel grid = new JPanel(new GridLayout(6, 5, 5, 5));

        // Gives the grid panel the same dark background as the window.
        grid.setBackground(DARK);

        // Adds space around the grid so the tiles do not touch the panel edges.
        grid.setBorder(new EmptyBorder(10, 35, 10, 35));

        // Loops through all six rows of the Wordle grid.
        for (int row = 0; row < 6; row++) {

            // For each row, loops through all five columns.
            for (int col = 0; col < 5; col++) {

                // Creates an empty JLabel to represent one letter tile.
                // SwingConstants.CENTER horizontally and vertically centers its text.
                JLabel tile = new JLabel("", SwingConstants.CENTER);

                // Makes the label's background visible when we assign a tile color.
                tile.setOpaque(true);

                // Gives the empty tile its initial dark background.
                tile.setBackground(DARK);

                // Makes the letter inside the tile white.
                tile.setForeground(Color.WHITE);

                // Sets the letters to bold Arial with a size of 25 points.
                tile.setFont(new Font("Arial", Font.BOLD, 25));

                // Draws a gray border around the tile with a thickness of two pixels.
                tile.setBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 2));

                // Saves this tile at its row and column in the two-dimensional array.
                // Without this reference, it would be difficult to update this tile after a
                // guess.
                tiles[row][col] = tile;

                // Adds the tile to the grid panel, where GridLayout positions it automatically.
                grid.add(tile);
            }
        }

        // Places the complete 30-tile grid in the center of the main window.
        add(grid, BorderLayout.CENTER);

        // Creates a panel to contain the status message, input field, and Enter button.
        JPanel bottom = new JPanel();

        // Uses the same dark background for the bottom controls.
        bottom.setBackground(DARK);

        // Arranges the bottom controls vertically, one component beneath another.
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        // Adds empty space around the controls to make the interface less cramped.
        bottom.setBorder(new EmptyBorder(5, 20, 20, 20));

        // Makes the status message white.
        message.setForeground(Color.WHITE);

        // Horizontally centers the status message within its label.
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Limits the input field's maximum width and sets its height to 40 pixels.
        input.setMaximumSize(new Dimension(220, 40));

        // Makes the text typed into the input field bold and easy to read.
        input.setFont(new Font("Arial", Font.BOLD, 18));

        // Centers the guessed word inside the input field.
        input.setHorizontalAlignment(JTextField.CENTER);

        // Centers the input field horizontally inside the vertical controls panel.
        input.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creates the button the player can click to submit a guess.
        JButton enterButton = new JButton("ENTER");

        // Centers the Enter button inside the controls panel.
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adds the status message to the bottom panel.
        bottom.add(message);

        // Adds ten pixels of vertical space beneath the status message.
        bottom.add(Box.createVerticalStrut(10));

        // Adds the text field where the player enters a word.
        bottom.add(input);

        // Adds ten pixels of vertical space between the input field and button.
        bottom.add(Box.createVerticalStrut(10));

        // Adds the Enter button beneath the input field.
        bottom.add(enterButton);

        // Places the entire controls panel at the bottom of the window.
        add(bottom, BorderLayout.SOUTH);

        // Registers an event listener: clicking Enter calls submitGuess().
        // The method runs when the click happens, not while the constructor is being
        // executed.
        enterButton.addActionListener(e -> submitGuess());

        // Also calls submitGuess() when the player presses Enter inside the text field.
        input.addActionListener(e -> submitGuess());

        // Makes the completed window visible to the player.
        setVisible(true);

        // Requests keyboard focus for the input field so the player can start typing.
        input.requestFocusInWindow();
    }

    // Loads the word list once when the game starts.
    // This replaces repeatedly reopening and scanning the file for every guess in
    // your TUI.
    private void loadWords() throws FileNotFoundException {

        // Looks for valid-wordle-words.txt in the program's current working directory.
        File file = new File("valid-wordle-words.txt");

        // Creates a Scanner that reads the file one line at a time.
        Scanner fileScanner = new Scanner(file);

        // Continues until there are no more lines in the file.
        while (fileScanner.hasNextLine()) {

            // Reads the next line, removes surrounding whitespace, and converts it to
            // lowercase.
            String word = fileScanner.nextLine().trim().toLowerCase();

            // Accepts only words consisting of exactly five English letters.
            if (word.matches("[a-z]{5}")) {

                // Saves the word so we can quickly check whether a guess is valid.
                validWords.add(word);
            }
        }

        // Closes the file Scanner because we have finished reading the file.
        fileScanner.close();

        // Stops the game from continuing if the file contained no valid five-letter
        // words.
        if (validWords.isEmpty()) {
            throw new IllegalStateException(
                    "The word list contains no five-letter words.");
        }
    }

    // Selects a random answer from the valid words loaded from the file.
    private void chooseWord() {

        // Converts the Set into an ArrayList so we can select a word using an index.
        ArrayList<String> words = new ArrayList<>(validWords);

        // Creates the random-number generator used to choose the answer.
        Random random = new Random();

        // Produces a random index from zero up to, but not including, words.size().
        int WordNumber = random.nextInt(words.size());

        // Retrieves the randomly selected word and stores it as the secret answer.
        ActualWord = words.get(WordNumber);

        // Copies each character from the secret word into the answer character array.
        for (int i = 0; i < 5; i++) {

            // charAt(i) returns the character at position i in the String.
            ActualWordList[i] = ActualWord.charAt(i);
        }

        // For debugging, you can temporarily uncomment the following line.
        // Do not leave it enabled when you want to play without seeing the answer.
        // System.out.println("Actual word: " + ActualWord);
    }

    // Processes a single guess whenever the player clicks Enter or presses Enter on
    // the keyboard.
    // This method replaces the repeated input-and-checking portion of your original
    // TUI loop.
    private void submitGuess() {

        // Prevents additional guesses after the player has won or used all six rows.
        if (WonOrNot || currentRow >= 6) {
            return; // Ends this method immediately without processing another guess.
        }

        // Retrieves the player's input, removes surrounding spaces, and converts it to
        // lowercase.
        String TheWord = input.getText().trim().toLowerCase();

        // Checks that the guess contains exactly five characters.
        if (TheWord.length() != 5) {

            // Displays an error message in the GUI instead of printing it in the terminal.
            message.setText("Not 5 letters!");

            // Ends the method, leaving currentRow unchanged so the player does not lose a
            // turn.
            return;
        }

        // Rejects digits, spaces inside the word, punctuation, and non-English letters.
        if (!TheWord.matches("[a-z]{5}")) {

            // Tells the player that the guess must contain English letters only.
            message.setText("Use English letters only!");

            // Ends the method without consuming a guess.
            return;
        }

        // Checks whether the guessed word exists in the Set loaded from the text file.
        // contains() returns true if the Set contains TheWord, and false otherwise.
        boolean IsAWord = validWords.contains(TheWord);

        // Runs only when the word is not in the valid-word list.
        if (!IsAWord) {

            // Displays an error message in the GUI.
            message.setText("Not a valid word!");

            // Ends the method without coloring a row or consuming a guess.
            return;
        }

        // Copies the five guessed characters into your original LetterList array.
        for (int i = 0; i < 5; i++) {

            // Stores the character at position i of the guessed String.
            LetterList[i] = TheWord.charAt(i);
        }

        // Records which positions in the answer have already been matched.
        // This prevents one answer letter from being counted more than once.
        boolean[] usedAnswerLetters = new boolean[5];

        // First pass: identify letters that match the answer at the exact same
        // position.
        for (int a = 0; a < 5; a++) {

            // Compares the guessed letter at position a with the answer letter at position
            // a.
            if (LetterList[a] == ActualWordList[a]) {

                // Records that this letter is correct and in the correct position.
                ColorCode[a] = "Green";

                // Marks the answer position as used so another letter cannot claim it.
                usedAnswerLetters[a] = true;

            } else {

                // Initially marks unmatched letters as absent.
                // A later pass may change this result to Yellow if a matching letter exists
                // elsewhere.
                ColorCode[a] = "Red";
            }
        }

        // Second pass: check whether unmatched guessed letters occur elsewhere in the
        // answer.
        for (int a = 0; a < 5; a++) {

            // Green letters have already been processed, so skip them.
            if (ColorCode[a].equals("Green")) {
                continue; // Moves to the next guessed letter.
            }

            // Searches all five answer positions for the current guessed letter.
            for (int b = 0; b < 5; b++) {

                // The answer position must be unused, and its character must match the guess.
                if (!usedAnswerLetters[b]
                        && LetterList[a] == ActualWordList[b]) {

                    // Changes the result to Yellow because the letter exists in another position.
                    ColorCode[a] = "Yellow";

                    // Marks the matched answer position as used to handle duplicate letters
                    // correctly.
                    usedAnswerLetters[b] = true;

                    // Stops searching because this guessed letter has already found its match.
                    break;
                }
            }
        }

        // Updates the five graphical tiles in the current row with the guessed letters.
        for (int i = 0; i < 5; i++) {

            // Converts the current guessed character to a String and displays it in
            // uppercase.
            tiles[currentRow][i].setText(
                    String.valueOf(LetterList[i]).toUpperCase());

            // If the letter is Green, set its background to the green color.
            if (ColorCode[i].equals("Green")) {

                tiles[currentRow][i].setBackground(GREEN);

                // Otherwise, if the letter is Yellow, set its background to yellow.
            } else if (ColorCode[i].equals("Yellow")) {

                tiles[currentRow][i].setBackground(YELLOW);

                // Any remaining letter is absent from the answer and is displayed in gray.
            } else {

                tiles[currentRow][i].setBackground(GRAY);
            }

            // Changes the border to match the tile's background color.
            tiles[currentRow][i].setBorder(
                    BorderFactory.createLineBorder(
                            tiles[currentRow][i].getBackground(), 2));
        }

        // Checks whether the complete guessed word equals the secret answer.
        // String.equals() compares the actual text, rather than comparing object
        // references.
        if (ActualWord.equals(TheWord)) {

            // Records that the player has won so no further guesses can be processed.
            WonOrNot = true;

            // Displays the victory message.
            message.setText("You win!");

            // Disables the input field because the game has finished.
            input.setEnabled(false);

            // Ends this method immediately, so the row counter is not increased
            // unnecessarily.
            return;
        }

        // The guess was valid but incorrect, so move to the next row.
        currentRow++;

        // Checks whether the player has used all six rows without winning.
        if (currentRow == 6) {

            // Displays the losing message and reveals the secret answer.
            message.setText(
                    "You lose! Word: " + ActualWord.toUpperCase());

            // Disables the input field because no guesses remain.
            input.setEnabled(false);

        } else {

            // Tells the player how many guesses remain after this incorrect guess.
            message.setText(
                    (6 - currentRow) + " guesses remaining");
        }

        // Clears the input field after processing a valid guess.
        input.setText("");

        // Returns keyboard focus to the input field for the next guess.
        input.requestFocusInWindow();
    }

    // This is the entry point used when Java launches the GUI class directly.
    public static void main(String[] args) {

        // Ensures that the Swing interface is created on Swing's event-dispatch thread.
        SwingUtilities.invokeLater(() -> {

            // Attempts to construct the GUI and start the game.
            try {

                // Creates the window, loads the words, and initializes the first game.
                new GUI();

                // Runs if the word-list file cannot be found or opened.
            } catch (FileNotFoundException e) {

                // Displays a graphical error dialog rather than a terminal error message.
                JOptionPane.showMessageDialog(
                        null,
                        "Could not find valid-wordle-words.txt.\n"
                                + "Put it in the project root folder.",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
