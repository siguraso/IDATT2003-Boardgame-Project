package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.board.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;

/**
 * A special action that moves a player up or down a ladder on the board.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class LadderAction implements TileAction {

  private final Tile destinationTile;

  /**
   * Constructor for the LadderAction class.
   *
   * @param destinationTile the tile the player will move to if they land on the ladder space.
   */
  public LadderAction(Tile destinationTile) {
    this.destinationTile = destinationTile;
  }

  @Override
  public void performAction(Player player) {
    player.move(destinationTile);
  }
}
