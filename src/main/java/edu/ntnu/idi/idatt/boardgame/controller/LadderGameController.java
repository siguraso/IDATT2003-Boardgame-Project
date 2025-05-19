package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RandomActionTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.SpecialTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;

/**
 * Controller class for the Ladder game. This class extends the {@link GameController} class.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class LadderGameController extends GameController {

  /**
   * Constructor for the LadderGameController class. This constructor initializes the game board and
   * sets the current player.
   *
   * @param playersController The controller object for the players in the game.
   */
  public LadderGameController(PlayersController playersController, boolean useTwoDice) {
    super(playersController, useTwoDice);
  }

  @Override
  public void finishTurn() {
    // check the tile the current player is on
    Tile currentTile = board.tiles().get(playersController.getCurrentPlayer().getPosition());

    // check what typa tile it is, do the action if it is a special tile
    if (!currentTile.getTileType().equals(TileType.NORMAL.getTileType())) {
      if (currentTile.getTileType().equals(TileType.RANDOM_ACTION.getTileType())) {
        ((RandomActionTile) currentTile).setPlayers(playersController.getPlayers());
      }

      lastSpecialTile = playersController.getCurrentPlayer().getPosition();
      ((SpecialTile) currentTile).performAction(playersController.getCurrentPlayer());
    }

    if (playersController.getCurrentPlayer() != null) {
      die.removeObserver(playersController.getCurrentPlayer());
    }

    playersController.nextPlayer();

    die.addObserver(playersController.getCurrentPlayer());
  }

  /**
   * Method to get the destination tile number of a LadderTile.
   *
   * @param tileNumber an integer representing the tile number.
   */
  public int getLadderDestinationTileNumber(int tileNumber) {
    Tile tile = board.tiles().get(tileNumber);

    if (!tile.getTileType().equals(TileType.LADDER.getTileType())) {
      throw new IllegalArgumentException("Tile number " + tileNumber + " is not a LadderTile");
    }

    return ((LadderTile) tile).getDestinationTileNumber();
  }

  /**
   * Remove the winners from the game. Used when a player wins the game. and the user chooses to
   * continue playing.
   */
  public void removeWinners() {
    die.removeObserver(playersController.getCurrentPlayer());

    playersController.nextPlayer();

    die.addObserver(playersController.getCurrentPlayer());

    playersController.removeWinners();
  }

}
