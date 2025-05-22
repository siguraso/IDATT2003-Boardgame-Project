package edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox;

import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.view.window.components.WindowComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.helperComponents.HelperWindow;
import edu.ntnu.idi.idatt.boardgame.view.window.components.helperComponents.LaddergameHelper;
import edu.ntnu.idi.idatt.boardgame.view.window.components.helperComponents.ParioMartyHelper;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Abstract class for a dialog box that is displayed to the user.
 */
public abstract class DialogBox implements WindowComponent {

  private final Label dialogText = new Label();
  private final HelperWindow helpWindow;
  private final Button helperButton = new Button("?");

  public DialogBox(BoardType boardType) {
    if (boardType == BoardType.PARIO_MARTY) {
      this.helpWindow = new ParioMartyHelper();
    } else if (boardType == BoardType.LADDER_GAME_REGULAR) {
      this.helpWindow = new LaddergameHelper("Regular");
    } else {
      this.helpWindow = new LaddergameHelper("Special");
    }
  }

  protected HelperWindow getHelpWindow() {
    return helpWindow;
  }

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

    ImageView speaker = new ImageView(new Image(
        Objects.requireNonNull(getClass().getResourceAsStream("/Images/speaker.png"))));

    speaker.setFitHeight(50);
    speaker.setFitWidth(50);

    VBox speakerBox = new VBox();
    speakerBox.setMaxHeight(50);
    speakerBox.setPrefWidth(50);

    speakerBox.getStyleClass().add("speaker-box");
    speakerBox.getChildren().add(speaker);

    VBox speakerHelpBox = new VBox();
    speakerHelpBox.getChildren().addAll(speakerBox, helperButton);
    speakerHelpBox.setAlignment(javafx.geometry.Pos.CENTER);
    speakerHelpBox.setSpacing(20);

    helperButton.setOnAction(e -> {
      Stage helpStage = new Stage();
      helpStage.setScene(getHelpWindow().getScene());
      helpStage.setTitle("Help");
      helpStage.setResizable(true);
      helpStage.show();
    });

    helperButton.getStyleClass().add("helper-button");

    // initialize dialog text box
    dialogText.setText(message);

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
    speakerSection.setLeft(speakerHelpBox);
    BorderPane.setAlignment(speakerHelpBox, javafx.geometry.Pos.CENTER);
    speakerSection.setRight(dialogText);
    speakerSection.getRight().prefWidth(300);
    speakerSection.getRight().prefHeight(130);
    speakerSection.setPadding(new javafx.geometry.Insets(20, 20, 20, 25));

    dialogBox.getChildren().add(speakerSection);
    dialogBox.getStyleClass().add("dialog-box");

    return dialogBox;
  }

  /**
   * Updates the dialog box message label.
   *
   * @param message the message to be displayed in the dialog box.
   */
  protected void setMessage(String message) {
    dialogText.setText(message);
  }

  /**
   * Refreshes the dialog box with a new message.
   *
   * @param message the message to be displayed in the dialog box.
   */
  public void refresh(String message) {
    // This method is intentionally left blank. Subclasses should override this method to refresh
    // the dialog box with a new message.
  }

}
