package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardFactory;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MoveToRandomTileActionTest {

  private HashMap<Integer, Tile> tiles;
  private Board board;
  private MoveToRandomTileAction moveToRandomTileAction;
  private LadderGamePlayer player;

  @BeforeEach
  void setUp() {
    //Arrange
    tiles = (HashMap<Integer, Tile>) BoardFactory
        .createBoard(BoardType.LADDER_GAME_SPECIAL, false, null).tiles();
    board = new Board(tiles);
    moveToRandomTileAction = new MoveToRandomTileAction(BoardType.LADDER_GAME_SPECIAL);
    player = new LadderGamePlayer("TestPlayer", PlayerPiece.MARIOTINELLI);
  }

  @AfterEach
  void tearDown() {
    // Reset
    tiles = null;
    board = null;
    moveToRandomTileAction = null;
    player = null;
  }

  @Test
  @DisplayName("Test the constructor of MoveToRandomTileAction, and check for exceptions")
  void testConstructor() {
    try {
      new MoveToRandomTileAction(BoardType.LADDER_GAME_SPECIAL);
    } catch (IllegalArgumentException e) {
      fail("Constructor should not throw an exception");
    }
  }

  @Test
  @DisplayName("Positive tests for the PerformAction method of MoveToRandomTileAction")
  void performAction() {
    // Arrange
    int initialPosition = player.getPosition();

    // Act
    moveToRandomTileAction.performAction(player);

    // Assert
    assertTrue(player.getPosition() >= 1 && player.getPosition() <= 89);
    assertNotEquals(player.getPosition(), initialPosition);
  }

  @Test
  @DisplayName("Negative tests for all methods of MoveToRandomTileAction")
  void negativeTestRandomTileAction() {
    assertThrows(NullPointerException.class, () -> {
      moveToRandomTileAction = new MoveToRandomTileAction(null);
      moveToRandomTileAction.performAction(player);
    });

    assertThrows(NullPointerException.class, () -> {
      moveToRandomTileAction = new MoveToRandomTileAction(BoardType.LADDER_GAME_SPECIAL);
      moveToRandomTileAction.performAction(null);
    });
  }
}