package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special action that returns a player to the starting tile on the board.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class ReturnToStartAction implements TileAction {

  /**
   * Constructor for the ReturnToStartAction class.
   */
  public ReturnToStartAction() {
  }

  @Override
  public void performAction(Player player) {
    player.moveTo(1);
  }
}
