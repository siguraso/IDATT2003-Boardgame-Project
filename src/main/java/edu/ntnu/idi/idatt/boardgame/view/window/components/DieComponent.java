package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import edu.ntnu.idi.idatt.boardgame.util.WaitTime;
import javafx.scene.shape.Rectangle;
import javax.swing.JPasswordField;

/**
 * A class that contructs the dice component for the game window.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class DieComponent implements WindowComponent {

  private final Die die = new Die(6);
  private ImageView dieImage;
  private Button rollDieButton = new Button("Roll die");

  // constant for the path to the die images
  private final String IMAGE_PATH = "file:src/main/resources/Images/die-Faces/";

  /**
   * Constructor for the DiceComponent class.
   */
  public DieComponent() {

  }

  @Override
  public Node getComponent() {
    dieImage = new ImageView(IMAGE_PATH + "1.jpg");
    dieImage.setFitWidth(200);
    dieImage.setFitHeight(200);
    dieImage.getStyleClass().add("die-image");

    Rectangle clip = new Rectangle(
        dieImage.getFitWidth(), dieImage.getFitHeight()
    );
    clip.setStyle("-fx-background-radius: 10; -fx-border-radius: 10;");

    dieImage.setClip(clip);


    rollDieButton = new Button("Roll die");
    rollDieButton.setOnAction(onPress -> {
      Timeline dieAnimation = new Timeline(
          new KeyFrame(javafx.util.Duration.millis(200), animation ->
              dieImage.setImage(new Image(IMAGE_PATH + "dice-animation.gif"))
          ));

      dieAnimation.play();

    });

    VBox dieBox = new VBox(dieImage, rollDieButton);
    dieBox.setAlignment(javafx.geometry.Pos.CENTER);
    dieBox.setSpacing(20);

    return dieBox;
  }

  /**
   * Retrieves the value of the last die throw.
   *
   * @return and int containing the value of the last die throw.
   */
  public int getCurrentThrow() {
    return this.die.getCurrentThrow();
  }

  public void enableRollDieButton() {
    rollDieButton.setDisable(false);
  }

  private void rollDieAction() {
    this.die.throwDie();

    System.out.println("Die rolled: " + this.die.getCurrentThrow());

    switch (this.die.getCurrentThrow()) {
      case 1 -> dieImage.setImage(new Image(IMAGE_PATH + "1.jpg"));

      case 2 -> dieImage.setImage(new Image(IMAGE_PATH + "2.jpg"));

      case 3 -> dieImage.setImage(new Image(IMAGE_PATH + "3.jpg"));

      case 4 -> dieImage.setImage(new Image(IMAGE_PATH + "4.jpg"));

      case 5 -> dieImage.setImage(new Image(IMAGE_PATH + "5.jpg"));

      case 6 -> dieImage.setImage(new Image(IMAGE_PATH + "6.jpg"));

    }
  }
}
