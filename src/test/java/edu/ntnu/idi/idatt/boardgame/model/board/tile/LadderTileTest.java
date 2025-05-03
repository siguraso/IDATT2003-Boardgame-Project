package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LadderTileTest {

  LadderTile tile;
  Player player;

  @BeforeEach
  void setUp() {
    tile = new LadderTile(1, new int[]{0, 0}, 2);
    player = new Player("TestPlayer", PlayerPiece.EVIL_PAUL);
  }

  @Test
  void testClassAccessors() {
    assertEquals(1, tile.getTileNumber());
    assertEquals("LadderTile", tile.getTileType());
    assertArrayEquals(new int[]{0, 0}, tile.getOnscreenPosition());
    assertEquals(2, tile.getDestinationTileNumber());
  }

  @Test
  void testPerformAction() {
    tile.performAction(player);
    assertEquals(2, player.getPosition());
  }


}