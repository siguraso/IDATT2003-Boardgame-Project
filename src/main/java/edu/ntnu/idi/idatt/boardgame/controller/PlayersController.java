package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import java.util.List;

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

    if (index == players.size() - 1 && !currentPlayer.canRollAgain()) {
      // if the current player is the last player in the list and they cannot roll again,
      // set the current player to the first player in the list
      setCurrentPlayer(0);
    } else if (!currentPlayer.canRollAgain()) {
      // if the current player is not the last player in the list and they cannot roll again,
      // set the current player to the next player in the list
      setCurrentPlayer(index + 1);
    } else {
      // if the current player can roll again, do not change the current player
      currentPlayer.setRollAgain(false);
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
   * Sets the {@link Player} that was previously taking their turn.
   */
  public void setPreviousPlayer(int index) {
    this.previousPlayer = players.get(index);

  }

  /**
   * Returns the {@link List} containing the players in the game.
   *
   * @return The {@link List} containing the players in the game.
   */
  public List<Player> getPlayers() {
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

  /**
   * Clears the {@link ArrayList} of players.
   */
  public void clearPlayers() {
    players.clear();
  }

  /**
   * Sets the {@link List} of players.
   *
   * @param players The {@link List} of players to set.
   */
  public void setPlayers(List<Player> players) {
    this.players.clear();
    this.players.addAll(players);
  }

}
