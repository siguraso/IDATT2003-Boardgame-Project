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
 * UserInterface component that displays a random action that gets picked from a list of actions.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class RandomActionComponent implements WindowComponent {

  protected final ListView<String> actionsListView = new ListView<>();
  private final Timeline randomActionTimeline = new Timeline();
  protected final VBox listViewContainer = new VBox();
  protected final Label header = new Label("Random Happening!");


  @Override
  public Node getComponent() {

    actionsListView.getItems()
        .addAll("Return to start", "Roll again", "Move to a random tile",
            "Swap spaces with a random player");

    // make the list view not selectable by mouse or keyboard
    actionsListView.setMouseTransparent(true);
    actionsListView.setFocusTraversable(false);

    actionsListView.getSelectionModel().select(0);

    // from css sheet
    int listHeight = 30;
    actionsListView.setMaxHeight(listHeight * actionsListView.getItems().size() + (double) 3);
    actionsListView.setPrefHeight(listHeight * actionsListView.getItems().size() + (double) 3);

    header.getStyleClass().add("header");
    header.setStyle("-fx-text-fill: text_wht;");

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
   * @param tileAction  A String containing tile action to be selected at the end of the animation.
   * @param moveSound   A SoundFile object containing the sound to be played when moving to a new
   *                    action.
   * @param selectSound A SoundFile object containing the sound to be played when selecting an
   *                    action.
   * @param showSound   A SoundFile object containing the sound to be played when showing the
   *                    action.
   */
  public void randomActionSequence(String tileAction, SoundFile moveSound, SoundFile selectSound,
      SoundFile showSound) {
    SfxPlayer sfxPlayer = new SfxPlayer();

    randomActionTimeline.getKeyFrames().clear();

    var selectedWrapper = new Object() {
      int currentSelected = 0;
      final int finalSelected = actionsListView.getItems().indexOf(tileAction);
    };

    for (int i = 0; i < actionsListView.getItems().size() * 3; i++) {
      KeyFrame keyFrame = new KeyFrame(
          javafx.util.Duration.millis(i * (double) 150),
          event -> {
            actionsListView.getSelectionModel().select(selectedWrapper.currentSelected);
            selectedWrapper.currentSelected++;
            if (selectedWrapper.currentSelected >= actionsListView.getItems().size()) {
              selectedWrapper.currentSelected = 0;
            }

            sfxPlayer.openSoundFile(moveSound);
            sfxPlayer.playSound();
          });

      randomActionTimeline.getKeyFrames().add(keyFrame);
    }

    KeyFrame keyFrame = new KeyFrame(
        javafx.util.Duration.millis(actionsListView.getItems().size() * (double) 3 * 150),
        event -> {
          actionsListView.getSelectionModel().select(selectedWrapper.finalSelected);

          sfxPlayer.openSoundFile(selectSound);
          sfxPlayer.playSound();
        });

    randomActionTimeline.getKeyFrames().add(keyFrame);

    KeyFrame finalKeyFrame = new KeyFrame(
        javafx.util.Duration.millis(actionsListView.getItems().size() * (double) 3 * 150 + 700),
        event -> {
          listViewContainer.getChildren().remove(actionsListView);

          header.setText(tileAction);

          sfxPlayer.openSoundFile(showSound);
          sfxPlayer.playSound();
        });

    randomActionTimeline.getKeyFrames().add(finalKeyFrame);

    // create a dummy keyframe to show the finalKeyFrame, since the program just ignores the last
    // keyframe if it is not followed by a dummy keyframe
    KeyFrame dummyKeyFrame = new KeyFrame(
        javafx.util.Duration.millis(actionsListView.getItems().size() * (double) 3 * 150 + 1500),
        event -> {
        });

    randomActionTimeline.getKeyFrames().add(dummyKeyFrame);

    randomActionTimeline.playFromStart();
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
