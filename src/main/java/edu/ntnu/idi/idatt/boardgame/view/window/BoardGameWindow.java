package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.util.sound.SfxPlayer;
import edu.ntnu.idi.idatt.boardgame.util.sound.SoundFile;
import edu.ntnu.idi.idatt.boardgame.view.window.components.BoardDisplay;
import edu.ntnu.idi.idatt.boardgame.view.window.components.DieComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.Leaderboard;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.DialogBox;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.HappeningDialogBox;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Class to create a window that displays a board game, with a sidebar.
 *
 * @author siguraso & MagnusNaesanGaarder
 * @version 1.0
 * @since 1.0
 */
public class BoardGameWindow implements Window, BoardGameObserver {

  // window stage
  private final Stage window = new Stage();

  // different components of the window
  private final BorderPane sidebar = new BorderPane();
  private Leaderboard leaderboard;
  private final DieComponent dieBox;
  private DialogBox dialogBox;

  // player HashMap containing all the player pieves corresponding to the player profiles given.
  // the key is defined as the player's name.
  private final HashMap<String, ImageView> playerPieces = new HashMap<>();

  // all board elements
  private final BoardDisplay boardDisplay = new BoardDisplay();
  private final StackPane boardGrid = new StackPane();
  private final StackPane allElements = new StackPane();

  // sounds
  private final SfxPlayer sfxPlayer = new SfxPlayer();

  // game logic classes
  private final GameController gameController;

  // movement animation (changes based on the current player and the die throw)
  private Timeline movementAnimation;

  /**
   * Constructor for the BoardGameWindow class.
   *
   * @param board the {@link Board} object that represents the game board and all the logic that
   *              comes with it.
   */
  public BoardGameWindow(Board board, GameController gameController) {
    this.dieBox = new DieComponent(gameController);
    this.gameController = gameController;
    gameController.addObserver(this);

    init();
  }


  // methods from the window interface
  @Override
  public void show() {

    window.show();
  }

  @Override
  public void init() {
    // Initialize the window
    BorderPane root = new BorderPane();
    root.setCenter(getBoardRegion());

    Node sidebar = getSidebar();

    root.setRight(sidebar);

    gameController.getPlayersController().getPlayers().forEach(player -> {

      ImageView playerPiece = new ImageView(
          player.getPlayerPiece().getImagePath());

      playerPiece.setFitHeight(30);
      playerPiece.setFitWidth(30);

      // add the player piece to the player piece hashmap, set the key as the player name
      playerPieces.put(player.getName(), playerPiece);

      boardDisplay.getGridTiles().get(1).getChildren().add(playerPiece);

    });

    allElements.getChildren().add(root);

    Scene scene = new Scene(allElements, 1200, 815);
    scene.getStylesheets().add("file:src/main/resources/Styles/Style.css");

    window.setMinWidth(1200);
    window.setMinHeight(820);
    window.setResizable(true);
    window.setScene(scene);
  }

  @Override
  public void close() {
    window.close();
  }

  @Override
  public void update(int i) {
    this.movementAnimation = moveCurrentPlayerAnimation(i);

    this.movementAnimation.setOnFinished(onMovementFinished -> {
      // when the movement animation is finished, we need to update the current player
      // and set the die button to be enabled again
      finishTurn();
    });
  }

  // individual methods for setting up different parts of the window.

  private StackPane getBoardRegion() {
    StackPane boardDisplay = new StackPane();
    // padding top: 28px, side: 29px
    boardDisplay.setMinWidth(800);
    boardDisplay.setMinHeight(800);
    boardDisplay.getStyleClass().add("root");
    int tileWidth = (800 - (2 * 29)) / 9;
    int tileHeight = (800 - (2 * 28)) / 10;

    this.boardDisplay.init(tileWidth, tileHeight);

    boardGrid.getChildren().add(this.boardDisplay.getComponent());

    ImageView boardImage = new ImageView(
        new Image("file:src/main/resources/Images/LadderBoardGame_default.png"));
    boardImage.setFitHeight(800);
    boardImage.setFitWidth(800);

    boardDisplay.getChildren().addAll(boardImage, boardGrid);
    boardDisplay.setAlignment(javafx.geometry.Pos.CENTER);
    boardDisplay.getStyleClass().add("board-region");

    return boardDisplay;
  }

  private BorderPane getSidebar() {
    sidebar.setMinWidth(400);
    sidebar.setMinHeight(800);
    sidebar.setPadding(new javafx.geometry.Insets(20, 10, 20, 10));

    dialogBox = new HappeningDialogBox(
        gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");

    sidebar.setTop(dialogBox.getComponent());

    sidebar.setCenter(dieBox.getComponent());

    dieBox.getRollDieButton().setDisable(true);

    ((HappeningDialogBox) dialogBox).getConfirmationButton().setOnAction(onPress -> {
      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);
      dieBox.getRollDieButton().setDisable(false);
    });

    // when the button is pressed, it initializes a players turn, meaning that this button is more
    // or less what keeps the game going.
    dieBox.getRollDieButton().setOnAction(onPress -> {

      dieBox.getRollDieButton().setDisable(true);

      Timeline dieAnimation = dieBox.dieAnimation();

      // device what happens when the die animation is fininshed
      dieAnimation.setOnFinished(onDieAnimationFinished -> {
        gameController.rollDice();

        movementAnimation.play();

      });

      // play the animation
      dieAnimation.play();

    });

    leaderboard = new Leaderboard(gameController.getPlayersController().getPlayers());

    sidebar.setBottom(leaderboard.getComponent());

    sidebar.getStyleClass().add("sidebar");
    return sidebar;
  }

  private Timeline moveCurrentPlayerAnimation(int steps) {

    Player currentPlayer = gameController.getPlayersController().getCurrentPlayer();

    int currentPlayerPiecePosition = currentPlayer.getPosition();
    ImageView currentPlayerPiece = playerPieces.get(currentPlayer.getName());

    // create a timeline to animate the player's movement
    Timeline timeline = new Timeline();

    var nextTileWrapper = new Object() {
      int nextTile = currentPlayerPiecePosition + 1;
    };

    var moveBackwardsWrapper = new Object() {
      boolean moveBackwards = false;
    };

    for (int i = 1; i <= steps; i++) {

      KeyFrame keyFrame = new KeyFrame(Duration.millis(400 * i), event -> {

        if (nextTileWrapper.nextTile == boardDisplay.getGridTiles().size() + 1) {
          // if it is about to moveForward one over the last tile, moveForward them backwards,
          // in other words set nextTile to nextTile - 2

          boardDisplay.getGridTiles().get(nextTileWrapper.nextTile - 1).getChildren()
              .remove(currentPlayerPiece);

          nextTileWrapper.nextTile = nextTileWrapper.nextTile - 2;

          boardDisplay.getGridTiles().get(nextTileWrapper.nextTile).getChildren()
              .add(currentPlayerPiece);

          // update the next tile wrapper
          nextTileWrapper.nextTile--;

          moveBackwardsWrapper.moveBackwards = true;
        } else if (moveBackwardsWrapper.moveBackwards) {
          // if it is one over the last tile, moveForward them backwards, in other words,
          // set nexTile to nextTile - 2

          boardDisplay.getGridTiles().get(nextTileWrapper.nextTile + 1).getChildren()
              .remove(currentPlayerPiece);

          boardDisplay.getGridTiles().get(nextTileWrapper.nextTile).getChildren()
              .add(currentPlayerPiece);

          // update the next tile wrapper
          nextTileWrapper.nextTile--;

        } else {

          // if the player is not moving past the last tile, moveForward them normally

          // Remove the player from the current position
          boardDisplay.getGridTiles().get(nextTileWrapper.nextTile - 1).getChildren()
              .remove(currentPlayerPiece);

          // Add the player to the new position
          boardDisplay.getGridTiles().get(nextTileWrapper.nextTile).getChildren()
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

  private void finishTurn() {

    int[] initialPlayerPositions = new int[4];

    gameController.getPlayersController().getPlayers().forEach(player ->
        initialPlayerPositions[gameController.getPlayersController().getPlayers()
            .indexOf(player)] = player.getPosition()
    );

    // get the player object from the players hashmap
    gameController.finishTurn();

    String currentTileType = gameController.getBoard().getTiles()
        .get(initialPlayerPositions[gameController.getPlayersController().getPlayers()
            .indexOf(gameController.getPlayersController().getPreviousPlayer())]).getTileType();

    // if the player is on a special tile, tell the players, and perform on button click
    if (!currentTileType.equals(TileType.NORMAL.getTileType())) {

      System.out.println(currentTileType);

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
          } else {
            dialogBox.refresh(
                gameController.getPlayersController().getPreviousPlayer().getName()
                    + " fell down a ladder! "
                    + "They moved to space " + gameController.getPlayersController()
                    .getPreviousPlayer().getPosition());

            sfxPlayer.openSoundFile(SoundFile.PLAYER_FALL);
          }
        }

        case "WinnerTile" -> showWinnerScreen();

        // TODO: implement the rest of the special tiles
      }

      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

      ((HappeningDialogBox) dialogBox).getConfirmationButton().setOnAction(onPress -> {

        gameController.getPlayersController().getPlayers().forEach(player -> {
          String name = player.getName();
          ImageView playerPiece = playerPieces.get(name);

          // remove the player piece from the current tile
          boardDisplay.getGridTiles()
              .get(initialPlayerPositions[gameController.getPlayersController().getPlayers()
                  .indexOf(player)]).getChildren().remove(playerPiece);

          // add the player piece to the new tile

          boardDisplay.getGridTiles().get(player.getPosition()).getChildren()
              .add(playerPiece);
        });

        // disable the confirmation button
        dialogBox.refresh(
            gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");

        ((HappeningDialogBox) dialogBox).getConfirmationButton().setOnAction(onPress2 -> {
          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);
          dieBox.getRollDieButton().setDisable(false);
        });

        try {
          sfxPlayer.playSound();
        } catch (NullPointerException e) {
          // if the sound file is not found, do nothing
        }


      });

    } else {
      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

      dialogBox.refresh(
          gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");

      ((HappeningDialogBox) dialogBox).getConfirmationButton().setOnAction(onPress -> {
        ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);
        dieBox.getRollDieButton().setDisable(false);
      });

    }

    leaderboard.update();

  }

  private void showWinnerScreen() {
    StackPane winnerScreen = new StackPane();

    winnerScreen.getStyleClass().add("winner-screen");
    winnerScreen.getChildren().add(new Label(
        gameController.getPlayersController().getPreviousPlayer().getName() + " wins the game!"));

    winnerScreen.getChildren().get(0).setStyle("-fx-font-size: 60px; -fx-text-fill: text_wht;");

    allElements.getChildren().add(winnerScreen);

    sfxPlayer.openSoundFile(SoundFile.GAME_WON);
    sfxPlayer.playSound();
  }

}
