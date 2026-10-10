import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class StatsTracker {

    private int gamesPlayed;
    private int gamesWon;
    private final String filename;

    public StatsTracker(String filename) {
        this.filename = filename;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        loadstats();

    }

    private void loadstats() {
        File file = new File(filename);
        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                if (fileScanner.hasNextInt()) {
                    this.gamesPlayed = fileScanner.nextInt();
                }
                if (fileScanner.hasNextInt()) {
                    this.gamesWon = fileScanner.nextInt();
                }
            } catch (FileNotFoundException e) {
                System.out.print("File not found. Error code 404");
            }
        }
    }

    public void recordGameStats(boolean won) {
        this.gamesPlayed++;
        if (won){
            this.gamesWon++;
        }
        saveStats();

    }

    public void saveStats() {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.print(this.gamesPlayed);
            writer.print(this.gamesWon);
        } catch (FileNotFoundException e) {
            System.out.print("Could not save stats to file.");
        }
    }

    public void statsDisplay() {
        System.out.println(" PLAYER STATISTICS ");
        System.out.println();
        System.out.println("Games Played: " + this.gamesPlayed);
        System.out.println("Games Won: " + this.gamesWon);

        double winRate = (this.gamesPlayed > 0) ? ((double) this.gamesWon / this.gamesPlayed) * 100 : 0;
        System.out.printf("Win Rate:     %.1f%%\n", winRate);
    }

    public int getGamesPlayed()  {
         return gamesPlayed;
    }
    public int getGamesWon() {
        return gamesWon;
    }
    
    
}
