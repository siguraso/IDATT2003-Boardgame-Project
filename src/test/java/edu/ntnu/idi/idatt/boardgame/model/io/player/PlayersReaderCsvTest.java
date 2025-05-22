package edu.ntnu.idi.idatt.boardgame.model.io.player;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PlayersReaderCsvTest {

  @Test
  @DisplayName("positive tests for the readPlayersFile method")
  void testReadPlayersFile() {
    PlayersReaderCsv reader = new PlayersReaderCsv();
    String filePath = "src/test/resources/CSV/sigve_players.csv";

    // Test reading from file
    List<Player> players = reader.readPlayersFile(filePath);

    // Check if the players list is not empty
    assertFalse(players.isEmpty());

    players.forEach(player -> {
      // check if it includes the players
      if (!player.getName().equals("styggve") && !player.getName().equals("sigern")
          && !player.getName().equals("liggve") && !player.getName().equals("sigve these nuts")) {
        fail("LadderGamePlayer not found in the list: " + player.getName());
      }
    });

    // Check if the first player is as expected
    Player firstPlayer = players.getFirst();
    assertEquals("sigern", firstPlayer.getName());
    assertEquals(PlayerPiece.PAUL, firstPlayer.getPlayerPiece());

    // negative test
    filePath = "src/test/resources/CSV/BRRRRRRRRRRR.csv";

    try {
      reader.readPlayersFile(filePath);
      fail("Expected an exception to be thrown");
    } catch (RuntimeException e) {
      if (!e.getMessage().contains("No such file or directory") && !e.getMessage()
          .contains("The system cannot find the file specified")) {
        fail("Expected a file not found exception");
      }

    }
  }

}