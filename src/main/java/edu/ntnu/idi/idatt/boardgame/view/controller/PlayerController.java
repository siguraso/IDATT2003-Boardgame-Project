package edu.ntnu.idi.idatt.boardgame.view.controller;

import edu.ntnu.idi.idatt.boardgame.model.Observable;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;

/**
 * <h1>Class - PlayerController.</h1>
 *
 * <p>A controller class for the player in a board game.
 * This class is part of the Controller component in a Model-View-Controller architecture.
 * </p>
 *
 * @author Magnus Næssan Gaarder
 * @version 1.0
 * @see BoardGameObserver
 * @since 1.0
 */
public class PlayerController implements BoardGameObserver {
  private final Board board; // The board. Can be removed if not used.

  /**
   * Constructor for the PlayerController class.
   *
   * @param board The board that the player is on.
   */
  public PlayerController(Board board) {
    this.board = board;
  }

  @Override
  public void update(Observable subject) {
    try {
      Player player = (Player) subject;
      if (player.isWinner()) {
        // TODO display victory and end game
        System.out.println("Player: " + player.getName() + ", has won the game!");
        return;
      }
      // TODO move the player to the correct position on the board
      System.out.println("Player: " + player.getName() + ", is now at tile: "
          + player.getCurrentTile().getTileNumber());
    } catch (ClassCastException e) {
      System.err.println("Invalid subject type passed to PlayerController: "
          + subject.getClass().getName());
    }
  }
}