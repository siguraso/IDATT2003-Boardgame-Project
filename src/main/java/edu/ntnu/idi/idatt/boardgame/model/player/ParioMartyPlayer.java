package edu.ntnu.idi.idatt.boardgame.model.player;

import edu.ntnu.idi.idatt.boardgame.exception.InvalidPlayerException;
import java.util.Arrays;

/**
 * A player in the ParioMarty game. This class extends the {@link Player} class and represents a
 * player in the ParioMarty game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class ParioMartyPlayer extends Player {

  int coins = 10;
  int crowns = 0;

  /**
   * Constructor for the ParioMartyPlayer class.
   *
   * @param name  The name of the player.
   * @param piece The piece that the player uses on the board, as defined in the {@link PlayerPiece}
   *              enum.
   */
  public ParioMartyPlayer(String name, PlayerPiece piece) {
    super(name, piece);
  }


  /**
   * Retrieves the number of coins the player has.
   *
   * @return The number of coins the player has.
   */
  public int getCoins() {
    return coins;
  }

  /**
   * Sets the number of coins the player has.
   *
   * @param coins The number of coins to set for the player.
   */
  public void addCoins(int coins) {
    if (coins < 0) {
      throw new IllegalArgumentException("Cannot add negative coins. Coins cannot be negative");
    }
    this.coins += coins;
  }

  /**
   * Removes a specified number of coins from the player.
   *
   * @param coins The number of coins to remove from the player.
   */
  public void removeCoins(int coins) {
    if (coins < 0) {
      throw new IllegalArgumentException("Cannot subtract negative coins. Coins cannot be negative");
    } else if (coins > this.coins) {
      throw new IllegalArgumentException(
          "Cannot subtract more coins than the player has. Coins cannot be negative");
    }
    this.coins -= coins;
  }

  /**
   * Retrieves the number of crowns the player has.
   *
   * @return The number of crowns the player has.
   */
  public int getCrowns() {
    return crowns;
  }

  /**
   * Sets the number of crowns the player has.
   *
   * @param crowns The number of crowns to set for the player.
   */
  public void addCrowns(int crowns) {
    if (crowns < 0) {
      throw new IllegalArgumentException("Cannot add negative crowns. Crowns cannot be negative");
    }
    this.crowns += crowns;
  }

  /**
   * Removes a specified number of crowns from the player.
   *
   * @param crowns The number of crowns to remove from the player.
   */
  public void removeCrowns(int crowns) {
    if (crowns < 0) {
      throw new IllegalArgumentException("Cannot subtract negative crowns. Crowns cannot be negative");
    } else if (crowns > this.crowns) {
      throw new IllegalArgumentException(
          "Cannot subtract more crowns than the player has. Crowns cannot be negative");
    }
    this.crowns -= crowns;
  }

  @Override
  public void handleLadderAction(int destination) {
    throw new InvalidPlayerException("ParioMartyPlayer cannot use ladder action");
  }

  @Override
  public void moveTo(int tileNumber) {
    if (tileNumber < 0 || tileNumber > 35) {
      throw new IllegalArgumentException(
          "Illegal Transport: cannot transport to a negative spaces or to tiles past 35.");
    }

    super.position = tileNumber;
  }

  @Override
  public void update(int[] i) {
    if (i == null) {
      throw new NullPointerException("Input cannot be null");
    } else if (i.length == 0) {
      throw new IllegalArgumentException("Input cannot be empty");
    } else if (i[0] <= 0) {
      throw new IllegalArgumentException("Input value must be greater than 0");
    } else {
      int steps = Arrays.stream(i).sum();
      if (position + steps > 35) {
        // if the player goes over 35, they go back to the start, and move forward the
        // remaining steps
        int delta = position + steps - 35 + 1;
        this.moveTo(delta);
      } else {

        moveForward(steps);
      }
    }
  }
}
