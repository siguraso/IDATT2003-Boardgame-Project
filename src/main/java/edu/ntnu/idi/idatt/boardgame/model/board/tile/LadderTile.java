package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.LadderAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile on the board, where if a player lands on it, the player will moveForward up or
 * down a ladder.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class LadderTile extends SpecialTile {

  private final TileType tileType = TileType.LADDER;
  private final int destinationTileNumber;


  /**
   * Constructor for the LadderTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public LadderTile(int tileNumber, int[] onscreenPosition, int destinationTileNumber) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
    this.destinationTileNumber = destinationTileNumber;
  }

  /**
   * Used to get the number of the tile, which is used as the identifier of the tile.
   *
   * @return The number of the tile as an integer.
   */
  public int getDestinationTileNumber() {
    return destinationTileNumber;
  }

  @Override
  public void performAction(Player player) {
    // initialize the tileAction with a LadderAction
    this.tileAction = new LadderAction(destinationTileNumber);
    tileAction.performAction(player);
  }

  @Override
  public String getTileType() {
    return tileType.getTileType();
  }

}
