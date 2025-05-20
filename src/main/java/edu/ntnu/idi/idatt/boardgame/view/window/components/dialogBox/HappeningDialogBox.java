package edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox;

import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A dialog that is displayed to the user when something happens in the game, of which the player
 * has no control over.
 */
public class HappeningDialogBox extends DialogBox {

  private final VBox dialogBox;
  private final Button confirmationButton = new Button("OK");
  private final Button helperButton = new Button("Help");

  /**
   * Constructor for the HappeningDialogBox.
   */
  public HappeningDialogBox(String message, BoardType boardType) {
    super(boardType);
    dialogBox = (VBox) getTemplate(message);
  }

  @Override
  public Node getComponent() {
    init();

    return dialogBox;
  }

  private void init() {
    dialogBox.getChildren().add(confirmationButton);
    dialogBox.getChildren().add(helperButton);
    helperButton.setOnAction(e -> {
      Stage helpStage = new Stage();
      helpStage.setScene(getHelpWindow().getScene());
      helpStage.setTitle("Help");
      helpStage.setResizable(true);
      helpStage.show();
    });
    dialogBox.setSpacing(10);
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
}