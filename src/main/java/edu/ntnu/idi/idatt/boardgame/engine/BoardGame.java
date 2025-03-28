package edu.ntnu.idi.idatt.boardgame.engine;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.view.window.BoardGameWindow;
import java.util.HashMap;

/**
 * Board game engine class that is responsible for managing the game state and the game loop.
 *
 * <p> The BoardGame class is responsible for managing the game state and the game loop. It
 * contains the game board, the players, and the die used in the game. The class has a method that
 * starts the game loop and runs the game until a player has won. </p>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class BoardGame {

  private final Board board;
  private final HashMap<String, Player> players;

  private Player currentPlayer;

  /**
   * Constructor for the BoardGame class.
   *
   * @param board   the {@link Board} object that represents the game board.
   * @param players a {@link HashMap} containing all the players in the game.
   */
  public BoardGame(Board board, HashMap<String, Player> players) {
    this.board = board;
    this.players = players;
  }

  public void startGame() {
    boolean hasWinner = false;

    while (!hasWinner) {
      for (Player player : players.values()) {

        // wait for player to roll the die

        //while (die.getCurrentThrow() == lastThrow) {
        // wait for player to roll the die
        //}

        // move the player

        // check what typa tile the player has landed on
        // if the player has landed on a special tile do the tile action

        // check if the player has won

      }
    }
  }

  /**
   * Method to set the current player when it is their turn.
   *
   * @param currentPlayer {@link Player} object that represents the current player.
   */
  public void setCurrentPlayer(Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  /**
   * Method to get the player that currently has their turn.
   *
   * @return {@link Player} object that represents the current player.
   */
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Method to get the board games {@link Board} object.
   *
   * @return the {@link Board} object that represents the game board.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Method to access the {@link HashMap} that contains the players in the game.
   *
   * @return a {@link HashMap} containing all the players in the game.
   */
  public HashMap<String, Player> getPlayers() {
    return players;
  }

}
