package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LadderActionTest {

  @Test
  @DisplayName("Tests the constructor of LadderAction, and check for exceptions")
  void testConstructor() {
    assertDoesNotThrow(() -> new LadderAction(5));
  }

  @Test
  @DisplayName("Positive test for performAction method of LadderAction")
  void performAction() {
    // Arrange
    int destination = 5;
    LadderAction ladderAction = new LadderAction(destination);
    LadderGamePlayer player = new LadderGamePlayer("TestPlayer", PlayerPiece.MARIOTINELLI);

    // Act
    ladderAction.performAction(player);

    // Assert
    assertEquals(destination, player.getPosition());
  }

  @Test
  @DisplayName("Negative test for performAction method of LadderAction")
  void testNegativePerformAction() {
    // Arrange
    int destination = -5;
    LadderGamePlayer player = new LadderGamePlayer("TestPlayer", PlayerPiece.MARIOTINELLI);
    ParioMartyPlayer invalidPlayer = new ParioMartyPlayer("AYO BRRRRRRRRRRRRR",
        PlayerPiece.MARIOTINELLI);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> new LadderAction(91));
    assertThrows(IllegalArgumentException.class, () -> new LadderAction(90));
    assertThrows(IllegalArgumentException.class, () -> new LadderAction(0));
    assertThrows(IllegalArgumentException.class, () ->
        new LadderAction(destination).performAction(player));
    assertThrows(NullPointerException.class, () ->
        new LadderAction(5).performAction(null));
    assertThrows(InvalidPlayerException.class, () ->
        new LadderAction(5).performAction(invalidPlayer));
  }
}