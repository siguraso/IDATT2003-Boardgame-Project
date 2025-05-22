package edu.ntnu.idi.idatt.boardgame.model.io.player;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.MockedConstruction;


class PlayersReaderCsvTest {

  @Test
  @DisplayName("Test reading players file without real file operations")
  void testReadPlayersFileMocked() {
    PlayersReaderCsv reader = new PlayersReaderCsv();

    // Setup the mock data that would come from the file
    String fileContent = """
        sigern,PAUL
        styggve,MIKE
        liggve,SAUL
        sigve these nuts,JESSE
        """;

    try (MockedConstruction<FileReader> mockedFileReader = mockConstruction(FileReader.class);
        MockedConstruction<BufferedReader> mockedBufferedReader = mockConstruction(
            BufferedReader.class, (mock, context) -> {
              // Configure BufferedReader to return lines from our test data
              when(mock.readLine())
                  .thenReturn("sigern,PAUL")
                  .thenReturn("styggve,EVIL_PAUL")
                  .thenReturn("liggve,MARIOTINELLI")
                  .thenReturn("sigve these nuts,LOCKED_IN_SNOWMAN")
                  .thenReturn(null); // End of file
            })) {

      // Call the method with a dummy file path
      List<Player> players = reader.readPlayersFile("dummy/path.csv");

      // Verify results
      assertEquals(4, players.size(), "Should have read 4 players");

      // Check individual players
      assertEquals("sigern", players.get(0).getName());
      assertEquals(PlayerPiece.PAUL, players.get(0).getPlayerPiece());

      assertEquals("styggve", players.get(1).getName());
      assertEquals(PlayerPiece.EVIL_PAUL, players.get(1).getPlayerPiece());

      assertEquals("liggve", players.get(2).getName());
      assertEquals(PlayerPiece.MARIOTINELLI, players.get(2).getPlayerPiece());

      assertEquals("sigve these nuts", players.get(3).getName());
      assertEquals(PlayerPiece.LOCKED_IN_SNOWMAN, players.get(3).getPlayerPiece());

      // Verify the reader was constructed
      assertEquals(1, mockedFileReader.constructed().size());
      assertEquals(1, mockedBufferedReader.constructed().size());
    } catch (RuntimeException e) {
      fail("Should not throw RuntimeException: " + e.getMessage());
    }
  }

}