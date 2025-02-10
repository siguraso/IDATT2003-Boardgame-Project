package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.WinnerAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

public class WinnerTile extends SpecialTile {

  /**
   * Constructor for the WinnerTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public WinnerTile(int tileNumber, int[] onscreenPosition) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
  }

  @Override
  public void performAction(Player player) {
    // initialize the tileAction with a WinnerAction
    this.tileAction = new WinnerAction();

    tileAction.performAction(player);
  }

}
