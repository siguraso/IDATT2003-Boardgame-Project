package edu.ntnu.idi.idatt.boardgame.view.window.components.helperComponents;

import edu.ntnu.idi.idatt.boardgame.view.window.BoardGameWindow;
import edu.ntnu.idi.idatt.boardgame.view.window.components.WindowComponent;
import java.io.InputStream;
import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * <h1>Abstract Class ~ HelperWindow.</h1>
 * A class that represents a helper window in the game. This class extends the {@link StackPane}
 * class and implements the {@link WindowComponent} interface.
 *
 * @author MagnusNaessanGaarder & siguraso
 * @version 1.0
 * @since 1.0
 */
public abstract class HelperWindow extends BorderPane implements WindowComponent {

  private final Label titleLabel;
  private final Label subTitleLabel;
  private final ImageView image;
  private final Label descriptionLabel;
  private final Scene scene;

  /**
   * Base constructor for the HelperWindow class. This class serves as a baseline for the other
   * helper windows.
   */
  protected HelperWindow() {
    titleLabel = new Label("Title");
    subTitleLabel = new Label("Subtitle");
    image = new ImageView();
    image.setFitWidth(400);
    image.setFitHeight(400);
    descriptionLabel = new Label("""
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut
        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
        laboris nisi ut aliquip. Duis aute irure dolor in reprehenderit in voluptate velit esse
        cillum dolore eu. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia
        deserunt. Mollit anim id est laborum.
        
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut
        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
        laboris nisi ut aliquip. Duis aute irure dolor in reprehenderit in voluptate velit esse
        cillum dolore eu. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia
        deserunt. Mollit anim id est laborum.""");
    this.scene = new Scene(this, 1100, 800);
    this.scene.getStylesheets().add(
        Objects.requireNonNull(BoardGameWindow.class.getResource("/Styles/Style.css"))
            .toExternalForm());
  }

  /**
   * Initializes the helper window. This method sets the style of the window and adds the title,
   * subtitle, description, and image to the window.
   */
  protected void init() {
    // Set the style
    this.getStyleClass().add("helper-window");
    titleLabel.getStyleClass().add("title-label");
    subTitleLabel.getStyleClass().add("subtitle-label");
    descriptionLabel.getStyleClass().add("description-label");

    HBox titleBox = new HBox();
    titleBox.getStyleClass().add("title-box");
    titleBox.getChildren().add(titleLabel);
    HBox.setHgrow(titleBox, javafx.scene.layout.Priority.ALWAYS);

    VBox textBox = new VBox();
    textBox.getStyleClass().add("text-box");
    textBox.getChildren().addAll(subTitleLabel, descriptionLabel);

    VBox imageBox = new VBox(image);

    BorderPane contentBox = new BorderPane();
    contentBox.getStyleClass().add("content-box");
    contentBox.setLeft(textBox);
    contentBox.setRight(imageBox);

    setTop(titleBox);
    setCenter(contentBox);
  }

  /**
   * Sets the title of the helper window.
   *
   * @param title a String containing the title of the helper window.
   */
  protected void setTitle(String title) {
    titleLabel.setText(title);
  }

  /**
   * Sets the subtitle of the helper window.
   *
   * @param subTitle a String containing the subtitle of the helper window.
   */
  protected void setSubTitle(String subTitle) {
    subTitleLabel.setText(subTitle);
  }

  /**
   * Sets the description of the helper window.
   *
   * @param description a String containing the description of the helper window.
   */
  protected void setDescription(String description) {
    descriptionLabel.setText(description);
  }

  /**
   * Sets the board image displayed in the helper window.
   *
   * @param image an {@link InputStream} that contains the image of the board that is to be
   *              displayed in the helper window.
   */
  protected void setImage(InputStream image) {
    this.image.setImage(new Image(image));
  }

  /**
   * Sets the scene of the helper window.
   *
   * @param element the {@link HelperWindow} element to be set as the scene.
   */
  protected void setScene(HelperWindow element) {
    this.scene.setRoot(element);
  }

  @Override
  public Node getComponent() {
    return this;
  }
}
