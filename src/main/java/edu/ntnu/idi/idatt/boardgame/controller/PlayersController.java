package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.player.LadderGamePlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.ParioMartyPlayer;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
   * Returns the {@link List} containing the players in the game as {@link ParioMartyPlayer}.
   *
   * @return The {@link List} containing the players in the game as {@link ParioMartyPlayer}.
   * @throws IllegalArgumentException if a player is not a {@link ParioMartyPlayer}.
   */
  public List<ParioMartyPlayer> getPlayersAsParioMarty() throws IllegalArgumentException {
    List<ParioMartyPlayer> parioMartyPlayers = new ArrayList<>();

    players.forEach(player -> {
      try {
        parioMartyPlayers.add((ParioMartyPlayer) player);
      } catch (ClassCastException e) {
        throw new IllegalArgumentException("Player is not a ParioMartyPlayer: " + player.getName());
      }
    });

    return parioMartyPlayers;
  }

  /**
   * Adds a {@link LadderGamePlayer} to the {@link ArrayList} of players.
   *
   * @param name  The name of the player.
   * @param piece The {@link PlayerPiece} that the player will use on the board.
   */
  public void addLadderGamePlayer(String name, PlayerPiece piece) {
    if (hasPlayerWithName(name)) {
      throw new IllegalArgumentException("Duplicate player name: " + name);
    }

    players.add(new LadderGamePlayer(name, piece));
  }

  /**
   * Adds a {@link ParioMartyPlayer} to the {@link ArrayList} of players.
   *
   * @param name  The name of the player.
   * @param piece The {@link PlayerPiece} that the player will use on the board.
   */
  public void addParioMartyPlayer(String name, PlayerPiece piece) {
    if (hasPlayerWithName(name)) {
      throw new IllegalArgumentException("Duplicate player name: " + name);
    }

    players.add(new ParioMartyPlayer(name, piece));
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
    Set<String> playerNames = new HashSet<>();

    players.forEach(player -> {
      if (!playerNames.add(player.getName().toLowerCase())) {
        throw new IllegalArgumentException("Duplicate player name: " + player.getName());
      }
    });

    this.players.clear();
    this.players.addAll(players);
  }

  /**
   * Removes the {@link Player} objects where isWinner is true from the {@link ArrayList} of
   * players.
   */
  public void removeWinners() {
    players.removeIf(Player::isWinner);
  }

  /**
   * Accesses the {@link Player} positions based on their name, and turns it into a {@link Map}
   * where the key is the {@link Player} name and the value is the player position.
   *
   * @return A {@link Map} containing the player names and their positions.
   */
  public Map<String, Integer> getPlayerPositions() {
    Map<String, Integer> playerPositions = new HashMap<>();

    players.forEach(player -> playerPositions.put(player.getName(), player.getPosition()));

    return playerPositions;
  }

  /**
   * Checks if a player name already exists in the player list.
   *
   * @param name The name to check for duplicates
   * @return true if the name already exists, false otherwise
   */
  private boolean hasPlayerWithName(String name) {
    return players.stream()
        .anyMatch(player -> player.getName().equalsIgnoreCase(name));
  }

}
