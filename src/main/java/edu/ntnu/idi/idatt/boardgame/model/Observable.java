package edu.ntnu.idi.idatt.boardgame.model;

import edu.ntnu.idi.idatt.boardgame.view.controller.BoardGameObserver;

public abstract class Observable {
  abstract void addObserver(BoardGameObserver o);

  abstract void removeObserver(BoardGameObserver o);

  abstract void notifyObservers();
}
