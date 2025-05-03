package edu.ntnu.idi.idatt.boardgame.model.dice;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DieTest {
  private Die die;
  private Player player;

  @BeforeEach
  void setUp() {
    // Arrange
    die = new Die(6);
    player = new Player("TestPlayer", PlayerPiece.LOCKED_IN_SNOWMAN);
    die.addObserver(player);
  }

  @AfterEach
  void tearDown() {
    // Reset
    die = null;
  }

  @Test
  void roll() {
    // Arrange
    int initialValue = player.getPosition();

    // Act
    die.roll();

    // Assert
    assertTrue(player.getPosition() > initialValue);
  }

  @Test
  void testObserverPattern() {
    Player newPlayer = new Player("testPlayer2", PlayerPiece.PROPELLER_ACCESSORIES);
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
  void testInvalidDieCreation() {
    // Test invalid die creation
    assertThrows(IllegalArgumentException.class, () -> new Die(1));
    assertThrows(IllegalArgumentException.class, () -> new Die(0));
    assertThrows(IllegalArgumentException.class, () -> new Die(-1));
  }

  @Test
  void testNegativeObserverPattern() {
    // Test negative observer pattern
    assertThrows(NullPointerException.class, () -> die.addObserver(null));

    assertThrows(NullPointerException.class, () -> die.removeObserver(null));
    assertThrows(IllegalArgumentException.class, () ->
        die.removeObserver(new Player("testPlayer2", PlayerPiece.PROPELLER_ACCESSORIES)));

    assertThrows(NullPointerException.class, () -> die.notifyObservers(null));
    assertThrows(IllegalArgumentException.class, () -> die.notifyObservers(new int[]{}));
    assertThrows(IllegalArgumentException.class, () -> die.notifyObservers(new int[]{-1}));
    assertThrows(IllegalArgumentException.class, () -> die.notifyObservers(new int[]{0}));
    assertThrows(IllegalArgumentException.class, () -> die.notifyObservers(new int[]{1, -1}));
  }
}