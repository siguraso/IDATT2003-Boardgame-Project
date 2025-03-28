package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;


import edu.ntnu.idi.idatt.boardgame.model.board.tile.SpecialTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.HashMap;

/**
 * Interface for the special actions that can be performed on a special tile.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class MoveToRandomTileAction implements TileAction {

  HashMap<Integer, Tile> board;

  /**
   * Constructor for the MoveToRandomTileAction class.
   *
   * @param board HashMap containing all the tiles on the board.
   */
  public MoveToRandomTileAction(HashMap<Integer, Tile> board) {
    this.board = board;
  }

  @Override
  public void performAction(Player player) {
    int newPosition = (int) (Math.random() * board.size());
    Tile boardTile = board.get(newPosition);

    if (boardTile instanceof SpecialTile) {
      throw new IllegalArgumentException(
          "Player cannot be moved to a special tile using this action.");
    }

    player.move(newPosition);

  }


}
