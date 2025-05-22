package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomActionTileTest {

  private Tile tile;
  private LadderGamePlayer player;
  private ArrayList<Player> players;

  @BeforeEach
  void setUp() {
    tile = new RandomActionTile(1, new int[]{0, 0}, BoardType.LADDER_GAME_REGULAR);

    player = new LadderGamePlayer("TestPlayer", PlayerPiece.EVIL_PAUL);
    LadderGamePlayer player1 = new LadderGamePlayer("TestPlayer1", PlayerPiece.EVIL_PAUL);

    player1.moveTo(2);
    player.moveTo(5);

    players = new ArrayList<>();
    players.add(player);
    players.add(player1);
  }

  @Test
  @DisplayName("positive test the constructor of RandomActionTile")
  void testConstructor() {
    // Test constructor
    assertEquals(1, tile.getTileNumber());
    assertArrayEquals(new int[]{0, 0}, tile.getOnscreenPosition());
    assertEquals("RandomActionTile", tile.getTileType());
  }

  @Test
  @DisplayName("negative test the constructor of RandomActionTile")
  void testNegativeConstructor() {
    // Test constructor with null board
    try {
      new RandomActionTile(1, new int[]{0, 0}, null);
      fail("Expected an exception to be thrown");
    } catch (NullPointerException e) {
      assertEquals("Board type cannot be null", e.getMessage());
    }
  }


  @Test
  void testClassAccessors() {
    assertEquals(1, tile.getTileNumber());
    assertArrayEquals(new int[]{0, 0}, tile.getOnscreenPosition());
    assertEquals("RandomActionTile", tile.getTileType());

    ((RandomActionTile) tile).setPlayers(players);

    // make the swap action happen
    boolean swapAction = false;

    while (!swapAction) {
      ((RandomActionTile) tile).performAction(player);

      try {
        ((RandomActionTile) tile).getPlayerToSwapWith();
        swapAction = true;
      } catch (Exception e) {
        continue;
      }

    }

    // test getPlayerToSwapWith
    try {
      assertEquals("TestPlayer1", ((RandomActionTile) tile).getPlayerToSwapWith().getName());
      assertEquals(2, ((RandomActionTile) tile).getTileAction());
    } catch (Exception e) {
      fail("Expected no exception to be thrown");
    }

  }

  @Test
  void testSetPlayers() {

    ((RandomActionTile) tile).setPlayers(players);

    boolean swapAction = false;

    // if it manages to do the swap action, then we successfully set the players
    while (!swapAction) {
      ((RandomActionTile) tile).performAction(player);

      try {
        ((RandomActionTile) tile).getPlayerToSwapWith();
        swapAction = true;
      } catch (Exception e) {
        assertFalse(swapAction);
      }

    }

  }

  @Test
  void testPerformAction() {
    try {
      ((RandomActionTile) tile).setPlayers(players);
      ((RandomActionTile) tile).performAction(player);

      int numberOfActionsRun = 0;
      boolean returnToStartAction = false;
      boolean rollAgainAction = false;
      boolean swapAction = false;
      boolean moveToRandomTileAction = false;

      while (numberOfActionsRun < 4) {
        ((RandomActionTile) tile).performAction(player);
        if (((RandomActionTile) tile).getTileAction() == 0) {
          if (!returnToStartAction) {
            numberOfActionsRun++;
            returnToStartAction = true;
            assertEquals(1, player.getPosition());
          }
        } else if (((RandomActionTile) tile).getTileAction() == 1) {
          if (!rollAgainAction) {
            numberOfActionsRun++;
            rollAgainAction = true;
            assertTrue(player.canRollAgain());
          }
        } else if (((RandomActionTile) tile).getTileAction() == 2) {
          if (!swapAction) {
            numberOfActionsRun++;
            swapAction = true;
            assertEquals("TestPlayer1", ((RandomActionTile) tile).getPlayerToSwapWith().getName());
          }
        } else if (((RandomActionTile) tile).getTileAction() == 3) {
          if (!moveToRandomTileAction) {
            numberOfActionsRun++;
            moveToRandomTileAction = true;
            assertTrue(player.getPosition() >= 1 && player.getPosition() <= 89);
          }
        }
      }

    } catch (Exception e) {
      fail("Expected no exception to be thrown");
    }

  }

  @Test
  @DisplayName("Negative tests")
  void negativeTests() {
    // negative test getPlayerToSwapWith
    try {
      ((RandomActionTile) tile).getPlayerToSwapWith();
      fail("Expected an exception to be thrown");
    } catch (Exception e) {
      assertEquals("Player to swap with is not set. Please call performAction() first.",
          e.getMessage());
    }

    // negative test for performAction
    try {
      ((RandomActionTile) tile).performAction(null);
      fail("Expected an exception to be thrown");
    } catch (NullPointerException e) {
      if (!e.getMessage().contains("Player cannot be null")) {
        fail("Expected a player cannot be null exception");
      }
    }

    try {
      ((RandomActionTile) tile).setPlayers(null);
      fail("Expected an exception to be thrown");
    } catch (NullPointerException e) {
      assertEquals("Players cannot be null. Please provide a valid list of players.",
          e.getMessage());
    }

    try {
      ((RandomActionTile) tile).setPlayers(new ArrayList<>());
      fail("Expected an exception to be thrown");
    } catch (IllegalArgumentException e) {
      assertEquals("At least two players are required to perform a swap.", e.getMessage());
    }
  }
}