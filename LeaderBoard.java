
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LeaderBoard extends JFrame {

    public LeaderBoard() {

        // Set up the window
        setTitle("Leaderboard");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(18, 18, 19));
        panel.setLayout(new BorderLayout());

        // Create the title
        JLabel title = new JLabel(
                "LEADERBOARD", SwingConstants.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        panel.add(title, BorderLayout.NORTH);

        // Create the area that displays the results
        JTextArea results = new JTextArea();

        results.setEditable(false);
        results.setFont(
                new Font("Monospaced", Font.PLAIN, 16));
        results.setBackground(new Color(30, 30, 30));
        results.setForeground(Color.WHITE);

        // Display the column names
        results.setText("WORD          TIME\n");
        results.append("------------------------\n");

        // Read the saved results
        try {

            Scanner scanner = new Scanner(
                    new File("wordle-results.txt"));

            while (scanner.hasNextLine()) {

                // Read one line from the file
                String line = scanner.nextLine();

                // Separate the time and word
                String[] parts = line.split("\\|");

                if (parts.length == 2) {

                    long time = Long.parseLong(parts[0]);
                    String word = parts[1];

                    // Display the word and formatted time
                    results.append(
                            word + "          "
                                    + formatTime(time) + "\n");
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {

            results.append("No results found yet.\n");
        }

        // Allow scrolling through the results
        JScrollPane scrollPane = new JScrollPane(results);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Create the HOME button
        JButton homeButton = new JButton("HOME");

        homeButton.addActionListener(e -> {
            new Main();
            dispose();
        });

        panel.add(homeButton, BorderLayout.SOUTH);

        // Display the window
        add(panel);
        setVisible(true);
    }

    // Save a winning result to the file
    public static void add(String word, long time) {

        try {

            // Open the file in append mode
            PrintWriter writer = new PrintWriter(
                    new FileWriter("wordle-results.txt", true));

            // Save time first, then the word
            writer.println(
                    time + "|" + word.toUpperCase());

            writer.close();

        } catch (IOException e) {

            System.out.println("Could not save result.");
        }
    }

    // Convert milliseconds into minutes and seconds
    public static String formatTime(long milliseconds) {

        long totalSeconds = milliseconds / 1000;

        long minutes = totalSeconds / 60;

        long seconds = totalSeconds % 60;

        return String.format(
                "%02d:%02d", minutes, seconds);
    }
}
