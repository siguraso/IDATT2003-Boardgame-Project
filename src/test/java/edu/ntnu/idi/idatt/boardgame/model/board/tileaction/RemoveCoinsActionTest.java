package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RemoveCoinsActionTest {

  private Player player;
  private TileAction removeCoinsAction;

  @BeforeEach
  void setUp() {
    player = new ParioMartyPlayer("you ever cooked a half a brick in the air fryer?",
        PlayerPiece.EVIL_PAUL);
    removeCoinsAction = new RemoveCoinsAction(5);
  }

  @Test
  @DisplayName("Test the constructor of RemoveCoinsAction, and check for exceptions")
  public void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new RemoveCoinsAction(-1));

    try {
      new RemoveCoinsAction(5);
    } catch (Exception e) {
      fail("Constructor should not throw an exception");
    }
  }

  @Test
  @DisplayName("Test the performAction method of RemoveCoinsAction, and check for exceptions")
  public void testPerformAction() {
    assertThrows(NullPointerException.class, () -> removeCoinsAction.performAction(null));

    Player invalidPlayer = new LadderGamePlayer("king von", PlayerPiece.EVIL_PAUL);
    assertThrows(InvalidPlayerException.class,
        () -> removeCoinsAction.performAction(invalidPlayer));

    try {
      int initialCoins = ((ParioMartyPlayer) player).getCoins();
      removeCoinsAction.performAction(player);
      int finalCoins = ((ParioMartyPlayer) player).getCoins();
      assertEquals(initialCoins - 5, finalCoins);
    } catch (ClassCastException e) {
      fail("performAction should not throw an exception for a valid player");
    }
  }

  @Test
  @DisplayName("Positive test the setCoinsToRemove method of RemoveCoinsAction")
  public void testSetCoinsToRemove() {
    try {
      ((RemoveCoinsAction) removeCoinsAction).setCoinsToRemove(3);
      int initialCoins = ((ParioMartyPlayer) player).getCoins();
      removeCoinsAction.performAction(player);
      int finalCoins = ((ParioMartyPlayer) player).getCoins();
      assertEquals(initialCoins - 3, finalCoins);
    } catch (Exception e) {
      fail("setCoinsToRemove should not throw an exception");
    }

    // test if it sets to 0 if the player has less coins than the amount to remove
    try {
      ((RemoveCoinsAction) removeCoinsAction).setCoinsToRemove(100);
      removeCoinsAction.performAction(player);
      int finalCoins = ((ParioMartyPlayer) player).getCoins();
      assertEquals(0, finalCoins);
    } catch (Exception e) {
      fail("setCoinsToRemove should not throw an exception");
    }
  }

  @Test
  @DisplayName("Negative test the setCoinsToRemove method of RemoveCoinsAction")
  public void testNegativeSetCoinsToRemove() {
    assertThrows(IllegalArgumentException.class,
        () -> ((RemoveCoinsAction) removeCoinsAction).setCoinsToRemove(-2));
  }

}