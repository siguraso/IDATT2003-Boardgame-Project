package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * A special action that moves a player up or down a ladder on the board.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public class LadderAction implements TileAction {

  private final int destination;

  /**
   * Constructor for the LadderAction class.
   *
   * @param destination the position the player will moveForward to if they land on the ladder
   *                    space.
   */
  public LadderAction(int destination) {
    if (destination < 1 || destination >= 90) {
      throw new IllegalArgumentException("Destination must be between 1 and 89");
    }
    this.destination = destination;
  }

  @Override
  public void performAction(Player player) {
    if (player == null) {
      throw new NullPointerException("Player cannot be null");
    } else {
      try {
        player.handleLadderAction(destination);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(e.getMessage());
      }
    }
  }
}
