package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.ReturnToStartAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile that moves a player back to the start of the board if the player lands on it.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class ReturnToStartTile extends SpecialTile {

  private final transient Board board;
  private final TileType tileType = TileType.RETURN_TO_START;

  /**
   * Constructor for the ReturnToStartTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public ReturnToStartTile(int tileNumber, int[] onscreenPosition, Board board) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
    this.board = board;
  }

  @Override
  public void performAction(Player player) {
    // initialize the tileAction with a ReturnToStartAction
    this.tileAction = new ReturnToStartAction(board);

    tileAction.performAction(player);
  }

  @Override
  public String getTileType() {
    return tileType.getTileType();
  }

}
