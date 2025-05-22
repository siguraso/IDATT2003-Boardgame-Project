package edu.ntnu.idi.idatt.boardgame.model.dice;

import edu.ntnu.idi.idatt.boardgame.observer.BoardGameObservable;
import edu.ntnu.idi.idatt.boardgame.observer.BoardGameObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class representing a die with a given number of sides.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public class Die implements BoardGameObservable {

  private final int sides;
  private final List<BoardGameObserver> observers = new ArrayList<>();
  protected final Random random = new Random();

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
   * Throws the {@link Die} and notifies the observers.
   */
  public void roll() {
    int[] currentThrow = new int[]{random.nextInt(1, sides + 1)};

    notifyObservers(currentThrow);
  }

  @Override
  public void addObserver(BoardGameObserver o) {
    if (o == null) {
      throw new NullPointerException("Observer cannot be null");
    }
    observers.add(o);
  }

  @Override
  public void removeObserver(BoardGameObserver o) {
    if (o == null) {
      throw new NullPointerException("Observer cannot be null");
    } else if (!observers.contains(o)) {
      throw new IllegalArgumentException("Observer not found");
    } else {
      observers.remove(o);
    }
  }

  @Override
  public void notifyObservers(int[] i) {
    try {
      observers.forEach(o -> o.update(i));
    } catch (NullPointerException e) {
      throw new NullPointerException("Illegal Update: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Illegal Update: " + e.getMessage());
    }
  }

  @Override
  public List<BoardGameObserver> getObservers() {
    return observers;
  }
}
