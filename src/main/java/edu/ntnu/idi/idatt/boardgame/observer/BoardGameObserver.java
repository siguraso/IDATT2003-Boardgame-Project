package edu.ntnu.idi.idatt.boardgame.observer;

/**
 * <h1>Interface - BoardGameObserver.</h1>
 *
 * <p>An observer interface for the Model-View-Controller architecture.
 * This interface is used to observe an {@link BoardGameObservable} object.
 * </p>
 *
 * @author Magnus Næssan Gaarder
 * @version 1.0
 * @see BoardGameObservable
 * @since 1.0
 */
public interface BoardGameObserver {

  /**
   * Updates observer based on the subject passed as an argument.
   *
   * @param i an integer.
   */
  void update(int[] i);
}
