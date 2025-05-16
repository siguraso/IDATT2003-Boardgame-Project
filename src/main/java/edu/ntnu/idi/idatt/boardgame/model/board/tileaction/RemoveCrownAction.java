package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile action that removes a crown to a player in the ParioMarty game. The performAction
 * method removes a crown to the player's crown count, and can only be performed by a player of type
 * {@link ParioMartyPlayer}.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class RemoveCrownAction implements TileAction {

  /**
   * Constructor for the RemoveCrownAction class.
   */
  public RemoveCrownAction() {
  }

  @Override
  public void performAction(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    } else if (((ParioMartyPlayer) player).getCrowns() == 0) {
      throw new IllegalArgumentException("Cannot remove crown from player with 0 crowns");
    }

    try {
      ((ParioMartyPlayer) player).removeCrowns(1);
    } catch (ClassCastException e) {
      throw new ClassCastException("Player is not of type ParioMartyPlayer");
    }
  }
}
