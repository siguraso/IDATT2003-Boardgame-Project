package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReturnToStartActionTest {

  private ReturnToStartAction returnToStartAction;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    returnToStartAction = new ReturnToStartAction();
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.MARIOTINELLI);
  }

  @AfterEach
  void tearDown() {
    returnToStartAction = null;
    player = null;
  }

  @Test
  @DisplayName("Test the constructor of ReturnToStartAction, and check for exceptions")
  void testConstructor() {
    try {
      new ReturnToStartAction();
    } catch (Exception e) {
      fail("Constructor should not throw an exception");
    }
  }

  @Test
  @DisplayName("Positive test for performAction method of ReturnToStartAction")
  void performAction() {
    // Arrange
    player.moveTo(5); // Move the player to a different position

    // Act
    returnToStartAction.performAction(player);

    // Assert
    assertEquals(1, player.getPosition(), "Player should be returned to start position");
  }

  @Test
  @DisplayName("Negative test for performAction method and constructor of ReturnToStartAction")
  void negativePerformAction() {
    assertThrows(NullPointerException.class, () -> {
      returnToStartAction = new ReturnToStartAction();
      returnToStartAction.performAction(null);
    });
  }
}