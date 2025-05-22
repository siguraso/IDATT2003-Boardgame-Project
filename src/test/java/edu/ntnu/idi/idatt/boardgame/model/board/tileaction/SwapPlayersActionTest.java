package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SwapPlayersActionTest {

  private Player player1;
  private Player player2;
  private SwapPlayersAction swapPlayersAction;

  @BeforeEach
  void setUp() {
    player1 = new LadderGamePlayer("LadderGamePlayer 1", PlayerPiece.MY_LOVE);
    player2 = new LadderGamePlayer("LadderGamePlayer 2", PlayerPiece.KONKEY_DONG);
    swapPlayersAction = new SwapPlayersAction();
  }

  @AfterEach
  void tearDown() {
    player1 = null;
    player2 = null;
    swapPlayersAction = null;
  }

  @Test
  @DisplayName("test the constructor of SwapPlayersAction")
  void testConstructor() {
    try {
      new SwapPlayersAction();
    } catch (Exception e) {
      fail("Constructor should not throw an exception");
    }
  }

  @Test
  @DisplayName("test the setPlayers method of SwapPlayersAction")
  void setSwapPlayersActionSetPlayers() {
    // Arrange
    ArrayList<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);

    // Act
    swapPlayersAction.setPlayers(players);

    // Assert
    assertTrue(swapPlayersAction.getPlayers().contains(player1));
    assertTrue(swapPlayersAction.getPlayers().contains(player2));
  }

  @Test
  @DisplayName("negative test of the setPlayers method of SwapPlayersAction")
  void negativeSetPlayers() {
    ArrayList<Player> players = new ArrayList<>();
    players.add(player1);

    assertThrows(IllegalArgumentException.class, () -> swapPlayersAction.setPlayers(players));
    assertThrows(NullPointerException.class, () -> swapPlayersAction.setPlayers(null));
  }

  @Test
  @DisplayName("test the getPlayerToSwapWith method of SwapPlayersAction")
  void getPlayerToSwapWithTwoPlayers() {
    // Arrange
    ArrayList<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    swapPlayersAction.setPlayers(players);

    // Act
    swapPlayersAction.performAction(player1);

    // Assert
    assertEquals(player2, swapPlayersAction.getPlayerToSwapWith());
  }

  @Test
  void getPlayerToSwapWithSeveralPlayers() {
    // Arrange
    ArrayList<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(new LadderGamePlayer("LadderGamePlayer 3", PlayerPiece.MY_LOVE_WITH_HAT));
    players.add(new LadderGamePlayer("LadderGamePlayer 4", PlayerPiece.EVIL_PAUL));
    swapPlayersAction.setPlayers(players);

    // Act
    swapPlayersAction.performAction(player1);
    players.remove(player1);

    // Assert
    assertTrue(players.contains(swapPlayersAction.getPlayerToSwapWith()));
  }

  @Test
  void negativeGetPlayerToSwapWith() {
    // Arrange
    ArrayList<Player> players = new ArrayList<>();
    players.add(player1);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
      swapPlayersAction.setPlayers(players);
      swapPlayersAction.performAction(player1);
      swapPlayersAction.getPlayerToSwapWith();
    });
    assertThrows(NullPointerException.class, () -> {
      swapPlayersAction.setPlayers(null);
      swapPlayersAction.performAction(player1);
      swapPlayersAction.getPlayerToSwapWith();
    });
    assertThrows(IllegalStateException.class, () -> swapPlayersAction.getPlayerToSwapWith());
  }

  @Test
  void performAction() {
    // Arrange
    ArrayList<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    swapPlayersAction.setPlayers(players);

    // Act
    swapPlayersAction.performAction(player1);

    // Assert
    assertEquals(player2, swapPlayersAction.getPlayerToSwapWith());
  }

  @Test
  void negativePerformAction() {
    // Arrange
    ArrayList<Player> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(new LadderGamePlayer("LadderGamePlayer 3", PlayerPiece.MY_LOVE_WITH_HAT));
    players.add(new LadderGamePlayer("LadderGamePlayer 4", PlayerPiece.EVIL_PAUL));

    // Act
    swapPlayersAction.setPlayers(players);

    // Assert
    assertThrows(NullPointerException.class, () -> swapPlayersAction.performAction(null));
  }
}