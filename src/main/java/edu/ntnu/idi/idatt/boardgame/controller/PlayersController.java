package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.ArrayList;

/**
 * A class representing the controller for the players in the game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class PlayersController {

  private final ArrayList<Player> players;
  private Player currentPlayer;
  private Player nextPlayer;

  /**
   * Constructor for the PlayersController.
   *
   * @param players An {@link ArrayList} containing the players in the game.
   */
  public PlayersController(ArrayList<Player> players) {
    this.players = players;
  }

  /**
   * Returns the {@link Player} that is currently taking their turn.
   *
   * @return The {@link Player} that is currently taking their turn.
   */
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Sets the {@link Player} that is currently taking their turn.
   *
   * @param index The index of the {@link Player} in the {@link ArrayList} of players.
   */
  public void setCurrentPlayer(int index) {
    if (index < 0) {
      throw new IllegalArgumentException("Index cannot be less than 0");
    }

    currentPlayer = players.get(index);

    if (index + 1 >= players.size()) {
      nextPlayer = players.getFirst();
    } else {
      nextPlayer = players.get(index + 1);
    }

  }

  /**
   * Returns the {@link Player} that is next in line to take their turn.
   *
   * @return A {@link Player} object representing the player that is next in line to take their
   * turn.
   */
  public Player getNextPlayer() {
    return nextPlayer;
  }

  /**
   * Returns the {@link ArrayList} containing the players in the game.
   *
   * @return The {@link ArrayList} containing the players in the game.
   */
  public ArrayList<Player> getPlayers() {
    return players;
  }


}
