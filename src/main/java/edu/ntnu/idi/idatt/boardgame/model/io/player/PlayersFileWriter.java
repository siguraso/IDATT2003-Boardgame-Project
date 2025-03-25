package edu.ntnu.idi.idatt.boardgame.model.io.player;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.HashMap;

/**
 * An interface for writing a player to a .csv file.
 */
public interface PlayersFileWriter {

  /**
   * Writes a player to a new file so that it can be loaded later on by the user.
   *
   * @param fileDirectory directory to save the file to.
   * @param players       {@link java.util.HashMap} containing the players to write to the file.
   */
  void writePlayersFile(String fileDirectory, HashMap<String, Player> players);
}
