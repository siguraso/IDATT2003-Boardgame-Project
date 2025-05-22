package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RollAgainTileTest {

  private Tile tile;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    tile = new RollAgainTile(1, new int[]{0, 0});
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.EVIL_PAUL);
  }

  @Test
  @DisplayName("Test the class getters of RollAgainTile")
  void testClassAccessors() {
    assertEquals(1, tile.getTileNumber());
    assertArrayEquals(new int[]{0, 0}, tile.getOnscreenPosition());
    assertEquals("RollAgainTile", tile.getTileType());
  }

  @Test
  @DisplayName("positive test for the performAction method of RollAgainTile")
  void testPerformAction() {
    try {
      ((RollAgainTile) tile).performAction(null);
    } catch (NullPointerException e) {
      assertEquals("Player cannot be null", e.getMessage());
    }

    ((RollAgainTile) tile).performAction(player);
    assertTrue(player.canRollAgain());
  }

  @Test
  @DisplayName("Negative test for the performAction method of RollAgainTile")
  void testNegativePerformAction() {
    try {
      ((RollAgainTile) tile).performAction(null);
    } catch (NullPointerException e) {
      assertEquals("Player cannot be null", e.getMessage());
    }
  }
}