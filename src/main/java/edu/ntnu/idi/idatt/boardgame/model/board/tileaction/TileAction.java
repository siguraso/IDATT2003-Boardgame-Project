package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * Interface for the special actions that can be performed on a special tile.
 */
public interface TileAction {

  /**
   * Performs the special tile action on a player.
   */
  void performAction(Player player);
}
