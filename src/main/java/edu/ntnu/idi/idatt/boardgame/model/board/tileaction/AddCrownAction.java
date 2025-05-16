package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;

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
  public void performAction(edu.ntnu.idi.idatt.boardgame.model.player.Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    try {
      ((ParioMartyPlayer) player).addCrowns(1);
    } catch (ClassCastException e) {
      throw new ClassCastException("Player is not of type ParioMartyPlayer");
    }
  }

}
