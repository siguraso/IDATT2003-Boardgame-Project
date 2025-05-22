package edu.ntnu.idi.idatt.boardgame.model.board;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import java.util.HashMap;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class BoardFactoryTest {

  @Test
  void testCreateBoard() {
    Board regularBoard = BoardFactory.createBoard(BoardType.LADDER_GAME_REGULAR, false, null);
    HashMap<Integer, String> tileTypes = new HashMap<>(regularBoard.getTileTypes());

    IntStream.range(1, 91).forEach(i -> {
      if (!tileTypes.containsKey(i)) {
        fail("Tile " + i + " should be present in the board");
      }
      if (i == 1) {
        assertEquals(TileType.NORMAL.getTileType(), tileTypes.get(i));
      } else if (i == 90) {
        assertEquals(TileType.WINNER.getTileType(), tileTypes.get(i));
      } else if (i == 2 || i == 8 || i == 24 || i == 33 || i == 36 || i == 42 || i == 43 || i == 49
          || i == 56 || i == 64 || i == 65 || i == 68 || i == 74 || i == 87) {
        assertEquals(TileType.LADDER.getTileType(), tileTypes.get(i));
      } else {
        assertEquals(TileType.NORMAL.getTileType(), tileTypes.get(i));
      }
    });

    Board specialBoard = BoardFactory.createBoard(BoardType.LADDER_GAME_SPECIAL, false, null);
    HashMap<Integer, String> specialTileTypes = new HashMap<>(specialBoard.getTileTypes());

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

    Board parioMartyBoard = BoardFactory.createBoard(BoardType.PARIO_MARTY, false, null);
    HashMap<Integer, String> parioTileTypes = new HashMap<>(parioMartyBoard.getTileTypes());

    IntStream.range(1, 36).forEach(i -> {
      if (!parioTileTypes.containsKey(i)) {
        fail("Tile " + i + " should be present in the board");
      }

      if (i == 1) {
        assertEquals(TileType.NORMAL.getTileType(), parioTileTypes.get(i));
      } else if (i == 2 || i == 3 || i == 5 || i == 7 || i == 9 || i == 10 || i == 11 || i == 14
          || i == 15 || i == 18 || i == 19 || i == 20 || i == 22 || i == 23 || i == 26 || i == 27
          || i == 30 || i == 31 || i == 33 || i == 34) {
        assertEquals(TileType.ADD_COINS.getTileType(), parioTileTypes.get(i));
      } else if (i == 13 || i == 21) {
        assertEquals(TileType.MOWSER.getTileType(), parioTileTypes.get(i));
      } else if (i == 24) {
        assertEquals(TileType.RETURN_TO_START.getTileType(), parioTileTypes.get(i));
      } else if (i == 28 || i == 6 || i == 16) {
        assertEquals(TileType.ROLL_AGAIN.getTileType(), parioTileTypes.get(i));
      } else if (i == 17 || i == 35) {
        assertEquals(TileType.RANDOM_ACTION.getTileType(), parioTileTypes.get(i));
      } else {
        assertEquals(TileType.REMOVE_COINS.getTileType(), parioTileTypes.get(i));
      }
    });

  }


  @Test
  void testCreateBoardJson() {
    Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_REGULAR, true,
        null);

    HashMap<Integer, String> regularTileTypes = new HashMap<>(board.getTileTypes());
    IntStream.range(1, 91).forEach(i -> {
      if (!regularTileTypes.containsKey(i)) {
        fail("Tile " + i + " should be present in the board");
      }
      if (i == 1) {
        assertEquals(TileType.NORMAL.getTileType(), regularTileTypes.get(i));
      } else if (i == 90) {
        assertEquals(TileType.WINNER.getTileType(), regularTileTypes.get(i));
      } else if (i == 2 || i == 8 || i == 24 || i == 33 || i == 36 || i == 42 || i == 43 || i == 49
          || i == 56 || i == 64 || i == 65 || i == 68 || i == 74 || i == 87) {
        assertEquals(TileType.LADDER.getTileType(), regularTileTypes.get(i));
      } else {
        assertEquals(TileType.NORMAL.getTileType(), regularTileTypes.get(i));
      }
    });

    Board specialBoard = BoardFactory.createBoard(BoardType.LADDER_GAME_SPECIAL, true,
        null);

    HashMap<Integer, String> specialTileTypes = new HashMap<>(specialBoard.getTileTypes());

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


  }

  @Test
  void testCreateCustomBoardJson() {
    // negative tests
    try {
      Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true,
          "src/test/resources/JSON/Not90Tiles.json");
      fail("Should throw an exception");
    } catch (RuntimeException e) {
      assertEquals("Board must have 90 tiles", e.getMessage());
    }

    try {
      Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true,
          "src/test/resources/JSON/NotInOrder.json");
      fail("Should throw an exception");
    } catch (RuntimeException e) {
      assertEquals("Tiles must be in order from 1 to 90", e.getMessage());
    }

    try {
      Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true,
          "src/test/resources/JSON/NoFirstNormalTile.json");
      fail("Should throw an exception");
    } catch (RuntimeException e) {
      assertEquals("First tile must be a normal tile", e.getMessage());
    }

    try {
      Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true,
          "src/test/resources/JSON/NoLastWinnerTile.json");
      fail("Should throw an exception");
    } catch (RuntimeException e) {
      assertEquals("Last tile must be a winner tile", e.getMessage());
    }

    // Positive test

    try {
      Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_JSON, true,
          "src/test/resources/JSON/TestBoard.json");
      assertNotNull(board);

      board.getTileTypes().forEach((key, value) -> {
        if (key == 1) {
          assertEquals(TileType.NORMAL.getTileType(), value);
        } else if (key == 90) {
          assertEquals(TileType.WINNER.getTileType(), value);
        } else {
          assertEquals(TileType.RANDOM_ACTION.getTileType(), value);
        }
      });
    } catch (RuntimeException e) {
      fail("Should not throw an exception");
    }

  }
}