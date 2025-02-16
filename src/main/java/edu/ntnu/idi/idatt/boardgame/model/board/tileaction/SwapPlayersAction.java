package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.util.HashMap;

/**
 * A tile action that swaps to players positions on the board.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class SwapPlayersAction implements TileAction {

  HashMap<Integer, Player> playersToSwapWith;

  /**
   * Constructor for the SwapPlayersAction class.
   *
   * @param playersToSwapWith The player to swap positions with.
   */
  public SwapPlayersAction(HashMap<Integer, Player> playersToSwapWith) {
    this.playersToSwapWith = playersToSwapWith;
  }

  /**
   * Swaps the position of the player with the playerToSwapWith.
   *
   * @param player The player that lands on the space.
   */
  @Override
  public void performAction(Player player) {
    Player playerToSwapWith = playersToSwapWith.get(
        (int) (Math.random() * playersToSwapWith.size()));

    if (playerToSwapWith == player) {
      throw new IllegalArgumentException("Player cannot swap position with itself.");
    }

    Tile landingPlayerPosition = player.getCurrentTile();
    Tile swappingPlayerPosition = playerToSwapWith.getCurrentTile();

    player.move(swappingPlayerPosition);
    playerToSwapWith.move(landingPlayerPosition);
  }

}
