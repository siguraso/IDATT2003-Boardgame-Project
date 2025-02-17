package edu.ntnu.idi.idatt.boardgame.view.window.components;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A dialog that is displayed to the user when something happens in the game, of which the player
 * has no control over.
 */
public class HappeningDialog implements DialogMessage {

  private String message;
  private final VBox dialogBox = new VBox();

  /**
   * Constructor for the HappeningDialog.
   */
  public HappeningDialog() {
  }

  @Override
  public Node getDialog(String message) {
    init(message);

    return dialogBox;
  }

  /**
   * initiates the dialog box.
   *
   * @param message The message that is displayed in the dialog box.
   */
  private void init(String message) {
    dialogBox.getChildren().clear();
    dialogBox.setMinWidth(380);
    dialogBox.setMinHeight(200);
    dialogBox.getStyleClass().add("dialog-box");
    dialogBox.setAlignment(javafx.geometry.Pos.CENTER);
    dialogBox.setSpacing(20);

    ImageView speaker = new ImageView(new Image("file:src/main/resources/Images/placeholder.jpg"));
    speaker.setFitHeight(50);
    speaker.setFitWidth(50);
    Label dialogText = new Label(message);
    dialogText.getStyleClass().add("dialog-text");

    HBox speakerText = new HBox(speaker, dialogText);
    speakerText.getStyleClass().add("dialog-box");
    speakerText.setAlignment(javafx.geometry.Pos.CENTER);
    speakerText.setSpacing(20);

    dialogBox.getChildren().add(speakerText);

    Button ConfirmButton = new Button("OK");
    ConfirmButton.getStyleClass().add("button");

    dialogBox.getChildren().add(ConfirmButton);
  }
}