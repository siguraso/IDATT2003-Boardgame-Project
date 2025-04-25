package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;

/**
 * A class representing the controller for the players in the game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class PlayersController {

  private final ArrayList<Player> players = new ArrayList<>();
  private Player currentPlayer;
  private Player previousPlayer;

  /**
   * Constructor for the PlayersController.
   */
  public PlayersController() {
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

    this.currentPlayer = players.get(index);

  }

  /**
   * Sets the {@link Player} to the next player in the {@link ArrayList} of players.
   *
   * <p>This method is used to set the next player in line to take their turn.</p>
   */
  public void nextPlayer() {
    int index = players.indexOf(currentPlayer);

    setPreviousPlayer(index);

    if (index + 1 == players.size()) {
      setCurrentPlayer(0);
    } else {
      setCurrentPlayer(index + 1);
    }

  }

  /**
   * Returns the {@link Player} that was previously taking their turn.
   *
   * @return The {@link Player} that was previously taking their turn.
   */
  public Player getPreviousPlayer() {
    return previousPlayer;
  }

  /**
   * Sets the {@link Player} that was previously taking their turn-
   *
   * @return The index of the {@link Player} that was previously taking their turn.
   */
  public void setPreviousPlayer(int index) {
    this.previousPlayer = players.get(index);

  }

  /**
   * Returns the {@link ArrayList} containing the players in the game.
   *
   * @return The {@link ArrayList} containing the players in the game.
   */
  public ArrayList<Player> getPlayers() {
    return players;
  }

  /**
   * Adds a {@link Player} to the {@link ArrayList} of players.
   *
   * @param name  The name of the player.
   * @param piece The {@link PlayerPiece} that the player will use on the board.
   */
  public void addPlayer(String name, PlayerPiece piece) {
    players.add(new Player(name, piece));
  }


}
