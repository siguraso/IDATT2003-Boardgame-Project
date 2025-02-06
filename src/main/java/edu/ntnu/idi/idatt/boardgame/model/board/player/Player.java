package edu.ntnu.idi.idatt.boardgame.model.board.player;

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

  /**
   * Constructor for the Player class.
   *
   * @param name The name of the player.
   */
  public Player(String name) {
    this.name = name;
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

  // mutator methods

  /**
   * Used to move a player to a new tile by setting the players current tile to a new tile.
   *
   * @param newTile The new tile that the player moves to.
   */
  public void move(Tile newTile) {
    currentTile = newTile;
  }

}
