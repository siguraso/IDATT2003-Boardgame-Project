package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidBoardException;
import edu.ntnu.idi.idatt.boardgame.exception.MalformedBoardException;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardFactory;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.MowserTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.RandomActionTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.model.dice.Dice;
import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObservable;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for the game controller. This class is responsible for controlling the flow of the
 * game, including rolling the die, moving players, and handling special tiles.
 *
 * @author Magnus Næssan Gaarder & siguraso
 * @version 1.0
 * @see BoardGameObserver
 * @since 1.0
 */
public abstract class GameController implements BoardGameObserver, BoardGameObservable {

  protected final Die die;
  protected final PlayersController playersController;
  protected Board board;

  protected final ArrayList<BoardGameObserver> uiObservers = new ArrayList<>();
  protected int lastSpecialTile;

  /**
   * Constructor for the GameController.
   *
   * <p>This class controls the flow of the game</p>
   *
   * @param playersController The controller object for the players in the game.
   */
  protected GameController(PlayersController playersController, boolean useTwoDice) {
    this.playersController = playersController;

    die = useTwoDice ? new Dice(2, 6) : new Die(6);

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
  public void setBoard(BoardType boardType, boolean useJson, String filePath) {
    try {
      this.board = BoardFactory.createBoard(boardType, useJson, filePath);
    } catch (InvalidBoardException e) {
      throw new InvalidBoardException(e.getMessage());
    } catch (MalformedBoardException e) {
      throw new MalformedBoardException("The board was could not be created: \n" + e.getMessage());
    }
  }

  /**
   * Method to roll the die.
   */
  public void rollDice() {
    die.roll();
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
  public abstract void finishTurn();

  /**
   * Accesses the action last performed by a RandomActionTile represented as a String.
   *
   * @return the action last performed by a RandomActionTile represented as a String.
   */
  public int getLastRandomAction() {

    // this method is only called when the last special tile was a RandomActionTile
    Tile tile = board.tiles().get(lastSpecialTile);

    if (!tile.getTileType().equals(TileType.RANDOM_ACTION.getTileType())
        && !tile.getTileType().equals(TileType.MOWSER.getTileType())) {
      throw new IllegalArgumentException(
          "Tile number " + playersController.getPreviousPlayer().getPosition()
              + " is not a RandomActionTile or a MowserTile");
    }

    if (tile.getTileType().equals(TileType.MOWSER.getTileType())) {
      return ((MowserTile) tile).getTileAction();
    }

    return ((RandomActionTile) tile).getTileAction();
  }

  /**
   * Accesses the name of the player that was swapped with using the SwapPlayersAction.
   *
   * @return a String containing the name of the player that was swapped with.
   */
  public String getLastSwappedPlayer() {

    //this method is only called when the last special tile was a RandomActionTile
    Tile tile = board.tiles().get(lastSpecialTile);

    if (!tile.getTileType().equals(TileType.RANDOM_ACTION.getTileType())) {
      throw new IllegalArgumentException(
          "Tile number " + playersController.getPreviousPlayer().getPosition()
              + " is not a RandomActionTile");
    }

    return ((RandomActionTile) tile).getPlayerToSwapWith().getName();
  }

  /**
   * Checks if the game has reached a winning state.
   *
   * @return true if the game has reached a winning state, false otherwise.
   */
  public abstract boolean isGameOver();

  @Override
  public void update(int[] i) {
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
  public void notifyObservers(int[] i) {
    uiObservers.forEach(o -> o.update(i));
  }

  @Override
  public List<BoardGameObserver> getObservers() {
    return uiObservers;
  }

}
