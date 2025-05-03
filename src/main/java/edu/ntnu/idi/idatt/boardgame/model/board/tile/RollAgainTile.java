package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.RollAgainAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A class representing a tile that lets the player roll again.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class RollAgainTile extends SpecialTile {

  private final TileType tileType = TileType.ROLL_AGAIN;

  /**
   * Constructor for a RollAgainTile.
   *
   * @param tileNumber       the number of the tile.
   * @param onscreenPosition the position of the tile on the screen.
   */
  public RollAgainTile(int tileNumber, int[] onscreenPosition) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
  }

  @Override
  public String getTileType() {
    return tileType.getTileType();
  }

  @Override
  public void performAction(Player player) {
    try {
      RollAgainAction rollAgainAction = new RollAgainAction();
      rollAgainAction.performAction(player);
    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
    }
  }
}
