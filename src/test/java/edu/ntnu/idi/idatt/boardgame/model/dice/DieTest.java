package edu.ntnu.idi.idatt.boardgame.model.dice;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DieTest {

  private Die die;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    // Arrange
    die = new Die(6);
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.LOCKED_IN_SNOWMAN);
    die.addObserver(player);
  }

  @AfterEach
  void tearDown() {
    // Reset
    die = null;
  }

  @Test
  @DisplayName("Test the constructor of Die, and check for exceptions")
  void testConstructor() {
    // Act
    try {
      new Die(6);
    } catch (IllegalArgumentException e) {
      fail("Constructor should not throw an exception");
    }
  }

  @Test
  @DisplayName("Positive test for the roll method of Die")
  void roll() {
    // Arrange
    int initialValue = player.getPosition();

    // Act
    die.roll();

    // Assert
    assertTrue(player.getPosition() > initialValue);
  }

  @Test
  @DisplayName("Tests the observer pattern of the Die class")
  void testObserverPattern() {
    LadderGamePlayer newPlayer = new LadderGamePlayer("testPlayer2",
        PlayerPiece.PROPELLER_ACCESSORIES);
    // Test add
    assertDoesNotThrow(() -> die.addObserver(newPlayer));
    assertTrue(die.getObservers().contains(newPlayer));

    // Test remove
    assertDoesNotThrow(() -> die.removeObserver(newPlayer));
    assertFalse(die.getObservers().contains(newPlayer));

    // Test notify
    // Arrange
    int playerStartingPosition = player.getPosition();
    int[] expected = {1};

    // Act
    die.notifyObservers(expected);

    // Assert
    assertEquals(player.getPosition(), playerStartingPosition + expected[0]);

    // Test get observers
    assertEquals(new ArrayList<>(Collections.singletonList(player)), die.getObservers());
  }

  @Test
  @DisplayName("Negative test for the constructor of Die")
  void testInvalidDieCreation() {
    // Test invalid die creation
    assertThrows(IllegalArgumentException.class, () -> new Die(1));
    assertThrows(IllegalArgumentException.class, () -> new Die(0));
    assertThrows(IllegalArgumentException.class, () -> new Die(-1));
  }

  @Test
  @DisplayName("Test the negative observer pattern of Die")
  void testNegativeObserverPattern() {
    // Test negative observer pattern
    assertThrows(NullPointerException.class, () -> die.addObserver(null));

    assertThrows(NullPointerException.class, () -> die.removeObserver(null));
    assertThrows(IllegalArgumentException.class, () ->
        die.removeObserver(new LadderGamePlayer("testPlayer2", PlayerPiece.PROPELLER_ACCESSORIES)));

    assertThrows(NullPointerException.class, () -> die.notifyObservers(null));
    assertThrows(IllegalArgumentException.class, () -> die.notifyObservers(new int[]{}));
    assertThrows(IllegalArgumentException.class, () -> die.notifyObservers(new int[]{-1}));
    assertThrows(IllegalArgumentException.class, () -> die.notifyObservers(new int[]{0}));
  }
}