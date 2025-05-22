package edu.ntnu.idi.idatt.boardgame.model.board.tile;


import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.AddCrownAction;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile that adds a crown to the player when they land on it. This is a tile that only
 * appears in the ParioMarty game, and thus, only works with Pario Marty players This class extends
 * the {@link SpecialTile} class.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class AddCrownTile extends SpecialTile {

  /**
   * Constructor for the AddCrownTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public AddCrownTile(int tileNumber, int[] onscreenPosition) {
    super(tileNumber, onscreenPosition);
    this.tileAction = new AddCrownAction();
    this.tileType = TileType.ADD_CROWN;
  }

  @Override
  public void performAction(Player player) {
    try {
      if (((ParioMartyPlayer) player).getCoins() >= 10) {

        tileAction.performAction(player);
        ((ParioMartyPlayer) player).removeCoins(10);

      } else {
        throw new IllegalArgumentException("Player does not have 10 or more coins.");
      }
    } catch (NullPointerException e) {
      throw new NullPointerException("Player cannot be null");
    }
  }
}

