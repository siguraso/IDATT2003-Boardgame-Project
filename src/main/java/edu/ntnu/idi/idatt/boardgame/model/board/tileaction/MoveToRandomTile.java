package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.player.Player;

/**
 * Interface for the special actions that can be performed on a special tile.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class MoveToRandomTile implements TileAction {

  Board board;

  public MoveToRandomTile(Board board) {
    this.board = board;
  }

  @Override
  public void performAction(Player player) {
    // TODO implement piss
    //player.move(board.getTiles().get((int) (Math.random() * board.getTiles().size())));
  }


}
