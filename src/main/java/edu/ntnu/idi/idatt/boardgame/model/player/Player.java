package edu.ntnu.idi.idatt.boardgame.model.player;

import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;

/**
 * A player in a board game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class Player implements BoardGameObserver {

  private final String name;
  private int position;
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
    this.position = 1;

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
  public int getPosition() {
    return position;
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
   * Used to moveForward a player to a new tile by adding the current dice throw to the player's
   * position number.
   *
   * @param steps integer with the number of tiles the player is to moveForward.
   */
  public void moveForward(int steps) {
    if (steps < 0) {
      throw new IllegalArgumentException(
          "Illegal moveForward: cannot moveForward negative spaces.");
    }

    if (position + steps > 90) {
      this.position = 90 - (position + steps - 90);

    } else {
      this.position += steps;

    }

  }

  /**
   * Move a player to a specific tile.
   *
   * @param tileNumber integer with the tile number the player is to move to.
   */
  public void moveTo(int tileNumber) {
    if (tileNumber < 0 || tileNumber > 90) {
      throw new IllegalArgumentException(
          "Illegal moveForward: cannot moveForward negative spaces or past 90.");
    }

    this.position = tileNumber;
  }

  /**
   * Used to set a player as a winner of the game. This method is used when a player reaches a
   * WinnerTile.
   */
  public void setWinner() {
    this.isWinner = true;
  }

  /**
   * Used to get the {@link PlayerPiece} enum that represents the piece that the player uses on the
   * board.
   *
   * @return The piece that the player uses on the board.
   */
  public PlayerPiece getPlayerPiece() {
    return piece;
  }

  @Override
  public void update(int i) {
    moveForward(i);
  }

}
