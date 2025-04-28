package edu.ntnu.idi.idatt.boardgame.view.window.components;


import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObservable;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * A class that contructs the dice component for the game window.
 *
 * <p>This component contains:</p>
 *
 * <p>- A button to roll the {@link Die}</p>
 *
 * <p>- An image of the die that animates whenever the {@link Die} is rolled.</p>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class DieComponent implements WindowComponent, BoardGameObserver {

  // constant for the path to the die images
  private final String imagePath = "file:src/main/resources/Images/die-Faces/";
  private ImageView dieImage;
  private ImageView dieImage2;
  private Button rollDieButton = new Button("Roll die");
  private VBox dieBox;
  boolean useTwoDice = false;


  /**
   * Constructor for the DiceComponent class.
   *
   * @param observable the observable object that implements BoardGameObservable that this class
   *                   observes as the primary method of communication with the game logic.
   */
  public DieComponent(BoardGameObservable observable, boolean useTwoDice) {
    this.useTwoDice = useTwoDice;
    observable.addObserver(this);

    init();
  }

  private void init() {
    HBox diceContainer = new HBox();

    if (!useTwoDice) {
      dieImage = new ImageView(imagePath + "1.jpg");
      dieImage.setFitWidth(200);
      dieImage.setFitHeight(200);
      dieImage.getStyleClass().add("die-image");

      Rectangle clip = new Rectangle(dieImage.getFitWidth(), dieImage.getFitHeight());

      clip.setArcHeight(20);
      clip.setArcWidth(20);

      dieImage.setClip(clip);

      diceContainer.getChildren().add(dieImage);
    } else {
      dieImage = new ImageView(imagePath + "1.jpg");
      dieImage2 = new ImageView(imagePath + "2.jpg");

      dieImage.setFitWidth(150);
      dieImage.setFitHeight(150);
      dieImage2.setFitWidth(150);
      dieImage2.setFitHeight(150);

      Rectangle clip = new Rectangle(dieImage.getFitWidth(), dieImage.getFitHeight());
      Rectangle clip2 = new Rectangle(dieImage2.getFitWidth(), dieImage2.getFitHeight());

      clip.setArcHeight(15);
      clip.setArcWidth(15);
      clip2.setArcHeight(15);
      clip2.setArcWidth(15);

      dieImage.setClip(clip);
      dieImage2.setClip(clip2);

      diceContainer.getChildren().addAll(dieImage, dieImage2);
      diceContainer.setSpacing(30);
    }

    diceContainer.setAlignment(Pos.CENTER);

    rollDieButton = new Button("Roll die");

    dieBox = new VBox(diceContainer, rollDieButton);
    dieBox.setAlignment(javafx.geometry.Pos.CENTER);
    dieBox.setSpacing(20);
  }

  /**
   * Creates the animation for the dieImage.
   *
   * <p>This is a simple animation that changes the image of the die every 100ms for 1 second.</p>
   *
   * @return a {@link Timeline} object that contains the animation.
   */
  public Timeline dieAnimation() {
    Timeline timeline = new Timeline();

    if (useTwoDice) {
      IntStream.range(0, 10).forEach(i -> {
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(100 * i), event -> {
          int[] randomDieFaces = new int[]{(int) (Math.random() * 6) + 1,
              (int) (Math.random() * 6) + 1};

          dieImage.setImage(new Image(imagePath + randomDieFaces[0] + ".jpg"));
          dieImage2.setImage(new Image(imagePath + randomDieFaces[1] + ".jpg"));
        });
        timeline.getKeyFrames().add(keyFrame);
      });

    } else {
      IntStream.range(0, 10).forEach(i -> {
        KeyFrame keyFrame = new KeyFrame(javafx.util.Duration.millis(100 * i), event -> {
          int randomDieFace = (int) (Math.random() * 6) + 1;

          dieImage.setImage(new Image(imagePath + randomDieFace + ".jpg"));
        });
        timeline.getKeyFrames().add(keyFrame);
      });
    }

    return timeline;
  }

  @Override
  public Node getComponent() {
    return dieBox;
  }

  /**
   * Retrieves the roll die button.
   *
   * <p>This allows us to set the action event in the BoardGameWindow, meaning we can control the
   * game loop.</p>
   *
   * @return the roll die button.
   */
  public Button getRollDieButton() {
    return rollDieButton;
  }

  @Override
  public void update(int[] i) {
    if (!useTwoDice) {
      dieImage2.setImage(new Image(imagePath + i[1] + ".jpg"));
    }
    dieImage.setImage(new Image(imagePath + i[0] + ".jpg"));
  }

}
