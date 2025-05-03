package edu.ntnu.idi.idatt.boardgame.model.board;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.util.HashMap;

/**
 * The board of a board game that contains the tiles on the board. This is a record class that
 * contains a HashMap of all the tiles on the board. The key is the tile number and the value is the
 * tile object itself.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public record Board(HashMap<Integer, Tile> tiles) {

  /**
   * Constructor for the Board class.
   *
   * @param tiles A HashMap containing all the tiles on the board, with the tile number as the key.
   */
  public Board {
    if (tiles == null) {
      throw new NullPointerException("Tiles cannot be null.");
    }

  }

  // accessor methods

  /**
   * Used to get the HashMap containtning all the tiles on the board.
   *
   * @return A HashMap containing all the tiles on the board.
   */
  @Override
  public HashMap<Integer, Tile> tiles() {
    return tiles;
  }


  /**
   * Return all the TileTypes on the board as a {@link HashMap} with the tile number as the key and
   * the tile type as the value.
   *
   * @return A {@link HashMap} containing all the tile types on the board.
   */
  public HashMap<Integer, String> getTileTypes() {
    HashMap<Integer, String> tileTypes = new HashMap<>();
    tiles.keySet().forEach(tile -> {
      tileTypes.put(tile, tiles.get(tile).getTileType());
    });

    return tileTypes;
  }
}
