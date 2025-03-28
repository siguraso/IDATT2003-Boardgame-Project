package edu.ntnu.idi.idatt.boardgame.model.io.player;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.HashMap;

/**
 * An interface for writing a player to a .csv file.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public interface PlayersFileWriter {

  /**
   * Writes a player to a new file so that it can be loaded later on by the user.
   *
   * @param fileDirectory directory to save the file to.
   * @param players       {@link java.util.HashMap} containing the players to write to the file.
   * @param fileName      name of the file to write to.
   */
  void writePlayersFile(String fileDirectory, String fileName, HashMap<String, Player> players);
}
