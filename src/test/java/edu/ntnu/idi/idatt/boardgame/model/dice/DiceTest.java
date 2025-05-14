package edu.ntnu.idi.idatt.boardgame.model.dice;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiceTest {

  private Dice dice;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    // Arrange
    dice = new Dice(2, 6);
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.LOCKED_IN_SNOWMAN);
    dice.addObserver(player);
  }

  @AfterEach
  void tearDown() {
    // Reset
    dice = null;
    player = null;
  }

  @Test
  void roll() {
    // Arrange
    int initialValue = player.getPosition();

    // Act
    dice.roll();

    // Assert
    assertTrue(player.getPosition() > initialValue);
    assertNotEquals(player.getPosition(), initialValue + 1);
  }

  @Test
  void testObserverPattern() {
    LadderGamePlayer newPlayer = new LadderGamePlayer("testPlayer2",
        PlayerPiece.PROPELLER_ACCESSORIES);
    // Test add
    assertDoesNotThrow(() -> dice.addObserver(newPlayer));
    assertTrue(dice.getObservers().contains(newPlayer));

    // Test remove
    assertDoesNotThrow(() -> dice.removeObserver(newPlayer));
    assertFalse(dice.getObservers().contains(newPlayer));

    // Test notify
    // Arrange
    int playerStartingPosition = player.getPosition();
    int[] expected = {1, 1};

    // Act
    dice.notifyObservers(expected);

    // Assert
    assertEquals(playerStartingPosition + expected[0] + expected[1], player.getPosition());

    // Test get observers
    assertEquals(new ArrayList<>(Collections.singletonList(player)), dice.getObservers());
  }

  @Test
  void testInvalidDiceCreation() {
    // Test invalid die creation
    assertThrows(IllegalArgumentException.class, () -> new Dice(0, 6));
    assertThrows(IllegalArgumentException.class, () -> new Dice(-1, 6));
    assertThrows(IllegalArgumentException.class, () -> new Dice(2, 1));
    assertThrows(IllegalArgumentException.class, () -> new Dice(2, 0));
    assertThrows(IllegalArgumentException.class, () -> new Dice(2, -1));
  }
}