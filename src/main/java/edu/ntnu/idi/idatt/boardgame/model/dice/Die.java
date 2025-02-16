package edu.ntnu.idi.idatt.boardgame.model.dice;

/**
 * A class representing a die with a given number of sides.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class Die {

  private int currentThrow;
  private final int sides;

  /**
   * Creates a new die with the given number of sides.
   *
   * @param sides the amount of sides on the die.
   */
  public Die(int sides) {
    if (sides < 2) {
      throw new IllegalArgumentException("A die must have at least two sides");
    }

    this.sides = sides;
  }

  /**
   * Throws the die and returns the result.
   *
   * @return the result of the throw.
   */
  public int throwDie() {
    this.currentThrow = (int) (Math.random() * sides) + 1;
    return this.currentThrow;
  }

  /**
   * Returns the last throw of the die.
   *
   * @return the last throw of the die.
   */
  public int getCurrentThrow() {
    return currentThrow;
  }
}
