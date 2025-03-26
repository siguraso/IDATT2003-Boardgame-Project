package edu.ntnu.idi.idatt.boardgame.view.controller;

import edu.ntnu.idi.idatt.boardgame.model.Observable;

/**
 * <h1>Interface - BoardGameObserver.</h1>
 *
 * <p>An observer interface for the Model-View-Controller architecture.
 * This interface is used to observe an {@link Observable} object.
 * </p>
 *
 * @version 1.0
 * @since 1.0
 * @see Observable
 * @author Magnus Næssan Gaarder
 */
public interface BoardGameObserver {
  /**
   * Updates observer based on the subject passed as an argument.
   *
   * @param subject The subject that the observer is observing.
   */
  void update(Observable subject);
}
