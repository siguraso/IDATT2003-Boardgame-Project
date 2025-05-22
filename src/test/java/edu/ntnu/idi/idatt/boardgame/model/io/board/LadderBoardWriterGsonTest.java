package edu.ntnu.idi.idatt.boardgame.model.io.board;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.io.FileWriter;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.MockedConstruction;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Test class for LadderBoardWriterGson, this class NEEDS to be run on Java 22 or under, since the
 * mock file reader is not supported in Java 23+.
 */
class LadderBoardWriterGsonTest {

  private LadderBoardWriterGson ladderBoardWriterGson;
  private BoardFileReader boardFileReader;

  @BeforeEach
  void setUp() {
    ladderBoardWriterGson = new LadderBoardWriterGson();
    boardFileReader = new LadderBoardReaderGson();
  }

  @Test
  @DisplayName("Tests the write board file method of LadderBoardWriterGson without file operations")
  void testWriteBoardWithoutFileHandling() {
    HashMap<Integer, Tile> tiles = new HashMap<>();
    tiles.put(1, new NormalTile(1, new int[]{0, 0}));
    tiles.put(90, new NormalTile(90, new int[]{0, 0}));
    Board board = new Board(tiles);

    try (MockedConstruction<FileWriter> mockedFileWriter = mockConstruction(FileWriter.class);
        MockedConstruction<BufferedWriter> mockedBufferedWriter = mockConstruction(
            BufferedWriter.class)) {

      // write a boardfile to a non exitent path
      ladderBoardWriterGson.writeBoardFile(board, "old_man_falling_wav.json");

      // Verify a FileWriter was created with the correct path
      assertEquals(1, mockedFileWriter.constructed().size());
      verify(mockedFileWriter.constructed().getFirst()).close();

      // Verify a BufferedWriter was created and write was called
      assertEquals(1, mockedBufferedWriter.constructed().size());
      verify(mockedBufferedWriter.constructed().getFirst()).write(anyString());
      verify(mockedBufferedWriter.constructed().getFirst()).close();
    } catch (IOException e) {
      fail("Test should not throw IOException: " + e.getMessage());
    }
  }
}