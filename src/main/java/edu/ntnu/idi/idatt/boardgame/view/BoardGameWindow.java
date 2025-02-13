package edu.ntnu.idi.idatt.boardgame.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class to create a window that displays a board game, with a sidebar.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class BoardGameWindow implements Window {

  private Stage window = new Stage();

  // methods for window initializing, opening and closing a window.

  @Override
  public void show() {
    window.show();
  }

  @Override
  public void init() {
    // Initialize the window
    Scene scene = new Scene(getBoardRegion(), 500, 500);
    scene.getStylesheets().add("file:src/main/resources/Styles/Style.css");

    window.setScene(scene);
  }

  @Override
  public void close() {
    window.close();
  }

  // individual methods for setting up different parts of the window.

  private StackPane getBoardRegion() {
    StackPane boardDisplay = new StackPane();
    boardDisplay.setLayoutX(0);
    boardDisplay.setLayoutY(0);
    boardDisplay.getStyleClass().add("root");

    ImageView boardImage = new ImageView(
        new Image("file:src/main/resources/Images/placeholder.jpg"));
    boardImage.setFitHeight(200);
    boardImage.setFitWidth(200);

    boardDisplay.getChildren().add(boardImage);

    return boardDisplay;
  }

  /*
  private VBox getSidebar() {

  }

   */

}
