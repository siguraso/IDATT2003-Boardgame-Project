package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile action that adds coins to a player in the ParioMarty game. The performAction
 * method adds 5 coins to the player's coin count, and can only be performed by a player of type
 * {@link ParioMartyPlayer}.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class AddCoinsAction implements TileAction {

  int coinsToAdd;

  /**
   * Constructor for the AddCoinsAction class.
   *
   * @param coinsToAdd the amount of coins to add to the player when performing the action
   */
  public AddCoinsAction(int coinsToAdd) {
    if (coinsToAdd < 0) {
      throw new IllegalArgumentException("Coins cannot be negative");
    }

    this.coinsToAdd = coinsToAdd;
  }

  @Override
  public void performAction(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }
    try {
      ((ParioMartyPlayer) player).addCoins(coinsToAdd);
    } catch (ClassCastException e) {
      throw new InvalidPlayerException("Player is not of type ParioMartyPlayer");
    }
  }
}
