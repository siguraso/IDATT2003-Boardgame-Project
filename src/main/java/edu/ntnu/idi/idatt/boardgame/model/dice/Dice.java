package edu.ntnu.idi.idatt.boardgame.model.dice;

import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * A class that represents two or more dice. This class extends the {@link Die} class and can be
 * used to represent multiple dice in a game, rather than only one.
 */
public class Dice extends Die {

  private final int numberOfDice;
  private final int sides;

  private final ArrayList<BoardGameObserver> observers = new ArrayList<>();

  /**
   * Constructor for the Dice class.
   *
   * @param numberOfDice the number of dice to be used.
   * @param sides        the number of sides on each die.
   */
  public Dice(int numberOfDice, int sides) {
    super(sides);
    this.numberOfDice = numberOfDice;
    this.sides = sides;
  }

  @Override
  public void roll() {
    int[] rolls = new int[numberOfDice];

    IntStream.range(0, sides).forEach(i -> {
      rolls[i] = (int) (Math.random() * sides) + 1;
    });

    notifyObservers(rolls);
  }

  @Override
  public void addObserver(BoardGameObserver o) {
    observers.add(o);
  }

  @Override
  public void removeObserver(BoardGameObserver o) {
    observers.remove(o);
  }

  @Override
  public void notifyObservers(int[] i) {
    observers.forEach(o -> o.update(i));
  }

}
