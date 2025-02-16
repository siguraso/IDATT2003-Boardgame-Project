package edu.ntnu.idi.idatt.boardgame.view;

import javafx.scene.Scene;

/**
 * Interface for displaying a window, includes basic methods like show and init.
 */
public interface Window {

  /**
   * Display the window
   */
  void show();

  /**
   * Method to initialize the window.
   */
  void init();

  /**
   * Method to close the window.
   */
  void close();
}
