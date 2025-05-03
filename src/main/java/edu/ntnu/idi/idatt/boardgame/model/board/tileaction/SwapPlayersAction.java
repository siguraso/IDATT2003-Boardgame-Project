package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * A tile action that swaps to players positions on the board.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class SwapPlayersAction implements TileAction {

  private ArrayList<Player> players;
  private Player playerToSwapWith;

  /**
   * Constructor for the SwapPlayersAction class.
   */
  public SwapPlayersAction() {
  }

  /**
   * Sets the players that are currently in the game.
   */
  public void setPlayers(List<Player> players) {
    this.players = new ArrayList<>(players);
  }

  /**
   * Returns the last {@link Player} that has been swapping with.
   *
   * @return the {@link Player} that the {@link Player} is swapping with.
   */
  public Player getPlayerToSwapWith() {
    return playerToSwapWith;
  }

  /**
   * Swaps the position of the player with the playerToSwapWith.
   *
   * @param player The player that lands on the space.
   */
  @Override
  public void performAction(Player player) {
    ArrayList<Player> playersToSwapWith = new ArrayList<>(players);

    // remove the player from the list of players to swap with
    playersToSwapWith.remove(player);

    playerToSwapWith = playersToSwapWith.get(
        (int) (Math.random() * playersToSwapWith.size()));

    int landingPlayerPosition = player.getPosition();
    int swappingPlayerPosition = playerToSwapWith.getPosition();

    player.moveTo(swappingPlayerPosition);
    playerToSwapWith.moveTo(landingPlayerPosition);
  }


}
