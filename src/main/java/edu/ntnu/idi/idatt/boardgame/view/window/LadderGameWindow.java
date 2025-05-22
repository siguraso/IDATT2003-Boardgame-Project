package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.util.sound.SoundFile;
import edu.ntnu.idi.idatt.boardgame.view.window.components.LadderGameLeaderboard;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.HappeningDialogBox;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * A class that constructs the game window for the Ladder Game. This class extends the
 * {@link BoardGameWindow} class and implements the game-specific UserInterface logic for the Ladder
 * Game.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public class LadderGameWindow extends BoardGameWindow {

  // different components of the window
  private LadderGameLeaderboard leaderboard;
  private final BoardType boardtype;

  /**
   * Constructor for the BoardGameWindow class.
   *
   * @param gameController The controller object for the game.
   */
  public LadderGameWindow(GameController gameController, boolean useTwoDice, BoardType boardtype) {
    super(gameController, useTwoDice, false);
    if (boardtype == null) {
      throw new IllegalArgumentException("Board type cannot be null");
    }
    this.boardtype = boardtype;
    super.init();
    window.setTitle("Ladder Game");
  }

  // individual methods for setting up different parts of the window.
  @Override
  protected Node getBoardRegion() {
    StackPane boardDisplay = new StackPane();
    // padding top: 28px, side: 29px
    boardDisplay.setMinWidth(800);
    boardDisplay.setMinHeight(800);

    int tileWidth = (800 - (2 * 29)) / 9;
    int tileHeight = (800 - (2 * 28)) / 10;

    this.boardDisplay.init(tileWidth, tileHeight, gameController.getBoard().getTileTypes());
    this.boardDisplay.drawLadders(gameController.getBoard().getTileTypes(),
        new int[]{tileWidth, tileHeight});

    boardGrid.getChildren().add(this.boardDisplay.getComponent());

    ImageView boardArrows = new ImageView(
        new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/Images/boards/ladder_game_arrows.png"))));
    boardArrows.setFitHeight(800);
    boardArrows.setFitWidth(800);

    StackPane boardImageStack = new StackPane();
    boardImageStack.getChildren().addAll(boardArrows, boardGrid);

    boardDisplay.getChildren().add(boardImageStack);
    boardDisplay.setAlignment(javafx.geometry.Pos.CENTER);
    boardDisplay.getStyleClass().add("board-region");

    return boardDisplay;
  }

  @Override
  protected void initSidebar() {
    sidebar.setMinWidth(400);
    sidebar.setMinHeight(800);
    sidebar.setPadding(new javafx.geometry.Insets(20, 10, 20, 10));

    dialogBox = new HappeningDialogBox(
        gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!",
        boardtype);

    sidebar.setTop(dialogBox.getComponent());

    sidebar.setCenter(dieBox.getComponent());

    ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);

    dieBox.getRollDieButton().setDisable(false);

    // when the button is pressed, it initializes a players turn, meaning that this button is more
    // or less what keeps the game going.
    dieBox.getRollDieButton().setOnAction(onPress -> {
      sfxPlayer.openSoundFile(SoundFile.ROLL_DIE);
      sfxPlayer.playSound();

      dieBox.getRollDieButton().setDisable(true);

      Timeline dieAnimation = dieBox.dieAnimation();

      // device what happens when the die animation is fininshed
      dieAnimation.setOnFinished(onDieAnimationFinished -> {
        sfxPlayer.stopSound();
        gameController.rollDice();

        movementAnimation.play();

      });

      // play the animation
      dieAnimation.play();

    });

    leaderboard = new LadderGameLeaderboard(gameController.getPlayersController());

    sidebar.setBottom(leaderboard.getComponent());

    sidebar.getStyleClass().add("sidebar");
  }

  @Override
  protected Timeline moveCurrentPlayerAnimation(int steps) {

    int currentPlayerPiecePosition = gameController.getPlayersController().getCurrentPlayer()
        .getPosition();

    ImageView currentPlayerPiece = playerPieces.get(
        gameController.getPlayersController().getCurrentPlayer().getName());

    // create a timeline to animate the player's movement
    Timeline timeline = new Timeline();

    var nextTileWrapper = new Object() {
      int nextTile = currentPlayerPiecePosition + 1;
    };

    var moveBackwardsWrapper = new Object() {
      boolean moveBackwards = false;
    };

    for (int i = 1; i <= steps; i++) {

      KeyFrame keyFrame = new KeyFrame(Duration.millis((double) 300 * i), event -> {

        if (nextTileWrapper.nextTile == boardDisplay.getPlayerGrid().size() + 1) {
          // if it is about to moveForward one over the last tile, moveForward them backwards,
          // in other words set nextTile to nextTile - 2

          boardDisplay.getPlayerGrid().get(nextTileWrapper.nextTile - 1).getChildren()
              .remove(currentPlayerPiece);

          nextTileWrapper.nextTile = nextTileWrapper.nextTile - 2;

          boardDisplay.getPlayerGrid().get(nextTileWrapper.nextTile).getChildren()
              .add(currentPlayerPiece);

          // update the next tile wrapper
          nextTileWrapper.nextTile--;

          moveBackwardsWrapper.moveBackwards = true;
        } else if (moveBackwardsWrapper.moveBackwards) {
          // if it is one over the last tile, moveForward them backwards, in other words,
          // set nexTile to nextTile - 2

          boardDisplay.getPlayerGrid().get(nextTileWrapper.nextTile + 1).getChildren()
              .remove(currentPlayerPiece);

          boardDisplay.getPlayerGrid().get(nextTileWrapper.nextTile).getChildren()
              .add(currentPlayerPiece);

          // update the next tile wrapper
          nextTileWrapper.nextTile--;

        } else {

          // if the player is not moving past the last tile, moveForward them normally

          // Remove the player from the current position
          boardDisplay.getPlayerGrid().get(nextTileWrapper.nextTile - 1).getChildren()
              .remove(currentPlayerPiece);

          // Add the player to the new position
          boardDisplay.getPlayerGrid().get(nextTileWrapper.nextTile).getChildren()
              .add(currentPlayerPiece);

          // update the next tile wrapper
          nextTileWrapper.nextTile++;

        }

        // open and play the moveForward player sound
        sfxPlayer.openSoundFile(SoundFile.PIECE_MOVED);
        sfxPlayer.playSound();

      });

      timeline.getKeyFrames().add(keyFrame);
    }

    return timeline;
  }

  @Override
  protected void finishTurn() {

    int[] initialPlayerPositions = getPlayerPositions();

    // get the player object from the players hashmap
    gameController.finishTurn();

    String currentTileType = gameController.getBoard().tiles()
        .get(initialPlayerPositions[gameController.getPlayersController().getPlayers()
            .indexOf(gameController.getPlayersController().getPreviousPlayer())]).getTileType();

    // if the player is on a special tile, tell the players, and perform on button click
    if (!currentTileType.equals(TileType.NORMAL.getTileType())) {
      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);
      switch (currentTileType) {
        case "LadderTile" -> {
          int ladderDelta = gameController.getPlayersController().getPreviousPlayer().getPosition()
              - initialPlayerPositions[gameController.getPlayersController().getPlayers()
              .indexOf(gameController.getPlayersController().getPreviousPlayer())];

          if (ladderDelta > 0) {
            dialogBox.refresh(
                gameController.getPlayersController().getPreviousPlayer().getName()
                    + " climbed a ladder! "
                    + "They moved to space " + gameController.getPlayersController()
                    .getPreviousPlayer().getPosition());

            sfxPlayer.openSoundFile(SoundFile.PLAYER_CLIMB);

            updatePlayerPositions(initialPlayerPositions);

          } else {
            dialogBox.refresh(
                gameController.getPlayersController().getPreviousPlayer().getName()
                    + " fell down a ladder! "
                    + "They moved to space " + gameController.getPlayersController()
                    .getPreviousPlayer().getPosition());

            sfxPlayer.openSoundFile(SoundFile.PLAYER_FALL);

            updatePlayerPositions(initialPlayerPositions);
          }
        }

        case "ReturnToStartTile" -> {
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " fell down a hole and returned to start!");

          sfxPlayer.openSoundFile(SoundFile.RETURN_TO_START);

          updatePlayerPositions(initialPlayerPositions);
        }

        case "RollAgainTile" -> {
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " landed on a roll again tile! They get to roll again!");

          sfxPlayer.openSoundFile(SoundFile.ROLL_AGAIN);

          updatePlayerPositions(initialPlayerPositions);
        }

        case "RandomActionTile" -> doRandomActionTileLogic(initialPlayerPositions);

        // default is the winner tile
        default -> checkForWinner();

      }

    } else {

      dialogBox.refresh(
          gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");

      dieBox.getRollDieButton().setDisable(false);

    }

    leaderboard.update();

  }

}