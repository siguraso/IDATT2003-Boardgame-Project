package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import edu.ntnu.idi.idatt.boardgame.view.window.BoardGameWindow;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * A class that contructs the dice component for the game window.
 * <p>This component contains:</p>
 * <p> - A button to roll the {@link Die}</p>
 * <p> - An image of the die that animates whenever the {@link Die} is rolled.</p>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class DieComponent implements WindowComponent {

  private final Die die;

  // parent window
  private final BoardGameWindow parentWindow;

  // constant for the path to the die images
  private final String IMAGE_PATH = "file:src/main/resources/Images/die-Faces/";
  private ImageView dieImage;
  private Button rollDieButton = new Button("Roll die");

  /**
   * Constructor for the DiceComponent class.
   */
  public DieComponent(BoardGameWindow parentWindow, Die die) {
    this.parentWindow = parentWindow;
    this.die = die;
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

    clip.setArcHeight(20);
    clip.setArcWidth(20);

    dieImage.setClip(clip);

    rollDieButton = new Button("Roll die");

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


  /**
   * Rolls the die and updates the image of the die corresponding with what number was rolled.
   * <p>This method is called when the roll die button is pressed.</p>
   */
  public void rollDieAction() {
    this.die.throwDie();

    dieImage.setImage(new Image(IMAGE_PATH + die.getCurrentThrow() + ".jpg"));
  }

  /**
   * Creates the animation for the dieImage.
   * <p>This is a simple animation that changes the image of the die every 100ms for 1 second.</p>
   *
   * @return a {@link Timeline} object that contains the animation.
   */
  public Timeline dieAnimation() {
    Timeline timeline = new Timeline();

    IntStream.range(0, 10).forEach(i -> {
      KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(100 * i), event -> {
        int randomDieFace = (int) (Math.random() * 6) + 1;

        dieImage.setImage(new Image(IMAGE_PATH + randomDieFace + ".jpg"));
      });
      timeline.getKeyFrames().add(keyFrame);
    });

    return timeline;
  }

  /**
   * Retrieves the {@link Die} object.
   * <p>This allows the BoardGameWindow to see what the die has rolled</p>
   *
   * @return the die object.
   */
  public Die getDie() {
    return die;
  }

  /**
   * Retrieves the roll die button.
   * <p>This allows us to set the action event in the BoardGameWindow, meaning we can control the
   * game loop.</p>
   *
   * @return
   */
  public Button getRollDieButton() {
    return rollDieButton;
  }
}
