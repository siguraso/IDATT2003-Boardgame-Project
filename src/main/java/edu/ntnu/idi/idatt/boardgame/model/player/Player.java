package edu.ntnu.idi.idatt.boardgame.model.player;

import edu.ntnu.idi.idatt.boardgame.model.Observable;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.view.controller.BoardGameObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * A player in a board game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class Player extends Observable {
  private final List<BoardGameObserver> observerList = new ArrayList<>();

  private final String name;
  private Tile currentTile;
  private boolean isWinner = false;
  private final PlayerPiece piece;

  /**
   * Constructor for the Player class.
   *
   * @param name        The name of the player.
   * @param playerPiece The piece that the player uses on the board, as defined in the
   *                    {@link PlayerPiece} enum.
   */
  public Player(String name, PlayerPiece playerPiece) {
    this.name = name;
    this.piece = playerPiece;
  }

  // accessor methods

  /**
   * Used to get the name of the player.
   *
   * @return The name of the player.
   */
  public String getName() {
    return name;
  }

  /**
   * Used to get the tile that the player is currently on.
   *
   * @return The current tile of the player.
   */
  public Tile getCurrentTile() {
    return currentTile;
  }

  /**
   * Used to check if the player is a winner of the game.
   *
   * @return Boolean, true if the player is a winner, false otherwise.
   */
  public boolean isWinner() {
    return isWinner;
  }

  // mutator methods

  /**
   * Used to move a player to a new tile by setting the players current tile to a new tile.
   *
   * @param newTile The new tile that the player moves to.
   */
  public void move(Tile newTile) {
    this.currentTile = newTile;
    notifyObservers(); // Notify observers that the player has moved
  }

  /**
   * Used to set a player as a winner of the game. This method is used when a player reaches a
   * WinnerTile.
   */
  public void setWinner() {
    this.isWinner = true;
    notifyObservers(); // Notify observers that the player is a winner
  }

  /**
   * Used to get the {@link PlayerPiece} enum that represents the piece that the player uses on the
   * board.
   *
   * @return The piece that the player uses on the board.
   */
  public PlayerPiece getPieceType() {
    return piece;
  }

  @Override
  public void addObserver(BoardGameObserver o) {
    observerList.add(o);
  }

  @Override
  public void removeObserver(BoardGameObserver o) {
    observerList.remove(o);
  }

  @Override
  public void notifyObservers() {
    for (BoardGameObserver observer : observerList) {
      observer.update(this);
    }
  }

}
