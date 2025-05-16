package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile action that removes coins to a player in the ParioMarty game. The performAction
 * method removes a specified amount of coins to the player's coin count, and can only be performed
 * by a player of type {@link ParioMartyPlayer}.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class RemoveCoinsAction implements TileAction {

  private int coinsToRemove;

  /**
   * Constructor for the RemoveCoinsAction class.
   *
   * @param coinsToRemove the amount of coins to remove from the player when performing the action
   */
  public RemoveCoinsAction(int coinsToRemove) {
    if (coinsToRemove < 0) {
      throw new IllegalArgumentException("Coins cannot be negative");
    }

    this.coinsToRemove = coinsToRemove;
  }

  @Override
  public void performAction(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    }

    try {
      // Set to 0 if the player has less coins than the amount to remove
      ((ParioMartyPlayer) player).removeCoins(
          Math.min(((ParioMartyPlayer) player).getCoins(), coinsToRemove));
    } catch (ClassCastException e) {
      throw new ClassCastException("Player is not of type ParioMartyPlayer");
    }
  }

  /**
   * Mutator method for the coinsToRemove field. This method sets the amount of coins to remove from
   * the player when performing the action.
   *
   * @param coinsToRemove the amount of coins to remove from the player when performing the action
   */
  public void setCoinsToRemove(int coinsToRemove) {
    if (coinsToRemove < 0) {
      throw new IllegalArgumentException("Coins cannot be negative");
    }
    this.coinsToRemove = coinsToRemove;
  }
}
