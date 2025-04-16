package edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * A dialog that is displayed to the user when something happens in the game, of which the player
 * has no control over.
 */
public class HappeningDialogBox extends DialogBox {

  private final VBox dialogBox;
  private final Button confirmationButton = new Button("OK");

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
    dialogBox.getChildren().add(confirmationButton);
  }

  @Override
  public void refresh(String message) {
    dialogBox.getChildren().clear();
    dialogBox.getChildren().add(getTemplate(message));

    dialogBox.getChildren().add(confirmationButton);
  }

  /**
   * Accesses the confirmation {@link Button} in the dialog box. This allows the
   * {@link edu.ntnu.idi.idatt.boardgame.view.window.BoardGameWindow} class to choose what happens
   * when the {@link Button} is pressed.
   *
   * @return the confirmation {@link Button} in the dialog box.
   */
  public Button getConfirmationButton() {
    return confirmationButton;
  }
}