package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * Interface for the special actions that can be performed on a special tile.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public class WinnerAction implements TileAction {

  /**
   * Constructor for the WinnerAction class.
   */
  public WinnerAction() {
  }

  @Override
  public void performAction(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null.");
    }
    player.setWinner();
  }

}
