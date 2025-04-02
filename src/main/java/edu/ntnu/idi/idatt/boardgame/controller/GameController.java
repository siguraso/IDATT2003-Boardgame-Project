package edu.ntnu.idi.idatt.boardgame.controller;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObservable;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * <h1>Class - GameController.</h1>
 *
 * <p>A controller-class to controll the flow of the game</p>
 *
 * @author Magnus Næssan Gaarder & siguraso
 * @version 1.0
 * @see BoardGameObserver
 * @since 1.0
 */
public class GameController implements BoardGameObserver, BoardGameObservable {

  private final Die die;
  private final PlayersController playersController;
  private final Board board;

  List<BoardGameObserver> UiObservers = new ArrayList<>();

  /**
   * Constructor for the GameController
   *
   * <p>This class controlls the flow of the game</p>
   *
   * @param die               The {@link Die} to be used in the game.
   * @param playersController The controller object for the players in the game.
   */
  public GameController(Die die, PlayersController playersController, Board board) {
    this.die = die;
    this.playersController = playersController;
    this.board = board;
    die.addObserver(this);
  }

  /**
   * Method to roll the die
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
   * Method to get the games {@link Die}.
   *
   * @return this games {@link Die} object.
   */
  public Die getDie() {
    return die;
  }

  @Override
  public void update(int i) {
    notifyObservers(i);
  }

  @Override
  public void addObserver(BoardGameObserver o) {
    UiObservers.add(o);
  }

  @Override
  public void removeObserver(BoardGameObserver o) {
    UiObservers.remove(o);
  }

  @Override
  public void notifyObservers(int i) {
    UiObservers.forEach(o -> o.update(i));
  }

}
