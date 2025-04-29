package edu.ntnu.idi.idatt.boardgame.model.board.tile;

/**
 * Interface for the tiles on a game board.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public interface Tile {

  /**
   * Used to get the number of the tile, which is used as the identifier of the tile.
   *
   * @return The number of the tile as an integer.
   */
  int getTileNumber();

  /**
   * Used to get the position of the tile on the screen.
   *
   * @return An array with two elements, the x (as index 0) and y position (as index 1) of the tile.
   */
  int[] getOnscreenPosition();

  /**
   * Used to get the type of the tile.
   *
   * @return The type of the tile as a string.
   */
  String getTileType();
}
