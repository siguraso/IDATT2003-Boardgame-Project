package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReturnToStartTileTest {

  private Tile tile;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    tile = new ReturnToStartTile(1, new int[]{0, 0});
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.EVIL_PAUL);
  }

  @Test
  @DisplayName("Test the class getters for ReturnToStartTile")
  void testAccessors() {
    assertEquals(1, tile.getTileNumber());
    assertArrayEquals(new int[]{0, 0}, tile.getOnscreenPosition());
    assertEquals("ReturnToStartTile", tile.getTileType());
  }

  @Test
  @DisplayName("positive test the performAction method for ReturnToStartTile")
  void testPerformAction() {
    assertEquals(1, player.getPosition());
  }

  @Test
  @DisplayName("Negative test the performAction method for ReturnToStartTile")
  void testNegativePerformAction() {
    try {
      ((ReturnToStartTile) tile).performAction(null);
    } catch (NullPointerException e) {
      assertEquals("Player cannot be null", e.getMessage());
    }
  }

}