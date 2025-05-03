package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RollAgainTileTest {

  Tile tile;
  Player player;

  @BeforeEach
  void setUp() {
    tile = new RollAgainTile(1, new int[]{0, 0});
    player = new Player("TestPlayer", PlayerPiece.EVIL_PAUL);
  }

  @Test
  void testClassAccessors() {
    assertEquals(1, tile.getTileNumber());
    assertArrayEquals(new int[]{0, 0}, tile.getOnscreenPosition());
    assertEquals("RollAgainTile", tile.getTileType());
  }

  @Test
  void testPerformAction() {
    try {
      ((RollAgainTile) tile).performAction(null);
    } catch (NullPointerException e) {
      assertEquals("Player cannot be null.", e.getMessage());
    }

    ((RollAgainTile) tile).performAction(player);
    assertTrue(player.canRollAgain());
  }
}