package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.DialogBox;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.HappeningDialogBox;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

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

    window.setTitle("Pario Marty");
  }

  @Override
  protected Node getBoardRegion() {

    int tileWidth = (800 - (2 * 29)) / 9;
    int tileHeight = (800 - (2 * 28)) / 10;

    this.boardDisplay.init(tileWidth, tileHeight, gameController.getBoard().getTileTypes());
    boardGrid.getChildren().add(this.boardDisplay.getComponent());

    Label turns = new Label("Turn 1 of 15");

    VBox boardVBox = new VBox();
    turns.getStyleClass().add("turns-label");

    boardVBox.getStyleClass().add("board-region");

    boardVBox.getChildren().addAll(turns, boardGrid);
    boardVBox.setPadding(new Insets(20, 0, 0, 0));

    return boardVBox;
  }

  @Override
  protected void initSidebar() {
    sidebar.setMinWidth(400);
    sidebar.setMinHeight(800);
    sidebar.setPadding(new javafx.geometry.Insets(20, 10, 20, 10));

    dialogBox = new HappeningDialogBox("fwæh");
    sidebar.setTop(dialogBox.getComponent());
    sidebar.setCenter(dieBox.getComponent());
    // TODO: Make a new leaderboard for ParioMarty
    sidebar.setBottom(null);

    dieBox.getRollDieButton().setOnAction(e -> {
      // TODO: add the whole complicated logic for rolling the die
    });

    sidebar.getStyleClass().add("sidebar");
  }

  @Override
  protected Timeline moveCurrentPlayerAnimation(int steps) {
    return null;
  }

  @Override
  protected void finishTurn() {
  }

}
