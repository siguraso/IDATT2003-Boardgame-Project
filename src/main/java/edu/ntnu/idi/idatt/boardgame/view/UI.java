package edu.ntnu.idi.idatt.boardgame.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The main class of the application that launches the javaFX application.
 *
 * @author MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public class UI extends Application {
  public void start() {
    // TODO: Write testdata
  }
  public void init() {
    // TODO: Initialize the application

    // Creating a stage
    Stage stage = new Stage();

    // Fetching dimensions of the screen
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double screenWidth = screenSize.getWidth();
    double screenHeight = screenSize.getHeight();

    // Creating a horizontal box
    HBox root = new HBox();
    root.getStyleClass().add("root");

    // Creating a scene
    Scene scene = new Scene(root, screenWidth, screenHeight);
    scene.getStylesheets().add("Style.css");
    stage.setScene(scene);

    stage.setTitle("Stage");

    stage.show();
  }


}
