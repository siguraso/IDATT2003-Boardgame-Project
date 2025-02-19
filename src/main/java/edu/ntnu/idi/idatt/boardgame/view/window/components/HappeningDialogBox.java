package edu.ntnu.idi.idatt.boardgame.view.window.components;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * A dialog that is displayed to the user when something happens in the game, of which the player
 * has no control over.
 */
public class HappeningDialogBox extends DialogBox {

  private final VBox dialogBox;

  /**
   * Constructor for the HappeningDialogBox.
   */
  public HappeningDialogBox(String message) {
    dialogBox = (VBox) getTemplate(message);
  }

  @Override
  public Node getComponent() {
    init();

    return dialogBox;
  }

  private void init() {
    Button confirmButton = new Button("OK");

    dialogBox.getChildren().add(confirmButton);
  }
}