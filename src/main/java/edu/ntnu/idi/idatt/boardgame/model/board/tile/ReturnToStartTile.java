package edu.ntnu.idi.idatt.boardgame.model.board.tile;

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

  /**
   * Constructor for the ReturnToStartTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public ReturnToStartTile(int tileNumber, int[] onscreenPosition) {
    super(tileNumber, onscreenPosition);

    this.tileAction = new ReturnToStartAction();
    this.tileType = TileType.RETURN_TO_START;
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
