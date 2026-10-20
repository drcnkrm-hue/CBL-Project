import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class GameGUI extends JFrame {

    // The 6 rows and 5 columns of the Wordle board
    private JLabel[][] tiles = new JLabel[6][5];

    private JTextField input = new JTextField(10);

    private JLabel message = new JLabel("Enter a five-letter word");

    private JLabel timerLabel = new JLabel("Time: 00:00");

    private Color green = new Color(83, 141, 78);
    private Color yellow = new Color(201, 180, 88);
    private Color gray = new Color(120, 124, 126);
    private Color dark = new Color(18, 18, 19);

    private long startTime;

    private Timer timer;

    public GameGUI() {

        setTitle("WORDLE");
        setSize(420, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(dark);
        setLayout(new BorderLayout(10, 10));

        createTopPanel();
        createBoard();
        createBottomPanel();

        // Update the timer every tenth of a second
        timer = new Timer(100, e -> updateTimer());

        setVisible(true);
        input.requestFocusInWindow();
    }

    // Create the title, timer and home button
    private void createTopPanel() {

        JLabel title = new JLabel("WORDLE", SwingConstants.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        timerLabel.setForeground(Color.WHITE);
        timerLabel.setHorizontalAlignment(
                SwingConstants.CENTER);

        JButton homeButton = new JButton("HOME");

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(dark);

        top.add(title, BorderLayout.NORTH);
        top.add(timerLabel, BorderLayout.CENTER);
        top.add(homeButton, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);

        // The controller will tell the GUI what HOME should do
        homeButton.addActionListener(e -> {
            if (homeAction != null) {
                homeAction.run();
            }
        });
    }

    // Create the 6-by-5 board
    private void createBoard() {

        JPanel board = new JPanel(new GridLayout(6, 5, 5, 5));

        board.setBackground(dark);
        board.setBorder(
                new EmptyBorder(10, 35, 10, 35));

        for (int row = 0; row < 6; row++) {

            for (int col = 0; col < 5; col++) {

                JLabel tile = new JLabel("", SwingConstants.CENTER);

                tile.setOpaque(true);
                tile.setBackground(dark);
                tile.setForeground(Color.WHITE);

                tile.setFont(
                        new Font("Arial", Font.BOLD, 25));

                tile.setBorder(
                        BorderFactory.createLineBorder(
                                Color.GRAY, 2));

                tiles[row][col] = tile;

                board.add(tile);
            }
        }

        add(board, BorderLayout.CENTER);
    }

    // Create the input field, message and ENTER button
    private void createBottomPanel() {

        JPanel bottom = new JPanel();

        bottom.setBackground(dark);

        bottom.setLayout(
                new BoxLayout(bottom, BoxLayout.Y_AXIS));

        bottom.setBorder(
                new EmptyBorder(5, 20, 20, 20));

        message.setForeground(Color.WHITE);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        input.setFont(
                new Font("Arial", Font.BOLD, 18));

        input.setHorizontalAlignment(
                JTextField.CENTER);

        input.setMaximumSize(
                new Dimension(220, 40));

        input.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton enterButton = new JButton("ENTER");

        enterButton.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        bottom.add(message);
        bottom.add(Box.createVerticalStrut(10));
        bottom.add(input);
        bottom.add(Box.createVerticalStrut(10));
        bottom.add(enterButton);

        add(bottom, BorderLayout.SOUTH);

        enterButton.addActionListener(e -> runEnterAction());

        input.addActionListener(e -> runEnterAction());
    }

    // These actions are supplied by Game.java
    private Runnable enterAction;
    private Runnable homeAction;

    public void setEnterAction(Runnable action) {
        enterAction = action;
    }

    public void setHomeAction(Runnable action) {
        homeAction = action;
    }

    private void runEnterAction() {
        if (enterAction != null) {
            enterAction.run();
        }
    }

    // Return the word typed by the player
    public String getGuess() {
        return input.getText().trim().toLowerCase();
    }

    // Clear the input field
    public void clearInput() {
        input.setText("");
        input.requestFocusInWindow();
    }

    // Change the message displayed below the board
    public void setMessage(String text) {
        message.setText(text);
    }

    // Display a guess and its colors
    public void showGuess(
            int row, String guess, String[] feedback) {

        for (int col = 0; col < 5; col++) {

            tiles[row][col].setText(
                    String.valueOf(
                            guess.charAt(col)).toUpperCase());

            if (feedback[col].equals("GREEN")) {

                tiles[row][col].setBackground(green);

            } else if (feedback[col].equals("YELLOW")) {

                tiles[row][col].setBackground(yellow);

            } else {

                tiles[row][col].setBackground(gray);
            }

            tiles[row][col].setBorder(
                    BorderFactory.createLineBorder(
                            tiles[row][col].getBackground(), 2));
        }
    }

    // Start the timer
    public void startTimer() {

        startTime = System.currentTimeMillis();
        timer.start();
    }

    // Stop the timer
    public void stopTimer() {
        timer.stop();
    }

    // Get the total elapsed time in milliseconds
    public long getElapsedTime() {
        return System.currentTimeMillis() - startTime;
    }

    // Update the timer displayed on screen
    private void updateTimer() {

        long elapsed = getElapsedTime();

        long minutes = elapsed / 60000;
        long seconds = (elapsed / 1000) % 60;

        timerLabel.setText(
                String.format(
                        "Time: %02d:%02d",
                        minutes, seconds));
    }

    // Prevent further guesses after the game finishes
    public void disableInput() {
        input.setEnabled(false);
    }

    // Show the winning result
    public void showWin(long elapsed) {

        JOptionPane.showMessageDialog(
                this,
                "You won!\nTime: "
                        + LeaderBoard.formatTime(elapsed)
                        + "\nYour result has been saved!");
    }

    // Show the losing result
    public void showLoss(String secretWord) {

        JOptionPane.showMessageDialog(
                this,
                "Game over!\nThe word was "
                        + secretWord.toUpperCase()
                        + "\nThis result was not saved.");
    }
}