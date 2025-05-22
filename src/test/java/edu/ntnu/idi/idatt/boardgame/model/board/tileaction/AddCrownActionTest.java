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

class AddCrownActionTest {

  private Player player;
  private TileAction addCrownAction;

  @BeforeEach
  void setUp() {
    player = new ParioMartyPlayer("Martinelli goal vs Chelsea 21.01.2020",
        PlayerPiece.MARIOTINELLI);
    addCrownAction = new AddCrownAction();
  }

  @Test
  @DisplayName("Test the constructor of AddCrownAction, and check for exceptions")
  public void testConstructor() {
    try {
      new AddCrownAction();
    } catch (Exception e) {
      fail("Constructor should not throw an exception");
    }
  }

  @Test
  @DisplayName("Positive tests for performAction method of AddCrownAction")
  public void testPerformAction() {

    try {
      int initialCrowns = ((ParioMartyPlayer) player).getCrowns();
      addCrownAction.performAction(player);
      int finalCrowns = ((ParioMartyPlayer) player).getCrowns();
      assertEquals(initialCrowns + 1, finalCrowns);
    } catch (ClassCastException e) {
      fail("performAction should not throw an exception for a valid player");
    }
  }

  @Test
  @DisplayName("Negative tests for performAction method of AddCrownAction")
  public void negativeTestPerformAction() {
    assertThrows(NullPointerException.class, () -> addCrownAction.performAction(null));

    Player invalidPlayer = new LadderGamePlayer("Kante on the floor", PlayerPiece.EVIL_PAUL);
    assertThrows(InvalidPlayerException.class, () -> addCrownAction.performAction(invalidPlayer));
  }


}