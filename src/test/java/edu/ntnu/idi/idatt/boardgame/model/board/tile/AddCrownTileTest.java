package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddCrownTileTest {
  private AddCrownTile addCrownTile;

  @BeforeEach
  void setUp() {
    addCrownTile = new AddCrownTile(1, new int[]{0, 0});
  }

  @AfterEach
  void tearDown() {
    addCrownTile = null;
  }

  @Test
  void getTileNumber() {
    assertEquals(1, addCrownTile.getTileNumber());
  }

  @Test
  void getOnscreenPosition() {
    assertArrayEquals(new int[]{0, 0}, addCrownTile.getOnscreenPosition());
  }

  @Test
  void getTileType() {
    assertEquals("AddCrownTile", addCrownTile.getTileType());
  }

  @Test
  void performAction() {
    ParioMartyPlayer player = new ParioMartyPlayer("TestPlayer", PlayerPiece.PAUL);
    player.addCoins(30); // Add coins to the player
    addCrownTile.performAction(player);
    assertEquals(1, player.getCrowns());
  }

  @Test
  void performActionWithInsufficientCoins() {
    ParioMartyPlayer player = new ParioMartyPlayer("TestPlayer", PlayerPiece.PAUL);
    player.removeCoins(10);
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> addCrownTile.performAction(player));
    assertEquals("Player does not have 10 or more coins.", exception.getMessage());
  }

  @Test
  void performActionWithNullPlayer() {
    Exception exception = assertThrows(NullPointerException.class,
        () -> addCrownTile.performAction(null));
    assertEquals("Player cannot be null", exception.getMessage());
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
        assertThrows(IllegalArgumentException.class, () -> new AddCrownTile(-1, new int[] {0, 0}));
        continue;
      }
      int finalI = i;
      assertThrows(IllegalArgumentException.class, () -> new AddCrownTile(1, testList.get(finalI)));
    }
  }
}