package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RollAgainActionTest {
  private RollAgainAction action;
  private Player player;

  @BeforeEach
  void setUp() {
    // Arrange
    action = new RollAgainAction();
    player = new Player("TestPlayer", PlayerPiece.PAUL);
  }

  @AfterEach
  void tearDown() {
    // Reset
    action = null;
    player = null;
  }

  @Test
  void performAction() {
    // Act
    action.performAction(player);

    // Assert
    assertTrue(player.canRollAgain(), "Player should be able to roll again");
  }

  @Test
  void testConstructor() {
    // Assert
    assertNotNull(action, "RollAgainAction should be instantiated");
    assertThrows(NullPointerException.class, () -> action.performAction(null));
  }
}