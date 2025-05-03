package edu.ntnu.idi.idatt.boardgame.model.io.player;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An interface for writing a player to a .csv file.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class PlayersWriterCsv implements PlayersFileWriter {

  /**
   * Writes a player to a new file so that it can be loaded later on by the user.
   */
  public PlayersWriterCsv() {
  }

  @Override
  public void writePlayersFile(String fileDirectory, String fileName, List<Player> players) {
    try (FileWriter fileWriter = new FileWriter(fileDirectory);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

      for (Player player : players) {
        bufferedWriter.write(
            player.getName() + "," + player.getPlayerPiece() + "\n");
      }

    } catch (IOException e) {
      throw new RuntimeException("Failed to write players to file: " + e.getMessage());
    }

  }
}
