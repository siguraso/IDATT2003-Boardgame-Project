package edu.ntnu.idi.idatt.boardgame.model.player;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LadderGamePlayerTest {

  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.PAUL);
  }

  @AfterEach
  void tearDown() {
    player = null;
  }

  @Test
  @DisplayName("test the get name method")
  void getName() {
    assertEquals("TestPlayer", player.getName());
  }

  @Test
  @DisplayName("test the get position method")
  void getPosition() {
    assertEquals(1, player.getPosition());
  }

  @Test
  @DisplayName("test the is winner method")
  void isWinner() {
    assertFalse(player.isWinner());
    player.setWinner();
    assertTrue(player.isWinner());
  }

  @Test
  @DisplayName("test the set move forward method")
  void moveForward() {
    player.moveForward(5);
    assertEquals(6, player.getPosition());
  }

  @Test
  @DisplayName("test the set move to method")
  void moveTo() {
    player.moveTo(10);
    assertEquals(10, player.getPosition());
  }

  @Test
  @DisplayName("test the set winner to method")
  void setWinner() {
    player.setWinner();
    assertTrue(player.isWinner());
  }

  @Test
  @DisplayName("test the get player piece method")
  void getPlayerPiece() {
    assertEquals(PlayerPiece.PAUL, player.getPlayerPiece());
  }

  @Test
  @DisplayName("test the canRollAgain method")
  void canRollAgain() {
    assertFalse(player.canRollAgain());
  }

  @Test
  @DisplayName("test the setRollAgain method")
  void setRollAgain() {
    player.setRollAgain(true);
    assertTrue(player.canRollAgain());
  }

  @Test
  @DisplayName("test the update method")
  void update() {
    int initialPosition = player.getPosition();
    int[] input = {5};
    player.update(input);
    assertEquals(initialPosition + input[0], player.getPosition());
  }

  @Test
  @DisplayName("test the handleLadderAction method")
  void testHandleLadderAction() {
    // Test the handleAction method
    player.handleLadderAction(5);
    assertEquals(5, player.getPosition());
  }

  @Test
  @DisplayName("negative test for the update method")
  void updateNegative() {
    assertThrows(NullPointerException.class, () -> player.update(null));
    assertThrows(IllegalArgumentException.class, () -> player.update(new int[]{}));
    assertThrows(IllegalArgumentException.class, () -> player.update(new int[]{-1}));
    assertThrows(IllegalArgumentException.class, () -> player.update(new int[]{0}));
  }

  @Test
  @DisplayName("negative test for the moveTo method")
  void testMoveToInvalidTileNumber() {
    assertThrows(IllegalArgumentException.class, () -> player.moveTo(-1));
    assertThrows(IllegalArgumentException.class, () -> player.moveTo(91));
  }

  @Test
  @DisplayName("test the constructor of LadderGamePlayer, and check for exceptions")
  void testConstructorNegative() {
    assertThrows(NullPointerException.class, () -> new LadderGamePlayer(null, PlayerPiece.PAUL));
    assertThrows(IllegalArgumentException.class, () -> new LadderGamePlayer("", PlayerPiece.PAUL));
    assertThrows(NullPointerException.class, () -> new LadderGamePlayer("TestPlayer", null));
  }
}