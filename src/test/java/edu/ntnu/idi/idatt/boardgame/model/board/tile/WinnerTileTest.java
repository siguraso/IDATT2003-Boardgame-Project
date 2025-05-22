package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WinnerTileTest {

  private Tile tile;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    tile = new WinnerTile(1, new int[]{0, 0});
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.EVIL_PAUL);
  }

  @Test
  @DisplayName("Test the class getters of WinnerTile")
  void testClassAccessors() {
    assertEquals(1, tile.getTileNumber());
    assertArrayEquals(new int[]{0, 0}, tile.getOnscreenPosition());
    assertEquals("WinnerTile", tile.getTileType());
  }

  @Test
  @DisplayName("Positive test for the performAction method of WinnerTile")
  void testPerformAction() {
    ((WinnerTile) tile).performAction(player);

    assertTrue(player.isWinner());
  }

  @Test
  @DisplayName("Negative test for the performAction method of WinnerTile")
  void testNegativePerformAction() {
    try {
      ((WinnerTile) tile).performAction(null);
    } catch (NullPointerException e) {
      assertEquals("Player cannot be null.", e.getMessage());
    }
  }

}