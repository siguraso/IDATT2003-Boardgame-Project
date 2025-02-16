package edu.ntnu.idi.idatt.boardgame.model.board.tile;

import edu.ntnu.idi.idatt.boardgame.model.board.tileaction.LadderAction;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special tile on the board, where if a player lands on it, the player will move up or down a
 * ladder.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class LadderTile extends SpecialTile {

  private final Tile destinationTile;

  /**
   * Constructor for the LadderTile class.
   *
   * @param tileNumber       The number of the tile on the board.
   * @param onscreenPosition The position of the tile on the screen.
   */
  public LadderTile(int tileNumber, int[] onscreenPosition, Tile destinationTile) {
    this.tileNumber = tileNumber;
    this.onscreenPosition = onscreenPosition;
    this.destinationTile = destinationTile;
  }

  @Override
  public void performAction(Player player) {
    // initialize the tileAction with a LadderAction
    this.tileAction = new LadderAction(destinationTile);

    tileAction.performAction(player);
  }

}
