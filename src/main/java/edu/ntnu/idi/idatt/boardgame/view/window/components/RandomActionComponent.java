package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.util.sound.SfxPlayer;
import edu.ntnu.idi.idatt.boardgame.util.sound.SoundFile;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


/**
 * UI component that displays a random action that gets picked from a list of actions.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class RandomActionComponent implements WindowComponent {

  private final ListView<String> actionsListView = new ListView<>();
  private final Timeline randomActionTimeline = new Timeline();

  /**
   * Constructor for the RandomActionComponent class.
   */
  public RandomActionComponent() {
    // Constructor implementation
  }

  @Override
  public Node getComponent() {

    actionsListView.getItems()
        .addAll("Return to start", "Roll again", "Swap spaces with a random player");

    actionsListView.setMaxHeight(100);
    actionsListView.setMaxWidth(400);

    // make the list view not selectable by mouse or keyboard
    actionsListView.setMouseTransparent(true);
    actionsListView.setFocusTraversable(false);

    actionsListView.getSelectionModel().select(0);

    VBox listViewContainer = new VBox();
    Label header = new Label("Random Happening!");
    header.setStyle("-fx-font-size: 20px; -fx-text-fill: text_wht;");
    listViewContainer.getChildren().addAll(header, actionsListView);

    listViewContainer.setAlignment(javafx.geometry.Pos.CENTER);
    listViewContainer.setSpacing(10);

    StackPane stackPane = new StackPane();
    stackPane.getChildren().add(listViewContainer);

    stackPane.getStyleClass().add("action-list");

    return stackPane;
  }

  /**
   * Plays a random action sequence animation by showing a list of actions and selecting the action
   * that was passed in at the end of the animation. Used to show the user what action the
   * RandomAction tile has performed.
   *
   * @param tileAction A String containing tile action to be selected at the end of the animation.
   */
  public void randomActionSequence(String tileAction) {
    SfxPlayer sfxPlayer = new SfxPlayer();

    randomActionTimeline.getKeyFrames().clear();

    var selectedWrapper = new Object() {
      int currentSelected = 0;
      final int finalSelected = actionsListView.getItems().indexOf(tileAction);
    };

    for (int i = 0; i < actionsListView.getItems().size() * 3; i++) {
      KeyFrame keyFrame = new KeyFrame(
          javafx.util.Duration.millis(i * 150),
          event -> {
            actionsListView.getSelectionModel().select(selectedWrapper.currentSelected);
            selectedWrapper.currentSelected++;
            if (selectedWrapper.currentSelected >= actionsListView.getItems().size()) {
              selectedWrapper.currentSelected = 0;
            }

            sfxPlayer.openSoundFile(SoundFile.RANDOM_ACTION_MOVE);
            sfxPlayer.playSound();
          });

      randomActionTimeline.getKeyFrames().add(keyFrame);
    }

    KeyFrame finalKeyFrame = new KeyFrame(
        javafx.util.Duration.millis(actionsListView.getItems().size() * 3 * 150),
        event -> {
          actionsListView.getSelectionModel().select(selectedWrapper.finalSelected);

          sfxPlayer.openSoundFile(SoundFile.RANDOM_ACTION_SELECT);
          sfxPlayer.playSound();
        });

    randomActionTimeline.getKeyFrames().add(finalKeyFrame);

    randomActionTimeline.play();
  }

  /**
   * Access the randomActionTimeline to stop setOnFinished.
   *
   * @return Timeline object
   */
  public Timeline getRandomActionTimeline() {
    return randomActionTimeline;
  }

}
