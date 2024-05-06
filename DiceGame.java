import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DiceGame {
    public static void main(String[] args) throws IOException {
        File file = new File("DiceGameOutput.txt");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the number of sides for the die being used: ");
            int numSides = sc.nextInt();
            System.out.print("Enter the number of players: ");
            int numPlayers = sc.nextInt();
            sc.nextLine();

            List<Player> players = new ArrayList<>();
            for (int i = 1; i <= numPlayers; i++) {
                System.out.print("Enter the name for player " + i + ": ");
                String name = sc.nextLine();
                players.add(new Player(name, new Die(numSides)));
            }

            PrintStream printstream = new PrintStream(fileOutputStream);


            for (Player player : players) {
                player.rollDie();
                printstream.println("Player " + player.getName() + " rolled a " + player.getDie().getValue() +
                        " on a " + numSides + " sided die");

            }
            int winnerIndex = decideWinner(players);
            if (winnerIndex == -1) {
                printstream.println("It's a tie.");
            } else {

                printstream.println(players.get(winnerIndex).getName() + " won the game.");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

    }

    public static int decideWinner(List<Player> players) {
        int maxRoll = 0;
        int countMax = 0;
        int winnerIndex = -1;
        for (int i = 0; i < players.size(); i++) {
            int currentRoll = players.get(i).getDie().getValue();
            if (currentRoll > maxRoll) {
                maxRoll = currentRoll;
                winnerIndex = i;
                countMax = 1;
            } else if (currentRoll == maxRoll) {
                countMax++;
            }
        }
        return countMax > 1 ? -1 : winnerIndex;
    }

}
