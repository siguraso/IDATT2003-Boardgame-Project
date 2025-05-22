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
import org.junit.jupiter.api.Test;

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
    moveToRandomTileAction = new MoveToRandomTileAction(board);
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
  void negativePerformAction() {
    assertThrows(NullPointerException.class, () -> {
      moveToRandomTileAction = new MoveToRandomTileAction(null);
      moveToRandomTileAction.performAction(player);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      moveToRandomTileAction = new MoveToRandomTileAction(new Board(new HashMap<>()));
      moveToRandomTileAction.performAction(player);
    });
    assertThrows(NullPointerException.class, () -> {
      moveToRandomTileAction = new MoveToRandomTileAction(board);
      moveToRandomTileAction.performAction(null);
    });
  }
}