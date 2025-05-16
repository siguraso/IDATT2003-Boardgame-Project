package edu.ntnu.idi.idatt.boardgame.model.io.board;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import java.io.File;
import java.util.HashMap;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LadderBoardReaderGsonTest {

  private LadderBoardReaderGson ladderBoardReaderGson;

  @BeforeEach
  void setUp() {
    ladderBoardReaderGson = new LadderBoardReaderGson();
  }


  @Test
  void testReadBoard() {
    // Positive tests
    File file = new File("src/test/resources/JSON/TestBoard.json");
    String filePath = file.getAbsolutePath();

    Board board = null;

    try {
      board = ladderBoardReaderGson.readBoardFile(filePath, true);
    } catch (Exception e) {
      fail("Should not throw an exception");
    }

    assertNotNull(board, "Board should not be null");

    // Check if the board is read correctly

    Board finalBoard = board;
    board.getTileTypes().keySet().forEach(tileType -> {
      if (tileType == 1) {
        assertEquals("NormalTile", finalBoard.getTileTypes().get(tileType));
      } else if (tileType == 90) {
        assertEquals("WinnerTile", finalBoard.getTileTypes().get(tileType));
      } else {
        assertEquals("RandomActionTile", finalBoard.getTileTypes().get(tileType));
      }
    });

    // test not custom board

    Board board2 = null;

    try {
      board2 = ladderBoardReaderGson.readBoardFile("/JSON/LadderGameSpecial.json",
          false);
    } catch (Exception e) {
      fail("Should not throw an exception");
    }

    HashMap<Integer, String> specialTileTypes = (HashMap<Integer, String>) board2.getTileTypes();

    IntStream.range(1, 91).forEach(i -> {
      if (!specialTileTypes.containsKey(i)) {
        fail("Tile " + i + " should be present in the board");
      }
      if (i == 90) {
        assertEquals(TileType.WINNER.getTileType(), specialTileTypes.get(i));
      } else if (i == 3 || i == 10 || i == 43 || i == 85) {
        assertEquals(TileType.RANDOM_ACTION.getTileType(), specialTileTypes.get(i));
      } else if (i == 2 || i == 24 || i == 36 || i == 49
          || i == 64 || i == 74 || i == 87) {
        assertEquals(TileType.LADDER.getTileType(), specialTileTypes.get(i));
      } else if (i == 6 || i == 30 || i == 62 || i == 68 || i == 82) {
        assertEquals(TileType.ROLL_AGAIN.getTileType(), specialTileTypes.get(i));
      } else if (i == 22 || i == 42 || i == 56 || i == 65) {
        assertEquals(TileType.RETURN_TO_START.getTileType(), specialTileTypes.get(i));
      } else {
        assertEquals(TileType.NORMAL.getTileType(), specialTileTypes.get(i));
      }
    });

    // Negative tests

    try {
      board = ladderBoardReaderGson.readBoardFile("src/test/resources/JSON/60DAYSSSS.json", true);
      fail("Should throw an exception");
    } catch (RuntimeException e) {
      if (!e.getMessage().contains("File not found") &&
          !e.getMessage().contains("The system cannot find the file specified")) {
        fail("Did not throw the expected exception");
      }
    }

    try {
      File file2 = new File("src/test/resources/JSON/Malformed.json");
      filePath = file2.getAbsolutePath();

      board = ladderBoardReaderGson.readBoardFile(filePath, true);
      fail("Should throw an exception");
    } catch (RuntimeException e) {
      if (!e.getMessage().contains(
          "Cannot invoke \"com.google.gson.JsonElement.getAsJsonArray()\"")) {
        fail("Did not throw the expected exception");
      }
    }


  }
}