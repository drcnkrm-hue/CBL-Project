
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame {

    public Main() {

        setTitle("WORDLE");
        setSize(420, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color dark = new Color(18, 18, 19);
        Color green = new Color(83, 141, 78);

        // Main panel
        JPanel panel = new JPanel();
        panel.setBackground(dark);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        // Title
        JLabel title = new JLabel("WORDLE");
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("Choose what you want to do");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton newGameButton = new JButton("NEW GAME");
        JButton leaderboardButton = new JButton("LEADERBOARD");

        styleButton(newGameButton, green);
        styleButton(leaderboardButton, new Color(45, 45, 46));

        // Open game
        newGameButton.addActionListener(e -> {
            try {
                new Game();
                dispose();
            } catch (java.io.FileNotFoundException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Could not find valid-wordle-words.txt");
            }
        });

        // Open leaderboard
        leaderboardButton.addActionListener(e -> {
            new LeaderBoard();
            dispose();
        });

        // Add everything to the panel
        panel.add(title);
        panel.add(Box.createVerticalStrut(15));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(55));

        panel.add(newGameButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(leaderboardButton);
        panel.add(Box.createVerticalStrut(20));

        setContentPane(panel);
        setVisible(true);
    }

    // Style the buttons
    private void styleButton(JButton button, Color background) {

        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(background);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(300, 55));
        button.setMaximumSize(new Dimension(300, 55));
        button.setMinimumSize(new Dimension(300, 55));

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(true);
        button.setBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1));
    }

    // Start the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
