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

class RemoveCrownActionTest {


  private Player player;
  private TileAction removeCrownAction;

  @BeforeEach
  void setUp() {
    player = new ParioMartyPlayer("THEY OFTEN SEE WHAT YOU CANT SEEEEEEEE",
        PlayerPiece.EVIL_PAUL);
    removeCrownAction = new RemoveCrownAction();
  }

  @Test
  @DisplayName("Test the constructor of RemoveCrownAction, and check for exceptions")
  public void testConstructor() {
    try {
      new RemoveCrownAction();
    } catch (Exception e) {
      fail("Constructor should not throw an exception");
    }
  }

  @Test
  @DisplayName("Positive test the performAction method of RemoveCrownAction")
  public void testPerformAction() {
    try {
      // test if it removes a crown when it shouldnt
      assertEquals(0, ((ParioMartyPlayer) player).getCrowns());
      removeCrownAction.performAction(player);
      assertEquals(0, ((ParioMartyPlayer) player).getCrowns());

      ((ParioMartyPlayer) player).addCrowns(2);
      int initialCrowns = ((ParioMartyPlayer) player).getCrowns();
      removeCrownAction.performAction(player);
      int finalCrowns = ((ParioMartyPlayer) player).getCrowns();
      assertEquals(initialCrowns - 1, finalCrowns);

    } catch (ClassCastException e) {
      fail("performAction should not throw an exception for a valid player");
    }
  }

  @Test
  @DisplayName("Negative test for the performAction method of RemoveCrownAction")
  public void negativeTestPerformAction() {
    assertThrows(NullPointerException.class, () -> removeCrownAction.performAction(null));

    Player invalidPlayer = new LadderGamePlayer("spreaddie gibbs", PlayerPiece.EVIL_PAUL);
    assertThrows(InvalidPlayerException.class,
        () -> removeCrownAction.performAction(invalidPlayer));
  }
}