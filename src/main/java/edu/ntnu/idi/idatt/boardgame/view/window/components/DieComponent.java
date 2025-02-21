package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * A class that contructs the dice component for the game window.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class DieComponent implements WindowComponent {

  private final Die die;
  private ImageView diePlaceholder;

  /**
   * Constructor for the DiceComponent class.
   *
   * @param die The die object that returns a random number when rolled.
   */
  public DieComponent(Die die) {
    this.die = die;
  }

  @Override
  public Node getComponent() {
    // placeholder for die
    // TODO get actual sprites for the die
    diePlaceholder = new ImageView(
        new Image("file:src/main/resources/Images/placeholder.jpg"));
    diePlaceholder.setFitWidth(200);
    diePlaceholder.setFitHeight(200);
    // TODO make the button actually roll the die
    Button rollDieButton = new Button("Roll die");
    rollDieButton.setOnAction(e -> rollDieAction());

    VBox dieBox = new VBox(diePlaceholder, rollDieButton);
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

  private void rollDieAction() {
    this.die.throwDie();

    System.out.println("Die rolled: " + this.die.getCurrentThrow());

    switch (this.die.getCurrentThrow()) {
      case 1 -> {
        // TODO get actual sprites for the die
        diePlaceholder.setImage(new Image("file:src/main/resources/Images/placeholder2.png"));
      }
      case 2 -> {
        // TODO get actual sprites for the die
        diePlaceholder.setImage(new Image("file:src/main/resources/Images/placeholder.jpg"));
      }
      case 3 -> {
        // TODO get actual sprites for the die
        diePlaceholder.setImage(new Image("file:src/main/resources/Images/placeholder.jpg"));
      }
      case 4 -> {
        // TODO get actual sprites for the die
        diePlaceholder.setImage(new Image("file:src/main/resources/Images/placeholder2.png"));
      }
      case 5 -> {
        // TODO get actual sprites for the die
        diePlaceholder.setImage(new Image("file:src/main/resources/Images/placeholder.jpg"));
      }
      case 6 -> {
        // TODO get actual sprites for the die
        diePlaceholder.setImage(new Image("file:src/main/resources/Images/placeholder2.png"));
      }
    }


  }
}
