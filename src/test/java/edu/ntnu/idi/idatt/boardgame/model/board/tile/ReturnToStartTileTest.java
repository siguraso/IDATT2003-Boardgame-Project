package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnToStartTileTest {

  Tile tile;
  Player player;

  @BeforeEach
  void setUp() {
    tile = new ReturnToStartTile(1, new int[]{0, 0});
    player = new Player("TestPlayer", PlayerPiece.EVIL_PAUL);
  }

  @Test
  void testConstructor() {
  }

}