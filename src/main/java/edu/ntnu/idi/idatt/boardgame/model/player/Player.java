package edu.ntnu.idi.idatt.boardgame.model.player;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;

/**
 * A player in a board game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class Player {

  private final String name;
  private Tile currentTile;
  private boolean isWinner = false;
  private final PlayerPieces piece;

  /**
   * Constructor for the Player class.
   *
   * @param name        The name of the player.
   * @param playerPiece The piece that the player uses on the board, as defined in the
   *                    {@link PlayerPieces} enum.
   */
  public Player(String name, PlayerPieces playerPiece) {
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
  }

  /**
   * Used to set a player as a winner of the game. This method is used when a player reaches a
   * WinnerTile.
   */
  public void setWinner() {
    this.isWinner = true;
  }

  /**
   * Used to get the {@link PlayerPieces} enum that represents the piece that the player uses on the
   * board.
   *
   * @return The piece that the player uses on the board.
   */
  public PlayerPieces getPiece() {
    return piece;
  }

}
