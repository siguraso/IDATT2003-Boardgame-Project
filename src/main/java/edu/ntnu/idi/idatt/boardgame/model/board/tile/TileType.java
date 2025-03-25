package edu.ntnu.idi.idatt.boardgame.model.board.tile;

/**
 * An enum representing the different types of tiles on the board. Used when writing the board to a
 * JSON file.
 */
public enum TileType {
  NORMAL,
  LADDER,
  RANDOM_ACTION,
  RETURN_TO_START,
  WINNER
}
