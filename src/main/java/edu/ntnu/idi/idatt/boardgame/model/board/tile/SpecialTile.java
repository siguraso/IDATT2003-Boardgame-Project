package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile on the board, where the player can moveForward to, and a TileAction happens.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public abstract class SpecialTile implements Tile {

  protected int tileNumber;
  protected int[] onscreenPosition;
  protected TileAction tileAction;

  @Override
  public int getTileNumber() {
    return tileNumber;
  }

  @Override
  public int[] getOnscreenPosition() {
    return onscreenPosition;
  }

  /**
   * Performs a special tile action on a player.
   *
   * @param player the player to perform the action on.
   */
  public abstract void performAction(Player player);

  @Override
  public abstract String getTileType();

}
