package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemoveCoinsTileTest {
  private RemoveCoinsTile removeCoinsTile;

  @BeforeEach
  void setUp() {
    removeCoinsTile = new RemoveCoinsTile(1, new int[]{0, 0}, 10);
  }

  @AfterEach
  void tearDown() {
    removeCoinsTile = null;
  }

  @Test
  void getTileNumber() {
    assertEquals(1, removeCoinsTile.getTileNumber());
  }

  @Test
  void getOnscreenPosition() {
    assertArrayEquals(new int[]{0, 0}, removeCoinsTile.getOnscreenPosition());
  }

  @Test
  void getTileType() {
    assertEquals("RemoveCoinsTile", removeCoinsTile.getTileType());
  }

  @Test
  void performAction() {
    //player starts with 10 coins
    ParioMartyPlayer player = new ParioMartyPlayer("TestPlayer", PlayerPiece.PAUL);
    removeCoinsTile.performAction(player);
    assertEquals(0, player.getCoins());
  }

  @Test
  void testNegativePerformAction() {
    assertThrows(NullPointerException.class, () -> removeCoinsTile.performAction(null));
    assertThrows(InvalidPlayerException.class, () -> removeCoinsTile.performAction(
        new LadderGamePlayer("TestPlayer", PlayerPiece.PAUL)));
  }

  @Test
  void constructorNegative() {
    final List<int[]> testList = new ArrayList<>(Arrays.asList(
        null,
        new int[] {-1, 0},
        new int[] {}
    ));

    for (int i = 0; i < testList.size() + 1; i++) {
      if (i >= testList.size()) {
        assertThrows(IllegalArgumentException.class, () ->
            new AddCrownTile(-1, new int[] {0, 0}));
        continue;
      }
      int finalI = i;
      assertThrows(IllegalArgumentException.class, () ->
          new AddCrownTile(1, testList.get(finalI)));
    }
  }
}