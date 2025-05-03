package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NormalTileTest {


  Tile tile;
  Player player;

  @BeforeEach
  void setUp() {
    tile = new NormalTile(1, new int[]{0, 0});
    player = new Player("TestPlayer", PlayerPiece.EVIL_PAUL);
  }

  @Test
  void testClassAccessors() {
    assertEquals(1, tile.getTileNumber());
    assertArrayEquals(new int[]{0, 0}, tile.getOnscreenPosition());
    assertEquals("NormalTile", tile.getTileType());
  }

}