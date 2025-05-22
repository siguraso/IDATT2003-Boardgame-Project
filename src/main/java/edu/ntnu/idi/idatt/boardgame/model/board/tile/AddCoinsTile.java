package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.AddCoinsAction;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile that adds coins to the player when they land on it. This is a tile that only
 * appears in the ParioMarty game. This class extends the {@link SpecialTile} class.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class AddCoinsTile extends SpecialTile {

  /**
   * Constructor for the AddCoinsTile class.
   *
   * @param coinsToAdd the amount of coins to add to the player when they land on this tile
   */
  public AddCoinsTile(int tileNumber, int[] onscreenPosition, int coinsToAdd) {
    super(tileNumber, onscreenPosition);
    try {
      this.tileAction = new AddCoinsAction(coinsToAdd);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Coins cannot be negative");
    }
    this.tileType = TileType.ADD_COINS;
  }

  @Override
  public void performAction(Player player) {
    try {
      tileAction.performAction(player);
    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
    } catch (InvalidPlayerException e) {
      throw new InvalidPlayerException(e.getMessage());
    }
  }
}
