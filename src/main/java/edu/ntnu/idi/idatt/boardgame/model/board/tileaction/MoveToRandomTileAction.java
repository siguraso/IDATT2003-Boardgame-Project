package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;


import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.HashMap;

/**
 * Interface for the special actions that can be performed on a special tile.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class MoveToRandomTileAction implements TileAction {

  private final Board board;

  /**
   * Constructor for the MoveToRandomTileAction class.
   *
   * @param board HashMap containing all the tiles on the board.
   */
  public MoveToRandomTileAction(Board board) {
    this.board = board;
  }

  @Override
  public void performAction(Player player) {
    int newPosition = (int) (Math.random() * board.getTiles().size());
    Tile boardTile = board.getTiles().get(newPosition);

    if (!boardTile.getTileType().equals(TileType.NORMAL.getTileType())) {
      throw new IllegalArgumentException(
          "Player cannot be moved to a special tile using this action.");
    }

    player.moveTo(newPosition);
  }


}
