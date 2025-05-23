package edu.ntnu.idi.idatt.boardgame;

import static javafx.application.Application.launch;

import edu.ntnu.idi.idatt.boardgame.view.UserInterface;

/**
 * The main class of the application that launches the javaFX application.
 *
 * @author siguraso & MagnusNaesanGaarder
 * @version 1.0
 * @since 1.0
 */
public class BoardGameApp {

  /**
   * The main method that launches the javaFX application.
   *
   * @param args The arguments passed to the application.
   */
  public static void main(String[] args) {
    launch(UserInterface.class, args);
  }
}
