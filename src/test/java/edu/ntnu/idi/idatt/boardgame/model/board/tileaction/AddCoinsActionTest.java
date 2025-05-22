package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddCoinsActionTest {

  private Player player;
  private TileAction addCoinsAction;

  @BeforeEach
  void setUp() {
    player = new ParioMartyPlayer("YEAH YEAH 60 DAYSSS", PlayerPiece.EVIL_PAUL);
    addCoinsAction = new AddCoinsAction(5);
  }

  @Test
  @DisplayName("Test the constructor of AddCoinsAction, and check for exceptions")
  public void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new AddCoinsAction(-1));

    try {
      new AddCoinsAction(5);
    } catch (IllegalArgumentException e) {
      fail("Constructor should not throw an exception for positive coins");
    }
  }

  @Test
  @DisplayName("Positive test for performAction method of AddCoinsAction")
  public void testPerformAction() {
    try {
      int initialCoins = ((ParioMartyPlayer) player).getCoins();
      addCoinsAction.performAction(player);
      int finalCoins = ((ParioMartyPlayer) player).getCoins();
      assertEquals(initialCoins + 5, finalCoins);
    } catch (InvalidPlayerException e) {
      fail("performAction should not throw an exception for a valid player");
    }
  }

  @Test
  @DisplayName("Negative test for performAction method of AddCoinsAction")
  public void negativeTestPerformAction() {
    assertThrows(NullPointerException.class, () -> addCoinsAction.performAction(null));

    Player invalidPlayer = new LadderGamePlayer("Invalid Player", PlayerPiece.EVIL_PAUL);
    assertThrows(InvalidPlayerException.class, () -> addCoinsAction.performAction(invalidPlayer));
    ;
  }
}