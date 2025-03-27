package edu.ntnu.idi.idatt.boardgame.model;

import edu.ntnu.idi.idatt.boardgame.controller.BoardGameObserver;

/**
 * <h1>Abstract class - Observable.</h1>
 *
 * <p>An observable object that can be observed by a {@link BoardGameObserver}.
 * This class is part of the Model component in a Model-View-Controller architecture.
 * </p>
 *
 * @version 1.0
 * @author Magnus Næssan Gaarder
 * @see BoardGameObserver
 * @since 1.0
 */
public abstract class Observable {
  /**
   * Adds an observer to the list of observers.
   *
   * @param o The observer to be added. Of type {@link BoardGameObserver}.
   */
  public abstract void addObserver(BoardGameObserver o);

  /**
   * Removes an observer from the list of observers.
   *
   * @param o The observer to be removed. Of type {@link BoardGameObserver}.
   */
  public abstract void removeObserver(BoardGameObserver o);

  /**
   * Notifies all observers in the list of observers.
   */
  public abstract void notifyObservers();
}
