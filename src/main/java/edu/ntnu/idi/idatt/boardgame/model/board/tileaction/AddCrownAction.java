package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile action that adds a crown to a player in the ParioMarty game. The performAction
 * method adds a crown to the player's crown count, and can only be performed by a player of type
 * {@link ParioMartyPlayer}.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class AddCrownAction implements TileAction {

  /**
   * Constructor for the AddCrownAction class.
   */
  public AddCrownAction() {
  }


  @Override
  public void performAction(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    try {
      ((ParioMartyPlayer) player).addCrowns(1);
    } catch (ClassCastException e) {
      throw new InvalidPlayerException("Player is not of type ParioMartyPlayer");
    }
  }

}
