package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import javafx.animation.Timeline;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class ParioMartyGameWindow extends BoardGameWindow {

  /**
   * Constructor for the ParioMartyGameWindow class.
   *
   * @param gameController the {@link GameController} object that controls the game and its players
   *                       through the PlayerController
   * @param useTwoDice     boolean indicating whether to use two dice or not
   */
  public ParioMartyGameWindow(GameController gameController, boolean useTwoDice) {
    super(gameController, useTwoDice);
  }

  @Override
  public void init() {

  }

  @Override
  protected StackPane getBoardRegion() {
    return null;
  }

  @Override
  protected BorderPane getSidebar() {
    return null;
  }

  @Override
  protected Timeline moveCurrentPlayerAnimation(int steps) {
    return null;
  }

  @Override
  protected void finishTurn() {
  }

}
