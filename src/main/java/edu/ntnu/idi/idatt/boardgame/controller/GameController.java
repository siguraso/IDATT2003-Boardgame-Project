package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardFactory;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RandomActionTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.SpecialTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObservable;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import java.util.ArrayList;

/**
 * <h1>Class - GameController.</h1>
 *
 * <p>A controller-class to control the flow of the game</p>
 *
 * @author Magnus Næssan Gaarder & siguraso
 * @version 1.0
 * @see BoardGameObserver
 * @since 1.0
 */
public class GameController implements BoardGameObserver, BoardGameObservable {

  private final Die die = new Die(6);
  private final PlayersController playersController;
  private Board board;

  private final ArrayList<BoardGameObserver> uiObservers = new ArrayList<>();

  private int lastSpecialTile;

  /**
   * Constructor for the GameController.
   *
   * <p>This class controls the flow of the game</p>
   *
   * @param playersController The controller object for the players in the game.
   */
  public GameController(PlayersController playersController, boolean useTwoDice) {
    this.playersController = playersController;

    die.addObserver(this);

    playersController.setCurrentPlayer(0);
    playersController.setPreviousPlayer(0);

    // add the current player to the die observer list
    die.addObserver(playersController.getCurrentPlayer());
  }

  /**
   * Method to set the {@link Board} for the game based on the given {@link BoardType}.
   *
   * @param boardType The type of board to be used in the game.
   */
  public void setBoard(BoardType boardType) {
    this.board = BoardFactory.createBoard(boardType);
  }

  /**
   * Method to roll the die.
   */
  public void rollDice() {
    die.throwDie();
  }

  /**
   * Method to get the games {@link PlayersController}.
   *
   * @return this games {@link PlayersController} object.
   */
  public PlayersController getPlayersController() {
    return playersController;
  }

  /**
   * Method to get the games {@link Board}.
   *
   * @return this games {@link Board} object.
   */
  public Board getBoard() {
    return this.board;
  }

  /**
   * Finishes the current players turn, and sets the next player to take their turn.
   */
  public void finishTurn() {
    // check the tile the current player is on
    Tile currentTile = board.getTiles().get(playersController.getCurrentPlayer().getPosition());

    // check what typa tile it is, do the action if it is a special tile
    if (!currentTile.getTileType().equals(TileType.NORMAL.getTileType())) {
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
    Tile tile = board.getTiles().get(tileNumber);

    if (!tile.getTileType().equals(TileType.LADDER.getTileType())) {
      throw new IllegalArgumentException("Tile number " + tileNumber + " is not a LadderTile");
    }

    return ((LadderTile) tile).getDestinationTileNumber();
  }

  /**
   * Accesses the action that was last performed by a RandomActionTile represented as a String.
   *
   * @return the action that was last performed by a RandomActionTile represented as a String.
   */
  public String getLastRandomAction() {

    //this method is only called when the last special tile was a RandomActionTile
    Tile tile = board.getTiles().get(lastSpecialTile);

    if (!tile.getTileType().equals(TileType.RANDOM_ACTION.getTileType())) {
      throw new IllegalArgumentException(
          "Tile number " + playersController.getPreviousPlayer().getPosition()
              + " is not a RandomActionTile");
    }

    return ((RandomActionTile) tile).getTileAction();
  }

  @Override
  public void update(int i) {
    notifyObservers(i);
  }

  @Override
  public void addObserver(BoardGameObserver observer) {
    uiObservers.add(observer);
  }

  @Override
  public void removeObserver(BoardGameObserver observer) {
    uiObservers.remove(observer);
  }

  @Override
  public void notifyObservers(int i) {
    uiObservers.forEach(o -> o.update(i));
  }

}
