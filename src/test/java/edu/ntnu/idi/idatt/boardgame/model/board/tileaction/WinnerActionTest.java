package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WinnerActionTest {

  private WinnerAction winnerAction;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    winnerAction = new WinnerAction();
    player = new LadderGamePlayer("Player 1", PlayerPiece.PAUL);
  }

  @AfterEach
  void tearDown() {
    winnerAction = null;
    player = null;
  }

  @Test
  void performAction() {
    // Act
    winnerAction.performAction(player);

    // Assert
    assertTrue(player.isWinner());
  }

  @Test
  void performActionWithNullPlayer() {
    assertThrows(NullPointerException.class, () -> winnerAction.performAction(null));
  }
}