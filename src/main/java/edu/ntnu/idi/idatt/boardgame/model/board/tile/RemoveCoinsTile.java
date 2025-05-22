package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RemoveCoinsAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile that adds coins to the player when they land on it. This tile is used in the Pario
 * Marty game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class RemoveCoinsTile extends SpecialTile {

  /**
   * Constructor for the RemoveCoinsTile class.
   *
   * @param coinsToRemove the amount of coins to remove from the player when they land on this tile
   */
  public RemoveCoinsTile(int tileNumber, int[] onscreenPosition, int coinsToRemove) {
    super(tileNumber, onscreenPosition);
    try {
      this.tileAction = new RemoveCoinsAction(coinsToRemove);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Coins cannot be negative");
    }

    this.tileType = TileType.REMOVE_COINS;
  }

  @Override
  public void performAction(Player player) {
    try {
      tileAction.performAction(player);
    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
    } catch (ClassCastException e) {
      throw new ClassCastException(e.getMessage());
    }
  }
}
