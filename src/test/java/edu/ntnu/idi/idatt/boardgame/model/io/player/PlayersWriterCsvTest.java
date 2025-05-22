package edu.ntnu.idi.idatt.boardgame.model.io.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;

class PlayersWriterCsvTest {


  @Test
  @DisplayName("Test writing players file without real file operations")
  void testWritePlayersFileMocked() {
    PlayersWriterCsv writer = new PlayersWriterCsv();

    // Create a file that does not exist
    String fileDirectory = "I/DO/NOT/EXIT.csv";
    String fileName = "YESIRRYESIRRR.csv";

    List<Player> players = new ArrayList<>();
    players.add(new LadderGamePlayer("Player1", PlayerPiece.MY_LOVE));
    players.add(new LadderGamePlayer("Player2", PlayerPiece.MARIOTINELLI));

    try (MockedConstruction<FileWriter> mockedFileWriter = mockConstruction(FileWriter.class);
        MockedConstruction<BufferedWriter> mockedBufferedWriter = mockConstruction(
            BufferedWriter.class)) {

      // call the method with a dummy file path
      writer.writePlayersFile(fileDirectory, fileName, players);

      assertEquals(1, mockedFileWriter.constructed().size());

      assertEquals(1, mockedBufferedWriter.constructed().size());

      BufferedWriter mockWriter = mockedBufferedWriter.constructed().getFirst();

      // Verify write was called twice (once for each player)
      verify(mockWriter).write("Player1,MY_LOVE\n");
      verify(mockWriter).write("Player2,MARIOTINELLI\n");
    } catch (IOException e) {
      fail("Test should not throw IOException: " + e.getMessage());
    }
  }
}