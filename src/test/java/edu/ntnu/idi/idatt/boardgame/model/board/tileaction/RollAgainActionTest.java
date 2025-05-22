package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RollAgainActionTest {

  private RollAgainAction action;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    // Arrange
    action = new RollAgainAction();
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.PAUL);
  }

  @AfterEach
  void tearDown() {
    // Reset
    action = null;
    player = null;
  }

  @Test
  @DisplayName("Test the constructor of RollAgainAction, and check for exceptions")
  void testRollAgainContructor() {
    try {
      new RollAgainAction();
    } catch (Exception e) {
      fail("Constructor should not throw an exception");
    }
  }

  @Test
  @DisplayName("Positive test for performAction method of RollAgainAction")
  void performAction() {
    // Act
    action.performAction(player);

    // Assert
    assertTrue(player.canRollAgain(), "Player should be able to roll again");
  }

  @Test
  @DisplayName("Negative test for performAction method of RollAgainAction")
  void testConstructor() {
    // Assert
    assertNotNull(action, "RollAgainAction should be instantiated");
    assertThrows(NullPointerException.class, () -> action.performAction(null));
  }
}