package edu.ntnu.idi.idatt.boardgame.model.io.player;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * An interface for writing a player to a .csv file.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class PlayersWriterCSV implements PlayersFileWriter {

  /**
   * Writes a player to a new file so that it can be loaded later on by the user.
   */
  public PlayersWriterCSV() {
  }

  @Override
  public void writePlayersFile(String fileDirectory, String fileName,
      HashMap<String, Player> players) {
    try (FileWriter fileWriter = new FileWriter(fileDirectory + File.separator + fileName + ".csv");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

      for (String key : players.keySet()) {
        bufferedWriter.write(
            players.get(key).getName() + "," + players.get(key).getPlayerPiece() + "\n");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
