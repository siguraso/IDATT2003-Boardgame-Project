package edu.ntnu.idi.idatt.boardgame.model.board;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.util.HashMap;

/**
 * The board of a board game that contains the tiles on the board.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class Board {

  HashMap<Integer, Tile> tiles;

  /**
   * Constructor for the Board class.
   *
   * @param tiles A HashMap containing all the tiles on the board, with the tile number as the key.
   */
  public Board(HashMap<Integer, Tile> tiles) {
    this.tiles = tiles;
  }

  // accessor methods

  /**
   * Used to get the HashMap containtning all the tiles on the board.
   *
   * @return A HashMap containing all the tiles on the board.
   */
  public HashMap<Integer, Tile> getTiles() {
    return tiles;
  }

  /**
   * Mutator method for the tiles {@link HashMap}.
   *
   * @param tiles A {@link HashMap} containing all the tiles on the board.
   */
  public void setTiles(HashMap<Integer, Tile> tiles) {
    this.tiles = tiles;
  }
}
