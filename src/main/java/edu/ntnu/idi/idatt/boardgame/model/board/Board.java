package edu.ntnu.idi.idatt.boardgame.model.board;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.util.HashMap;
import java.util.Map;

/**
 * The board of a board game that contains the tiles on the board. This is a record class that
 * contains a HashMap of all the tiles on the board. The key is the tile number and the value is the
 * tile object itself.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public record Board(Map<Integer, Tile> tiles) {

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
   * Return all the TileTypes on the board as a {@link HashMap} with the tile number as the key and
   * the tile type as the value.
   *
   * @return A {@link Map} containing all the tile types on the board.
   */
  public Map<Integer, String> getTileTypes() {
    Map<Integer, String> tileTypes = new HashMap<>();
    tiles.keySet().forEach(tile ->
        tileTypes.put(tile, tiles.get(tile).getTileType())
    );

    return tileTypes;
  }
}
