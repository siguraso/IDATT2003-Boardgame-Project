package edu.ntnu.idi.idatt.boardgame.model.io.player;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPieces;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * An interface for reading a .csv file containing players.
 * <p>This way the user can read saved player profiles</p>
 */
public class PlayersReaderCSV {

  /**
   * Constructor for the PlayersReaderCSV class.
   */
  public PlayersReaderCSV() {
  }

  /**
   * Reads a .csv file containing players and returns a list of player objects.
   *
   * @param filePath The file path to the readable file.
   * @return A {@link HashMap} of {@link Player} objects containing the players from the .csv file.
   */
  public HashMap<String, Player> readPlayersFile(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      HashMap<String, Player> players = new HashMap<>();

      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        Player player = new Player(values[0], PlayerPieces.valueOf(values[1]));
        players.put(values[0], player);
      }
      return players;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static void main(String[] args) {
    // Test method to read a player from a file

    // Create a new PlayersReaderCSV object
    PlayersReaderCSV playersReaderCSV = new PlayersReaderCSV();

    // Read the players from a file
    HashMap<String, Player> players = playersReaderCSV.readPlayersFile(
        "/users/sigurdandris/Desktop/players.csv");

    for (String key : players.keySet()) {
      System.out.println("Name: " + players.get(key).getName() + ", PieceType: " + players.get(key)
          .getPieceType());
    }
  }
}
