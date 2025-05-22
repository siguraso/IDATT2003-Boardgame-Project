package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile on the board, where the player can moveForward to, and a TileAction happens.
 *
 * @author siguraso & MagnusNæssanGaarder
 * @version 1.0
 * @since 1.0
 */
public abstract class SpecialTile implements Tile {

  protected int tileNumber;
  protected int[] onscreenPosition;
  protected TileAction tileAction;
  protected TileType tileType;

  /**
   * Base constructor for the SpecialTile abstract class.
   *
   * @param tileNumber       the number that identifies the tile.
   * @param onscreenPosition the position of the tile in the grid on the screen.
   */
  protected SpecialTile(int tileNumber, int[] onscreenPosition) {
    if (tileNumber < 0) {
      throw new IllegalArgumentException("Tile number cannot be negative");
    } else if (onscreenPosition == null) {
      throw new IllegalArgumentException("Onscreen position cannot be null");
    } else if (onscreenPosition.length != 2) {
      throw new IllegalArgumentException("Onscreen position must be an array of length 2");
    } else if (onscreenPosition[0] < 0 || onscreenPosition[1] < 0) {
      throw new IllegalArgumentException("Onscreen position cannot be negative");
    } else {
      this.tileNumber = tileNumber;
      this.onscreenPosition = onscreenPosition;
    }
  }

  @Override
  public int getTileNumber() {
    return this.tileNumber;
  }

  @Override
  public int[] getOnscreenPosition() {
    return this.onscreenPosition;
  }

  /**
   * Performs a special tile action on a player.
   *
   * @param player the player to perform the action on.
   */
  public abstract void performAction(Player player);

  @Override
  public String getTileType() {
    return tileType.getTileType();
  }

}
