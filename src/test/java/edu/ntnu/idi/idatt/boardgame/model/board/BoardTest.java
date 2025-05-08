package edu.ntnu.idi.idatt.boardgame.model.board;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import java.util.HashMap;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  void testConstructor() {
    try {
      Board board = new Board(new HashMap<>());
      assertNotNull(board);
    } catch (Exception e) {
      fail("Constructor should not throw an exception");
    }

    try {
      Board board2 = new Board(null);
    } catch (NullPointerException e) {
      assertEquals("Tiles cannot be null.", e.getMessage());
    }
  }

  @Test
  void testTiles() {
    Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_REGULAR, false, null);

    HashMap<Integer, Tile> tiles = (HashMap<Integer, Tile>) board.tiles();
    assertNotNull(tiles);

    assertEquals(90, tiles.size());
    IntStream.range(1, 91).forEach(i -> {
      if (!tiles.containsKey(i)) {
        fail("Tile " + i + " should be present in the board");
      }
    });
  }

  @Test
  void testGetTileTypes() {
    Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_REGULAR, false, null);

    HashMap<Integer, String> tileTypes = (HashMap<Integer, String>) board.getTileTypes();
    assertNotNull(tileTypes);

    assertEquals(90, tileTypes.size());
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
  }

}