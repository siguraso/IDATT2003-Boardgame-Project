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

  private HashMap<Integer, Tile> tiles;
  private BoardType boardType;

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

  /**
   * Used to get the boardType of the board.
   *
   * @return The boardType of the board.
   */
  public BoardType getBoardType() {
    return boardType;
  }

  /**
   * Mutator method for the boardType.
   *
   * @param boardType The boardType of the board.
   */
  public void setBoardType(BoardType boardType) {
    this.boardType = boardType;
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
