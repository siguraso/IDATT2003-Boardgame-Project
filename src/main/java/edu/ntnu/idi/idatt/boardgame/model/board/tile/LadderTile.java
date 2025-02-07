package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.TileAction;

public class LadderTile extends SpecialTile {

  /**
   * Constructor for the LadderTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   * @param tileAction       The action that should be performed when a player lands on the tile.
   */
  public LadderTile(int tileNumber, int[] onscreenPosition, TileAction tileAction) {
    this.tileNumber = tileNumber;
  }

}
