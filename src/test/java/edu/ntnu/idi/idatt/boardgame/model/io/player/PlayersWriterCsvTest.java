package edu.ntnu.idi.idatt.boardgame.model.io.player;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PlayersWriterCsvTest {

  @Test
  void testWritePlayersFile() {
    PlayersWriterCsv writer = new PlayersWriterCsv();
    String fileDirectory = "test.csv";
    String fileName = "test.csv";
    List<Player> players = new ArrayList<>();
    players.add(new Player("Player1", PlayerPiece.MY_LOVE));
    players.add(new Player("Player2", PlayerPiece.MARIOTINELLI));

    // Test writing to file
    writer.writePlayersFile(fileDirectory, fileName, players);

    // Check if the file exists
    File file = new File(fileDirectory);
    assertTrue(file.exists());

    // check if the file can be read

    PlayersReaderCsv reader = new PlayersReaderCsv();

    List<Player> readPlayers = reader.readPlayersFile(fileDirectory);

    readPlayers.forEach(player -> {
      assertEquals(player.getName(), players.get(readPlayers.indexOf(player)).getName());
    });

  }
}