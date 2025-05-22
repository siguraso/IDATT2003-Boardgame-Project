package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddCoinsTileTest {

  private AddCoinsTile addCoinsTile;

  @BeforeEach
  void setUp() {
    addCoinsTile = new AddCoinsTile(1, new int[]{0, 0}, 10);
  }

  @AfterEach
  void tearDown() {
    addCoinsTile = null;
  }

  @Test
  void getTileNumber() {
    assertEquals(1, addCoinsTile.getTileNumber());
  }

  @Test
  void getOnscreenPosition() {
    assertArrayEquals(new int[]{0, 0}, addCoinsTile.getOnscreenPosition());
  }

  @Test
  void getTileType() {
    assertEquals("AddCoinsTile", addCoinsTile.getTileType());
  }

  @Test
  void performAction() {
    ParioMartyPlayer player = new ParioMartyPlayer("TestPlayer", PlayerPiece.PAUL);
    addCoinsTile.performAction(player);
    assertEquals(20, player.getCoins());
  }

  @Test
  void performActionWithNegativeCoins() {
    try {
      addCoinsTile = new AddCoinsTile(1, new int[]{0, 0}, -10);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Coins cannot be negative", e.getMessage());
    }
  }

  @Test
  void performActionNegative() {
    assertThrows(NullPointerException.class, () -> addCoinsTile.performAction(null));
    assertThrows(InvalidPlayerException.class, () -> addCoinsTile.performAction(
        new LadderGamePlayer("test", PlayerPiece.PAUL)));
  }

  @Test
  void constructorNegative() {
    final List<int[]> testList = new ArrayList<>(Arrays.asList(
        null,
        new int[]{-1, 0},
        new int[]{}
    ));

    for (int i = 0; i < testList.size() + 1; i++) {
      if (i >= testList.size()) {
        assertThrows(IllegalArgumentException.class, () ->
            new AddCrownTile(-1, new int[]{0, 0}));
        continue;
      }
      int finalI = i;
      assertThrows(IllegalArgumentException.class, () ->
          new AddCrownTile(1, testList.get(finalI)));
    }
  }
}