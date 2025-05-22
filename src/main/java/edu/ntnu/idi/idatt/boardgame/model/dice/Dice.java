package edu.ntnu.idi.idatt.boardgame.model.dice;

import edu.ntnu.idi.idatt.boardgame.observer.BoardGameObserver;
import java.util.stream.IntStream;


/**
 * A class that represents two or more dice. This class extends the {@link Die} class and can be
 * used to represent multiple dice in a game, rather than only one.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public final class Dice extends Die {

  private final int numberOfDice;
  private final int sides;

  /**
   * Constructor for the Dice class.
   *
   * @param numberOfDice the number of dice to be used.
   * @param sides        the number of sides on each die.
   */
  public Dice(int numberOfDice, int sides) {
    super(sides);
    if (numberOfDice < 2) {
      throw new IllegalArgumentException("Dice must have at least two dice");
    }
    this.numberOfDice = numberOfDice;
    this.sides = sides;
  }

  @Override
  public void roll() {
    int[] rolls = new int[numberOfDice];

    IntStream.range(0, numberOfDice).forEach(i ->
        rolls[i] = random.nextInt(1, sides + 1)
    );

    notifyObservers(rolls);
  }

  @Override
  public void addObserver(BoardGameObserver o) {
    super.getObservers().add(o);
  }

  @Override
  public void removeObserver(BoardGameObserver o) {
    super.getObservers().remove(o);
  }

  @Override
  public void notifyObservers(int[] i) {
    if (i.length != numberOfDice) {
      throw new IllegalArgumentException("Number of dice rolled does not match the number of dice");
    }

    super.getObservers().forEach(o ->
        o.update(i)
    );
  }
}
