package edu.ntnu.idi.idatt.boardgame.view.controller;

import edu.ntnu.idi.idatt.boardgame.model.Observable;

public interface BoardGameObserver {
  void update(String prompt, Observable subject);
}
