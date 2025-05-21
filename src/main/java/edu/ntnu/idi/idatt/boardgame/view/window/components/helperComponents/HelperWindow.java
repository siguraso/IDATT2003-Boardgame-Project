package edu.ntnu.idi.idatt.boardgame.view.window.components.helperComponents;

import edu.ntnu.idi.idatt.boardgame.view.window.components.WindowComponent;
import java.util.List;
import javafx.collections.ObservableList;
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
 * A class that represents a helper window in the game. This class extends the
 * {@link StackPane} class and implements the {@link WindowComponent} interface.
 *
 * @author MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public abstract class HelperWindow extends BorderPane implements WindowComponent {
  private Label titleLabel;
  private Label subTitleLabel;
  private ImageView image;
  private Label descriptionLabel;
  private Scene scene;

  public HelperWindow() {
    titleLabel = new Label("Title");
    subTitleLabel = new Label("Subtitle");
    image = new ImageView(new Image("file:src/main/resources/Images/placeholder.jpg"));
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
    this.scene.getStylesheets().add("file:src/main/resources/Styles/Style.css");
  }

  public HelperWindow(String title, String subTitle, String description, String image) {
    if (title == null || title.isEmpty()) {
      throw new IllegalArgumentException("Title cannot be null or empty");
    } else if (subTitle == null || subTitle.isEmpty()) {
      throw new IllegalArgumentException("Subtitle cannot be null or empty");
    } else if (description == null || description.isEmpty()) {
      throw new IllegalArgumentException("Description cannot be null or empty");
    } else {
      if (validateImage(image)) {
        this.titleLabel = new Label(title);
        this.subTitleLabel = new Label(subTitle);
        this.image = new ImageView(new Image(image));
        this.image.setFitWidth(400);
        this.image.setFitHeight(400);
        this.descriptionLabel = new Label(description);
        this.scene = new Scene(this, 1100, 800);
        this.scene.getStylesheets().add("file:src/main/resources/Styles/Style.css");
      }
    }
  }

  private boolean validateImage(String image) {
    if (image == null || image.isEmpty()) {
      throw new IllegalArgumentException("Image cannot be null or empty");
    } else {
      try {
        ImageView testImage = new ImageView(new Image(image));
        if (testImage.getImage().isError()) {
          throw new IllegalStateException("Image path is wrong. Image is not found in the project.");
        } else if (testImage.getImage() == null) {
          throw new IllegalStateException("Image is null. Image is not found in the project.");
        }
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Image cannot be null or empty");
      } catch (IllegalStateException e) {
        throw new IllegalArgumentException("Unexpected error occured: " + e.getMessage());
      }
    }
    return true;
  }

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

    BorderPane ContentBox = new BorderPane();
    ContentBox.getStyleClass().add("content-box");
    ContentBox.setLeft(textBox);
    ContentBox.setRight(imageBox);

    setTop(titleBox);
    setCenter(ContentBox);
  }

  protected void setTitle(String title) {
    titleLabel.setText(title);
  }

  protected void setSubTitle(String subTitle) {
    subTitleLabel.setText(subTitle);
  }

  protected void setDescription(String description) {
    descriptionLabel.setText(description);
  }

  protected void setImage(String image) {
    this.image.setImage(new Image(image));
  }

  protected ObservableList<Node> getContent() {
    return this.getChildren();
  }

  // Using this will render the previous setter-methods useless. Use for manual setting of content.
  protected void setContent(List<Node> content) {
    this.getChildren().clear();
    this.getChildren().addAll(content);
  }

  public Scene getSecondaryScene() {
    return scene;
  }
  protected void setScene(HelperWindow element) {
    this.scene.setRoot(element);
  }

  public Node getComponent() {
    return this;
  }
}
