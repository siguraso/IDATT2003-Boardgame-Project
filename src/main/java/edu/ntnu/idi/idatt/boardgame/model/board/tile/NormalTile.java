package edu.ntnu.idi.idatt.boardgame.model.board.tile;

/**
 * A normal tile on the board, where the player can moveForward to, but nothing special happens.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class NormalTile implements Tile {

  private final TileType type = TileType.NORMAL;
  private final int tileNumber;
  private final int[] onscreenPosition;

  /**
   * Constructor for the NormalTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public NormalTile(int tileNumber, int[] onscreenPosition) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
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
    return "NormalTile";
  }

}
