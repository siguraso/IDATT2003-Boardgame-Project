package edu.ntnu.idi.idatt.boardgame.view.window;

import java.util.Objects;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * A class representing a warning dialog window. This window is shown when the user tries to perform
 * an action that is not allowed, such as loading an unsupported .json file.
 *
 * @author sigurdandris
 * @version 1.0
 * @since 1.0
 */
public class WarningDialogWindow implements Window {

  private final Stage window = new Stage();

  private final Label message = new Label();
  private final Label header = new Label();


  /**
   * Constructor for the WarningDialogWindow class. This constructor initializes the window and its
   * components.
   */
  public WarningDialogWindow() {
    init();
  }

  @Override
  public void show() {
    window.show();
  }

  @Override
  public void init() {
    message.setWrapText(true);
    message.setTextAlignment(TextAlignment.CENTER);
    message.setAlignment(Pos.CENTER);
    message.setMinWidth(300);

    BorderPane root = new BorderPane();
    root.setRight(message);

    header.getStyleClass().add("header");
    root.setTop(header);

    ImageView icon = new ImageView(new Image(
        Objects.requireNonNull(getClass().getResourceAsStream("/images/warning_icon.png"))));
    icon.setFitHeight(64);
    icon.setFitWidth(64);
    root.setLeft(icon);

    Button okButton = new Button("OK");
    okButton.setOnAction(e -> window.close());
    root.setBottom(okButton);

    root.getStyleClass().add("warning-dialog");

    BorderPane.setAlignment(okButton, javafx.geometry.Pos.CENTER);
    BorderPane.setAlignment(header, javafx.geometry.Pos.CENTER);
    BorderPane.setAlignment(message, javafx.geometry.Pos.CENTER);
    BorderPane.setAlignment(icon, javafx.geometry.Pos.CENTER);

    Scene scene = new Scene(root);
    scene.getStylesheets().add(
        Objects.requireNonNull(getClass().getResource("/Styles/Style.css")).toExternalForm());

    window.setMinWidth(400);
    window.setMinHeight(200);

    window.setResizable(false);
    window.setScene(scene);
  }

  @Override
  public void close() {
    window.close();
  }

  /**
   * Update the dialog window by setting the message and title.
   *
   * @param message     the message to be displayed to the user in the dialog.
   * @param windowTitle the title of the dialog window and the window header
   */
  public void update(String message, String windowTitle) {
    this.message.setText(message);
    header.setText(windowTitle);
    window.setTitle(windowTitle);
  }
}
