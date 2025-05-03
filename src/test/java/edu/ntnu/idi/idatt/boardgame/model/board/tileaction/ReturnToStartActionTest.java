package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnToStartActionTest {
  private ReturnToStartAction returnToStartAction;
  private Player player;

  @BeforeEach
  void setUp() {
    returnToStartAction = new ReturnToStartAction();
    player = new Player("TestPlayer", PlayerPiece.MARIOTINELLI);
  }

  @AfterEach
  void tearDown() {
    returnToStartAction = null;
    player = null;
  }

  @Test
  void performAction() {
    // Arrange
    player.moveTo(5); // Move the player to a different position

    // Act
    returnToStartAction.performAction(player);

    // Assert
    assertEquals(1, player.getPosition(), "Player should be returned to start position");
  }

  @Test
  void negativePerformAction() {
    assertThrows(NullPointerException.class, () -> {
      returnToStartAction = new ReturnToStartAction();
      returnToStartAction.performAction(null);
    });
  }
}