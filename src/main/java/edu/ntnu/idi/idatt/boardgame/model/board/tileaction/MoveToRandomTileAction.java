package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;


import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * Interface for the special actions that can be performed on a special tile.
 *
 * @author siguraso & MagnusNaessanGaarder
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
    if (board == null) {
      throw new NullPointerException("Board cannot be null");
    } else if (board.tiles().isEmpty()) {
      throw new IllegalArgumentException("Board cannot be empty");
    } else {
      this.board = board;
    }
  }

  @Override
  public void performAction(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }

    int newPosition = (int) (Math.random() * (board.tiles().size() - 1)) + 1;

    // if the new position is 0 or 90, move to 1 to avoid landing on the end tile or on tile 0
    // which is not a valid tile
    newPosition = newPosition == 90 ? 89 : newPosition;

    player.moveTo(newPosition);
  }
}
