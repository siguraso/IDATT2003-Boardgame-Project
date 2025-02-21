package edu.ntnu.idi.idatt.boardgame;

import edu.ntnu.idi.idatt.boardgame.view.UI;
import static javafx.application.Application.launch;

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
    launch(UI.class, args);
  }
}
