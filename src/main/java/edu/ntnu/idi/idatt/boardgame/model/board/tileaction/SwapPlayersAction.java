package edu.ntnu.idi.idatt.boardgame.model.board.tileaction;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A tile action that swaps to players positions on the board.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public class SwapPlayersAction implements TileAction {

  private ArrayList<Player> players;
  private Player playerToSwapWith;
  private final Random random = new Random();


  /**
   * Returns the players that are currently in the game.
   *
   * @return the players that are currently in the game.
   */
  public List<Player> getPlayers() {
    if (players == null) {
      throw new IllegalStateException(
          "Players have not been set. Please call setPlayers() first.");
    }
    return players;
  }

  /**
   * Sets the players that are currently in the game.
   */
  public void setPlayers(List<Player> players) {
    if (players == null) {
      throw new NullPointerException(
          "Players cannot be null. Please provide a valid list of players.");
    } else if (players.size() < 2) {
      throw new IllegalArgumentException(
          "At least two players are required to perform a swap.");
    } else {
      this.players = new ArrayList<>(players);
    }
  }

  /**
   * Returns the last {@link Player} that has been swapping with.
   *
   * @return the {@link Player} that the {@link Player} is swapping with.
   */
  public Player getPlayerToSwapWith() {
    if (playerToSwapWith == null) {
      throw new IllegalStateException(
          "Player to swap with is not set. Please call performAction() first.");
    }
    return playerToSwapWith;
  }

  /**
   * Swaps the position of the player with the playerToSwapWith.
   *
   * @param player The player that lands on the space.
   */
  @Override
  public void performAction(Player player) {
    if (player == null) {
      throw new NullPointerException(
          "Player cannot be null. Please provide a valid player.");
    }
    ArrayList<Player> playersToSwapWith = new ArrayList<>(players);

    // remove the player from the list of players to swap with
    playersToSwapWith.remove(player);

    playerToSwapWith = playersToSwapWith.get(
        random.nextInt(playersToSwapWith.size()));

    int landingPlayerPosition = player.getPosition();
    int swappingPlayerPosition = playerToSwapWith.getPosition();

    player.moveTo(swappingPlayerPosition);
    playerToSwapWith.moveTo(landingPlayerPosition);
  }
}
