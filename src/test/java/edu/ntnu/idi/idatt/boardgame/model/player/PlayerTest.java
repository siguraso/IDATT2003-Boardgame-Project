package edu.ntnu.idi.idatt.boardgame.model.player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
  private Player player;

  @BeforeEach
  void setUp() {
    player = new Player("TestPlayer", PlayerPiece.PAUL);
  }

  @AfterEach
  void tearDown() {
    player = null;
  }

  @Test
  void getName() {
    assertEquals("TestPlayer", player.getName());
  }

  @Test
  void getPosition() {
    assertEquals(1, player.getPosition());
  }

  @Test
  void isWinner() {
    assertFalse(player.isWinner());
    player.setWinner();
    assertTrue(player.isWinner());
  }

  @Test
  void moveForward() {
    player.moveForward(5);
    assertEquals(6, player.getPosition());
  }

  @Test
  void moveTo() {
    player.moveTo(10);
    assertEquals(10, player.getPosition());
  }

  @Test
  void setWinner() {
    player.setWinner();
    assertTrue(player.isWinner());
  }

  @Test
  void getPlayerPiece() {
    assertEquals(PlayerPiece.PAUL, player.getPlayerPiece());
  }

  @Test
  void canRollAgain() {
    assertFalse(player.canRollAgain());
  }

  @Test
  void setRollAgain() {
    player.setRollAgain(true);
    assertTrue(player.canRollAgain());
  }

  @Test
  void update() {
    int initialPosition = player.getPosition();
    int[] input = {5};
    player.update(input);
    assertEquals(initialPosition + input[0], player.getPosition());
  }

  @Test
  void updateNegative() {
    assertThrows(NullPointerException.class, () -> player.update(null));
    assertThrows(IllegalArgumentException.class, () -> player.update(new int[]{}));
    assertThrows(IllegalArgumentException.class, () -> player.update(new int[]{1, 2}));
    assertThrows(IllegalArgumentException.class, () -> player.update(new int[]{-1}));
    assertThrows(IllegalArgumentException.class, () -> player.update(new int[]{0}));
  }

    @Test
  void testMoveToInvalidTileNumber() {
    assertThrows(IllegalArgumentException.class, () -> player.moveTo(-1));
    assertThrows(IllegalArgumentException.class, () -> player.moveTo(91));
  }

  @Test
  void testConstructorNegative() {
    assertThrows(NullPointerException.class, () -> new Player(null, PlayerPiece.PAUL));
    assertThrows(IllegalArgumentException.class, () -> new Player("", PlayerPiece.PAUL));
    assertThrows(NullPointerException.class, () -> new Player("TestPlayer", null));
  }
}