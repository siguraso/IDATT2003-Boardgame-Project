package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.HashMap;

/**
 * A class representing the controller for the players in the game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class PlayersController {

  private final HashMap<String, Player> players;
  private Player currentPlayer;

  /**
   * Constructor for the PlayersController.
   *
   * @param players A {@link HashMap} containing the players in the game.
   */
  public PlayersController(HashMap<String, Player> players) {
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
   * @param playerName The name of the player that is currently taking their turn.
   */
  public void setCurrentPlayer(String playerName) {
    if (players.containsKey(playerName)) {
      this.currentPlayer = players.get(playerName);
    } else {
      throw new IllegalArgumentException("Player " + playerName + " not found");
    }


  }

  /**
   * Returns the {@link HashMap} containing the players in the game.
   *
   * @return The {@link HashMap} containing the players in the game.
   */
  public HashMap<String, Player> getPlayers() {
    return players;
  }


}
