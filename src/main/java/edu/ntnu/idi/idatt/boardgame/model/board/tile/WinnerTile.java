package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.WinnerAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * The final tile on the board, where if a player lands on it, they win the game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class WinnerTile extends SpecialTile {

  /**
   * Constructor for the WinnerTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public WinnerTile(int tileNumber, int[] onscreenPosition) {
    super(tileNumber, onscreenPosition);
    this.tileAction = new WinnerAction();
    this.tileType = TileType.WINNER;
  }

  @Override
  public void performAction(Player player) {
    try {
      tileAction.performAction(player);
    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
    }
  }


}
