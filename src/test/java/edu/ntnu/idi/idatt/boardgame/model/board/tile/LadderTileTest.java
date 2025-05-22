package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LadderTileTest {

  private LadderTile tile;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    tile = new LadderTile(1, new int[]{0, 0}, 2);
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.EVIL_PAUL);
  }

  @Test
  @DisplayName("Test the class getters of LadderTile")
  void testClassAccessors() {
    assertEquals(1, tile.getTileNumber());
    assertEquals("LadderTile", tile.getTileType());
    assertArrayEquals(new int[]{0, 0}, tile.getOnscreenPosition());
    assertEquals(2, tile.getDestinationTileNumber());
  }

  @Test
  @DisplayName("positive test the performAction method of LadderTile")
  void testPerformAction() {
    tile.performAction(player);
    assertEquals(2, player.getPosition());
  }

  @Test
  @DisplayName("Negative test the performAction method of LadderTile")
  void testNegativePerformAction() {
    assertThrows(NullPointerException.class, () -> tile.performAction(null));
    assertThrows(InvalidPlayerException.class,
        () -> tile.performAction(new ParioMartyPlayer("InvalidPlayer", PlayerPiece.MARIOTINELLI)));
  }


}