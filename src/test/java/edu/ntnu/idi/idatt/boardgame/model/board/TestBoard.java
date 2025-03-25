package edu.ntnu.idi.idatt.boardgame.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.util.HashMap;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestBoard {

  Board board;

  @BeforeEach
  void setUp() {
    HashMap<Integer, Tile> tiles = new HashMap<>();

    IntStream.range(0, 18).boxed().forEach(i -> tiles.put(i, new NormalTile(i, new int[]{i, i})));

    board = new Board(tiles);
  }

  @Test
  @DisplayName("Tests class getters")
  void testGetters() {
    try {
      assertEquals(board.getTiles().size(), 18);
    } catch (Exception e) {
      fail("the test failed, as the class threw an exception");
    }
  }
  

}