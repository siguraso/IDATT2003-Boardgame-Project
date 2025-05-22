package edu.ntnu.idi.idatt.boardgame.model.board.tile;

/**
 * A normal tile on the board, where the player can moveForward to, but nothing special happens.
 * This is a simple tile that does not have any special actions associated with it, and can
 * therefore be defined as a record.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class NormalTile implements Tile {

  private static final TileType TILE_TYPE = TileType.NORMAL;
  private final int tileNumber;
  private final int[] onscreenPosition;

  /**
   * Constructor for the NormalTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public NormalTile(int tileNumber, int[] onscreenPosition) {
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
    return tileNumber;
  }

  @Override
  public int[] getOnscreenPosition() {
    return onscreenPosition;
  }


  @Override
  public String getTileType() {
    return TILE_TYPE.getTileType();
  }

}
