package edu.ntnu.idi.idatt.boardgame.view;

import edu.ntnu.idi.idatt.boardgame.view.window.BoardGameWindow;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class of the application that launches the javaFX application.
 *
 * @author MagnusNaessanGaarder & siguraso
 * @version 1.0
 * @since 1.0
 */
public class UI extends Application {


  @Override
  public void start(Stage primaryStage) {
    BoardGameWindow window = new BoardGameWindow();

    window.init();
    window.show();
  }

  public void init() {
    // TODO: Initialize the application

    // Creating a stage

  }
}