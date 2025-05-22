package edu.ntnu.idi.idatt.boardgame.model.player;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParioMartyPlayerTest {
  private ParioMartyPlayer player;

  @BeforeEach
  void setUp() {
    player = new ParioMartyPlayer("TestPlayer", PlayerPiece.PAUL);
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
    player.setRollAgain(true);
    assertTrue(player.canRollAgain());
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
    assertThrows(IllegalArgumentException.class, () -> player.update(new int[]{-1}));
    assertThrows(IllegalArgumentException.class, () -> player.update(new int[]{0}));
  }

  @Test
  void moveToInvalidTileNumber() {
    assertThrows(IllegalArgumentException.class, () -> player.moveTo(-1));
    assertThrows(IllegalArgumentException.class, () -> player.moveTo(36));
  }

  @Test
  void getCoins() {
    assertEquals(10, player.getCoins());
  }

  @Test
  void addCoins() {
    player.addCoins(5);
    assertEquals(15, player.getCoins());
  }

  @Test
  void addCoinsNegative() {
    assertThrows(IllegalArgumentException.class, () -> player.addCoins(-1));
  }

  @Test
  void removeCoins() {
    player.removeCoins(5);
    assertEquals(5, player.getCoins());
  }

  @Test
  void removeCoinsNegative() {
      assertThrows(IllegalArgumentException.class, () -> player.removeCoins(-1));
      assertThrows(IllegalArgumentException.class, () -> player.removeCoins(11));
  }

  @Test
  void getCrowns() {
    assertEquals(0, player.getCrowns());
  }

  @Test
  void addCrowns() {
    player.addCrowns(5);
    assertEquals(5, player.getCrowns());
  }
  @Test
  void addCrownsNegative() {
      assertThrows(IllegalArgumentException.class, () -> player.addCrowns(-1));
  }

  @Test
  void removeCrowns() {
    player.addCrowns(5);
    player.removeCrowns(3);
    assertEquals(2, player.getCrowns());
  }

  @Test
  void removeCrownsNegative() {
      assertThrows(IllegalArgumentException.class, () -> player.removeCrowns(-1));
      assertThrows(IllegalArgumentException.class, () -> player.removeCrowns(1));
  }

  @Test
  void handleLadderAction() {
    assertThrows(InvalidPlayerException.class, () -> player.handleLadderAction(1));
  }

  @Test
  void testConstructorNegative() {
    assertThrows(NullPointerException.class, () -> new ParioMartyPlayer(null, PlayerPiece.PAUL));
    assertThrows(IllegalArgumentException.class, () -> new ParioMartyPlayer("", PlayerPiece.PAUL));
    assertThrows(NullPointerException.class, () -> new ParioMartyPlayer("TestPlayer", null));
  }
}