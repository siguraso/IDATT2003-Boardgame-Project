package edu.ntnu.idi.idatt.boardgame.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

  private final Stage window = new Stage();

  // methods for window initializing, opening and closing a window.

  @Override
  public void show() {
    window.show();
  }

  @Override
  public void init() {
    // Initialize the window
    BorderPane root = new BorderPane();
    root.setLeft(getBoardRegion());
    root.setRight(getSidebar());

    Scene scene = new Scene(root, 1400, 800);

    scene.getStylesheets().add("file:src/main/resources/Styles/Style.css");

    window.setResizable(false);
    window.setScene(scene);
  }

  @Override
  public void close() {
    window.close();
  }

  // individual methods for setting up different parts of the window.

  private StackPane getBoardRegion() {
    StackPane boardDisplay = new StackPane();
    boardDisplay.setMinWidth(1000);
    boardDisplay.setMinHeight(800);
    boardDisplay.getStyleClass().add("root");

    ImageView boardImage = new ImageView(
        new Image("file:src/main/resources/Images/placeholder.jpg"));
    boardImage.setFitHeight(700);
    boardImage.setFitWidth(700);

    boardDisplay.getChildren().add(boardImage);

    return boardDisplay;
  }

  private VBox getSidebar() {
    VBox sidebar = new VBox();
    sidebar.setMinWidth(400);
    sidebar.setPadding(new javafx.geometry.Insets(20, 10, 20, 10));
    sidebar.setSpacing(20);
    sidebar.setAlignment(javafx.geometry.Pos.CENTER);

    // placeholder for dialog box
    // TODO make a dialog box class that can be defined in the constructor
    ImageView dialogPlaceholder = new ImageView(
        new Image("file:src/main/resources/Images/placeholder.jpg"));
    // IMPORTANT: dimentions of dialogbox: 380x200
    dialogPlaceholder.setFitWidth(380);
    dialogPlaceholder.setFitHeight(200);

    // placeholder for die
    // TODO get actual sprites for the die
    ImageView diePlaceholder = new ImageView(
        new Image("file:src/main/resources/Images/placeholder.jpg"));
    diePlaceholder.setFitWidth(200);
    diePlaceholder.setFitHeight(200);

    // TODO make the button actually roll the die
    Button rollDieButton = new Button("Roll die");
    rollDieButton.getStyleClass().add("button");
    rollDieButton.setOnAction(e -> {
      rollDieButton.setDisable(true);
      diePlaceholder.setImage(new Image("file:src/main/resources/Images/placeholder2.png"));
    });

    sidebar.getChildren().addAll(dialogPlaceholder, diePlaceholder, rollDieButton);

    sidebar.getStyleClass().add("sidebar");
    return sidebar;
  }

}
