package edu.ntnu.idi.idatt.boardgame.model.observerPattern;

import java.util.List;

/**
 * <h1>Abstract class - Observable.</h1>
 *
 * <p>An observable object that can be observed by a {@link BoardGameObserver}.
 * This class is part of the Model component in a Model-View-Controller architecture.
 * </p>
 *
 * @author Magnus Næssan Gaarder
 * @version 1.0
 * @see BoardGameObserver
 * @since 1.0
 */
public interface BoardGameObservable {

  /**
   * Adds an observer to the list of observers.
   *
   * @param o The observer to be added. Of type {@link BoardGameObserver}.
   */
  void addObserver(BoardGameObserver o);

  /**
   * Removes an observer from the list of observers.
   *
   * @param o The observer to be removed. Of type {@link BoardGameObserver}.
   */
  void removeObserver(BoardGameObserver o);

  /**
   * Notifies all observers in the list of observers.
   *
   * @param i an integer.
   */
  void notifyObservers(int[] i);

  /**
   * Get-method to get all observers.
   */
  List<BoardGameObserver> getObservers();
}
