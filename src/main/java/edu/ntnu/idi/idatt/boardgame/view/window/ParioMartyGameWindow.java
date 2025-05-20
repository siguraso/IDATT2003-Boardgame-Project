package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.util.sound.SoundFile;
import edu.ntnu.idi.idatt.boardgame.view.window.components.ParioMartyLeaderBoard;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.HappeningDialogBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * A class that constructs the game window for the Pario Marty game. This class extends the
 * {@link BoardGameWindow} class and implements the game-specific UI logic for the Pario Marty
 * game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class ParioMartyGameWindow extends BoardGameWindow {

  private ParioMartyLeaderBoard leaderboard;
  private BoardType boardtype;

  /**
   * Constructor for the ParioMartyGameWindow class.
   *
   * @param gameController the {@link GameController} object that controls the game and its players
   *                       through the PlayerController
   * @param useTwoDice     boolean indicating whether to use two dice or not
   */
  public ParioMartyGameWindow(GameController gameController, boolean useTwoDice, BoardType boardtype) {
    super(gameController, useTwoDice, true);
    this.boardtype = boardtype;

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

    dialogBox = new HappeningDialogBox("fwæh", boardtype);
    sidebar.setTop(dialogBox.getComponent());
    sidebar.setCenter(dieBox.getComponent());

    leaderboard = new ParioMartyLeaderBoard(gameController.getPlayersController(), playerPieces);

    sidebar.setBottom(leaderboard.getComponent());

    dieBox.getRollDieButton().setOnAction(e -> {
      dieBox.getRollDieButton().setDisable(true);

      sfxPlayer.openSoundFile(SoundFile.ROLL_DIE);
      sfxPlayer.playSound();

      Timeline dieAnimation = dieBox.dieAnimation();

      dieAnimation.setOnFinished(event -> {
        sfxPlayer.stopSound();

        gameController.rollDice();

        movementAnimation.play();
      });

      dieAnimation.play();
    });

    sidebar.getStyleClass().add("sidebar");
  }

  @Override
  protected Timeline moveCurrentPlayerAnimation(int steps) {
    int currentPlayerPiecePosition = gameController.getPlayersController().getCurrentPlayer()
        .getPosition();

    ImageView currentPlayerPiece = playerPieces.get(
        gameController.getPlayersController().getCurrentPlayer().getName());

    Timeline timeline = new Timeline();

    var nextTileWrapper = new Object() {
      int nextTile = currentPlayerPiecePosition + 1;
    };

    for (int i = 0; i < steps; i++) {
      KeyFrame keyFrame = new KeyFrame(Duration.millis(300 * i), e -> {
        if (nextTileWrapper.nextTile == 36) {
          boardDisplay.getGridTiles().get(nextTileWrapper.nextTile - 1).getChildren()
              .remove(currentPlayerPiece);
          boardDisplay.getGridTiles().get(2).getChildren().add(currentPlayerPiece);

          nextTileWrapper.nextTile = 3;
        } else {
          boardDisplay.getGridTiles().get(nextTileWrapper.nextTile - 1).getChildren()
              .remove(currentPlayerPiece);
          boardDisplay.getGridTiles().get(nextTileWrapper.nextTile).getChildren()
              .add(currentPlayerPiece);

          nextTileWrapper.nextTile++;
        }

        sfxPlayer.openSoundFile(SoundFile.PIECE_MOVED);
        sfxPlayer.playSound();
      });

      timeline.getKeyFrames().add(keyFrame);
    }

    return timeline;
  }

  @Override
  protected void finishTurn() {
  }

}
