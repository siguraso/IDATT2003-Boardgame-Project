package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;


import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.Random;

/**
 * Interface for the special actions that can be performed on a special tile.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public class MoveToRandomTileAction implements TileAction {

  private final Random random = new Random();
  private final BoardType boardType;

  /**
   * Constructor for the MoveToRandomTileAction class.
   *
   * @param boardType the type of the board. This is used to determine the number of tiles on the
   *                  board.
   */
  public MoveToRandomTileAction(BoardType boardType) {
    if (boardType == null) {
      throw new NullPointerException("Board type cannot be null");
    }
    this.boardType = boardType;
  }

  @Override
  public void performAction(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }

    int newPosition;

    if (boardType != BoardType.PARIO_MARTY) {
      newPosition = random.nextInt(1, 90);

      // if the new position is 0 or 90, move to 1 to avoid landing on the end tile or on tile 0
      // which is not a valid tile
      newPosition = newPosition == 90 ? 89 : newPosition;
    } else {
      // there are only 35 tiles on the ParioMarty board
      newPosition = random.nextInt(1, 36);
    }

    player.moveTo(newPosition);
  }
}
