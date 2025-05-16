package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RemoveCoinsAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile that adds coins to the player when they land on it. This tile is used in the Pario
 * Marty game.
 */
public class RemoveCoinsTile extends SpecialTile {

  private final TileType tileType = TileType.REMOVE_COINS;

  /**
   * Constructor for the RemoveCoinsTile class.
   *
   * @param coinsToRemove the amount of coins to remove from the player when they land on this tile
   */
  public RemoveCoinsTile(int tileNumber, int[] onscreenPosition, int coinsToRemove) {
    try {
      TileAction tileAction = new RemoveCoinsAction(coinsToRemove);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Coins cannot be negative");
    }

    this.tileNumber = coinsToRemove;
    this.onscreenPosition = onscreenPosition;
  }

  @Override
  public String getTileType() {
    return tileType.getTileType();
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
