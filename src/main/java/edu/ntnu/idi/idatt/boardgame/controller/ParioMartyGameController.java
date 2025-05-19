package edu.ntnu.idi.idatt.boardgame.controller;

/**
 * Controller class for Pario Marty. This class extends the {@link GameController} class.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class ParioMartyGameController extends GameController {

  /**
   * Constructor for the ParioMartyGameController class. This constructor initializes the game board
   * and sets the current player.
   *
   * @param playersController The controller object for the players in the game.
   */
  public ParioMartyGameController(PlayersController playersController, boolean useTwoDice) {
    super(playersController, useTwoDice);
  }

  @Override
  public void finishTurn() {
    // check the tile the current player is on
    // check what typa tile it is, do the action if it is a special tile

    if (playersController.getCurrentPlayer() != null) {
      die.removeObserver(playersController.getCurrentPlayer());
    }

    playersController.nextPlayer();

    die.addObserver(playersController.getCurrentPlayer());
  }
}
