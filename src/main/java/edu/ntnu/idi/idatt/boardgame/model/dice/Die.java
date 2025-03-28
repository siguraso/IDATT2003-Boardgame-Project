package edu.ntnu.idi.idatt.boardgame.model.dice;

import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObservable;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a die with a given number of sides.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class Die implements BoardGameObservable {

  private final int sides;

  private final List<BoardGameObserver> observers = new ArrayList<>();

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
   * Throws the die and notifies the observers.
   */
  public void throwDie() {
    int currentThrow = (int) (Math.random() * sides) + 1;
    notifyObservers(currentThrow);
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
  public void notifyObservers(int i) {
    observers.forEach(o -> o.update(i));
  }
}
