package edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A dialog that is displayed to the user when something happens in the game, of which the player
 * has no control over.
 */
public class HappeningDialogBox extends DialogBox {

  private final VBox dialogBox;
  private final Button confirmationButton = new Button("OK");
  private final Button NoButton = new Button("No");
  private final Button YesButton = new Button("Yes");

  private final HBox yesNoButtons = new HBox();

  /**
   * Constructor for the HappeningDialogBox.
   */
  public HappeningDialogBox(String message) {
    dialogBox = (VBox) getTemplate(message);
  }

  @Override
  public Node getComponent() {
    init();

    yesNoButtons.setSpacing(10);
    yesNoButtons.setAlignment(Pos.CENTER);

    yesNoButtons.getChildren().addAll(YesButton, NoButton);

    return dialogBox;
  }

  private void init() {
    dialogBox.getChildren().add(confirmationButton);
  }

  @Override
  public void refresh(String message) {
    setMessage(message);
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

  /**
   * Accesses the yes {@link Button} in the dialog box. This allows the
   * {@link edu.ntnu.idi.idatt.boardgame.view.window.BoardGameWindow} class to choose what happens
   * when the {@link Button} is pressed.
   *
   * @return the yes {@link Button} in the dialog box.
   */
  public Button getYesButton() {
    return YesButton;
  }

  /**
   * Accesses the no {@link Button} in the dialog box. This allows the
   * {@link edu.ntnu.idi.idatt.boardgame.view.window.BoardGameWindow} class to choose what happens
   * when the {@link Button} is pressed.
   *
   * @return the no {@link Button} in the dialog box.
   */
  public Button getNoButton() {
    return NoButton;
  }

  /**
   * Changes the button mode of the dialog box to a yes/no dialog box. This is used when the user is
   * asked to confirm an action, such as buying a crown in the Pario Marty game.
   */
  public void showYesNoDialogBox() {
    dialogBox.getChildren().remove(confirmationButton);
    dialogBox.getChildren().add(yesNoButtons);
  }

  /**
   * Changes the button mode of the dialog box to an OK dialog box. This is what is typically
   * displayed during the game.
   */
  public void showOkDialogBox() {
    dialogBox.getChildren().remove(yesNoButtons);
    dialogBox.getChildren().add(confirmationButton);
  }


}