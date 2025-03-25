package edu.ntnu.idi.idatt.boardgame.model.board.tile;

/**
 * An enum representing the different types of tiles on the board. Used when writing the board to a
 * JSON file.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public enum TileType {
  NORMAL,
  LADDER,
  RANDOM_ACTION,
  ROLL_AGAIN,
  RETURN_TO_START,
  WINNER
}
