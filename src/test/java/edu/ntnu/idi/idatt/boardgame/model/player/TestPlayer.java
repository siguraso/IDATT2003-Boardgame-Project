package edu.ntnu.idi.idatt.boardgame.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.WinnerTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TestPlayer {

  Player player;
  NormalTile tile;

  @BeforeEach
  void setUp() {
    player = new Player("Player 1");
    tile = new NormalTile(1, new int[]{0, 0});
    player.move(tile);
  }

  @Test
  @DisplayName("Test if the getters return the correct values")
  void testGetters() {
    try {
      assertEquals(player.getName(), "Player 1");
      assertEquals(player.getCurrentTile().getTileNumber(), 1);
    } catch (Exception e) {
      fail("The test failed as the class threw an exception.");
    }
  }

  @Test
  @DisplayName("Test if the player is a winner, also tests the setWinner method")
  void testIsWinner() {
    try {
      assertFalse(player.isWinner());
      WinnerTile winnerTile = new WinnerTile(2, new int[]{0, 0});
      player.move(winnerTile);
      winnerTile.performAction(player);
      assertTrue(player.isWinner());
    } catch (Exception e) {
      fail("The test failed as the class threw an exception.");
    }
  }

  @Test
  @DisplayName("Test if the player can move to a new tile")
  void testMove() {
    try {
      assertEquals(player.getCurrentTile().getTileNumber(), 1);
      NormalTile newTile = new NormalTile(2, new int[]{0, 0});
      player.move(newTile);
      assertEquals(player.getCurrentTile().getTileNumber(), 2);
    } catch (Exception e) {
      fail("The test failed as the class threw an exception.");
    }
  }

}