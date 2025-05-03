package edu.ntnu.idi.idatt.boardgame.model.io.player;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An interface for reading a .csv file containing players.
 *
 * <p>This way the user can read saved player profiles</p>
 */
public class PlayersReaderCsv implements PlayersFileReader {

  /**
   * Constructor for the PlayersReaderCsv class.
   */
  public PlayersReaderCsv() {
  }

  @Override
  public List<Player> readPlayersFile(String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      ArrayList<Player> players = new ArrayList<>();

      while ((line = br.readLine()) != null) {
        String[] values = line.split(",");

        Player player = new Player(values[0], PlayerPiece.valueOf(values[1]));
        players.add(player);
      }
      return players;
    } catch (IOException e) {
      throw new RuntimeException("Failed to read players from file: " + e.getMessage());
    }
  }
}
