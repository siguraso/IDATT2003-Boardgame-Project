package edu.ntnu.idi.idatt.boardgame.view.window.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Abstract class for a dialog box that is displayed to the user.
 */
public abstract class DialogBox implements WindowComponent {

  /**
   * Gets the template for backdrop, speaker and dialog text for every dialog box.
   *
   * @param message the message to be displayed in the dialog box.
   * @return a node containing the dialog box.
   */
  protected Node getTemplate(String message) {
    VBox dialogBox = new VBox();

    //initialize dialog box
    dialogBox.getChildren().clear();
    dialogBox.setMinWidth(380);
    dialogBox.setMinHeight(200);
    dialogBox.getStyleClass().add("dialog-box");
    dialogBox.setAlignment(javafx.geometry.Pos.CENTER);
    dialogBox.setSpacing(0);

    // set padding on the bottom to make it more symmetrical when adding buttons in subclasses
    dialogBox.setPadding(new javafx.geometry.Insets(0, 0, 20, 0));

    // add image of the speaker that talks to the player in the dialog box
    ImageView speaker = new ImageView(new Image("file:src/main/resources/Images/placeholder.jpg"));
    speaker.setFitHeight(50);
    speaker.setFitWidth(50);

    // initialize dialog text box
    Label dialogText = new Label(message);

    // set size of the textbox
    dialogText.setMinWidth(260);
    dialogText.setMinHeight(130);
    dialogText.setMaxWidth(260);
    dialogText.setMaxHeight(130);

    dialogText.setWrapText(true);
    dialogText.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);
    dialogText.setPadding(new javafx.geometry.Insets(15, 15, 15, 15));
    dialogText.getStyleClass().add("dialog-text");

    // create a border pane to hold the speaker and dialog text
    BorderPane speakerSection = new BorderPane();
    speakerSection.getStyleClass().add("dialog-box");
    speakerSection.setLeft(speaker);
    BorderPane.setAlignment(speaker, javafx.geometry.Pos.CENTER);
    speakerSection.setRight(dialogText);
    speakerSection.getRight().prefWidth(300);
    speakerSection.getRight().prefHeight(130);
    speakerSection.setPadding(new javafx.geometry.Insets(20, 20, 20, 25));

    dialogBox.getChildren().add(speakerSection);
    dialogBox.getStyleClass().add("dialog-box");

    return dialogBox;
  }

}
