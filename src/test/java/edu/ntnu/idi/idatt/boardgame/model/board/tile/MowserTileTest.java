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

class MowserTileTest {
  private MowserTile mowserTile;


  @BeforeEach
  void setUp() {
    mowserTile = new MowserTile(5, new int[]{0, 0});
  }

  @AfterEach
  void tearDown() {
    mowserTile = null;
  }

  @Test
  void getTileNumber() {
    assertEquals(5, mowserTile.getTileNumber());
  }

  @Test
  void getOnscreenPosition() {
    assertArrayEquals(new int[]{0, 0}, mowserTile.getOnscreenPosition());
  }

  @Test
  void getTileType() {
    assertEquals("MowserTile", mowserTile.getTileType());
  }

  @Test
  void getTileAction() {
    mowserTile.performAction(new ParioMartyPlayer("s", PlayerPiece.PAUL));

    int tileAction = mowserTile.getTileAction();

    assertTrue(tileAction < 4 && tileAction >= 0);
  }

  @Test
  void performAction() {
    ParioMartyPlayer player = new ParioMartyPlayer("TestPlayer", PlayerPiece.PAUL);
    player.addCoins(20); // Add coins to the player
    player.addCrowns(1);

    mowserTile.performAction(player);

    int tileAction = mowserTile.getTileAction();

    switch (tileAction) {
      case 0 -> assertEquals(10, player.getCoins());
      case 1 -> assertEquals(30, player.getCoins());
      case 2 -> assertEquals(0, player.getCrowns());
      case 3 -> assertEquals(1, player.getPosition());
      default -> fail("Invalid tile action");
    }
  }

  @Test
  void setPlayerCoins() {
    ParioMartyPlayer player = new ParioMartyPlayer("TestPlayer", PlayerPiece.PAUL);
    player.addCoins(20); // Add coins to the player

    mowserTile.setPlayerCoins(player);

    assertEquals(30, player.getCoins());

    player.addCoins(20);

    mowserTile.setPlayerCoins(player);

    assertEquals(50, player.getCoins());
  }

  @Test
  void negativeSetPlayerCoins() {
    assertThrows(NullPointerException.class, () -> mowserTile.setPlayerCoins(null));
    assertThrows(InvalidPlayerException.class, () -> mowserTile.setPlayerCoins(
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