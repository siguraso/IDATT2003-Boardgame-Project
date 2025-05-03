package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.util.sound.SfxPlayer;
import edu.ntnu.idi.idatt.boardgame.util.sound.SoundFile;
import edu.ntnu.idi.idatt.boardgame.view.window.components.BoardDisplay;
import edu.ntnu.idi.idatt.boardgame.view.window.components.DieComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.Leaderboard;
import edu.ntnu.idi.idatt.boardgame.view.window.components.RandomActionComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.DialogBox;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.HappeningDialogBox;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
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
  private RandomActionComponent randomActionComponent;
  private StackPane randomActionPane;

  // player HashMap containing all the player pieves corresponding to the player profiles given.
  // the key is defined as the player's name.
  private final HashMap<String, ImageView> playerPieces = new HashMap<>();

  // all board elements
  private final BoardDisplay boardDisplay;
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
   * @param gameController The controller object for the game.
   */
  public BoardGameWindow(GameController gameController, boolean useTwoDice) {
    this.dieBox = new DieComponent(gameController, useTwoDice);
    this.gameController = gameController;
    gameController.addObserver(this);

    this.boardDisplay = new BoardDisplay(gameController);

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
    scene.getStylesheets().add(
        Objects.requireNonNull(BoardGameWindow.class.getResource("/Styles/Style.css"))
            .toExternalForm());

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
  public void update(int[] i) {
    int steps = Arrays.stream(i).sum();

    this.movementAnimation = moveCurrentPlayerAnimation(steps);

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

      KeyFrame keyFrame = new KeyFrame(Duration.millis(300 * i), event -> {

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

    String currentTileType = gameController.getBoard().tiles()
        .get(initialPlayerPositions[gameController.getPlayersController().getPlayers()
            .indexOf(gameController.getPlayersController().getPreviousPlayer())]).getTileType();

    // if the player is on a special tile, tell the players, and perform on button click
    if (!currentTileType.equals(TileType.NORMAL.getTileType())) {
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
                  + " fell down a hole and returned to start! 🥲");

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

        case "RandomActionTile" -> {
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " landed on a random action tile! They get to do a random action!");

          String randomAction;

          switch (gameController.getLastRandomAction()) {
            case "ReturnToStartAction" -> randomAction = "Return to start";
            case "RollAgainAction" -> randomAction = "Roll again";
            case "SwapPlayersAction" -> randomAction = "Swap spaces with a random player";
            case "MoveToRandomTileAction" -> randomAction = "Move to a random tile";
            default -> randomAction = null;
          }

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setOnAction(onPress -> {
            ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);
            showRandomActionList(randomAction, initialPlayerPositions);
          });
        }

        case "WinnerTile" -> showWinnerScreen();

      }

      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

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

  private void updatePlayerPositions(int[] initialPlayerPositions) {

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

      // play the sound that was opened earlier
      sfxPlayer.playSound();


    });
  }

  private void showRandomActionList(String tileAction, int[] initialPlayerPositions) {
    sfxPlayer.stopSound();

    randomActionComponent = new RandomActionComponent();

    randomActionPane = (StackPane) randomActionComponent.getComponent();

    allElements.getChildren().add(randomActionPane);

    randomActionComponent.getRandomActionTimeline().setOnFinished(onFinished -> {
      allElements.getChildren().remove(randomActionPane);

      // perform the action that was selected
      switch (tileAction) {
        case "Return to start" -> {
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " returned to start!");

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          sfxPlayer.openSoundFile(SoundFile.RETURN_TO_START);

          updatePlayerPositions(initialPlayerPositions);

        }
        case "Roll again" -> {
          // logic gets handled in the game controller, all we have to do is prompt the player
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " gets to roll again!");

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          sfxPlayer.openSoundFile(SoundFile.ROLL_AGAIN);

          updatePlayerPositions(initialPlayerPositions);

        }
        case "Move to a random tile" -> {
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " moved to tile "
                  + gameController.getPlayersController().getPreviousPlayer().getPosition());

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          sfxPlayer.openSoundFile(SoundFile.RANDOM_TILE);

          updatePlayerPositions(initialPlayerPositions);
        }
        default -> {
          // default is swap players.

          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " swapped spaces with " + gameController.getLastSwappedPlayer() + "!");

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          sfxPlayer.openSoundFile(SoundFile.SWAP_PLAYERS);

          updatePlayerPositions(initialPlayerPositions);
        }
      }
    });

    randomActionComponent.randomActionSequence(tileAction);

  }

  private void showWinnerScreen() {
    StackPane winnerScreen = new StackPane();

    winnerScreen.getStyleClass().add("winner-screen");
    winnerScreen.setAlignment(Pos.CENTER);

    createConfettiAnimation(winnerScreen);
    winnerScreen.getChildren().add(new Label("Congratulations, "
        + gameController.getPlayersController().getPreviousPlayer().getName() + "!\n"
        + "You have won the game!"));
    winnerScreen.getChildren().getLast().getStyleClass().add("winner-label");

    allElements.getChildren().add(winnerScreen);

    sfxPlayer.openSoundFile(SoundFile.GAME_WON);
    sfxPlayer.playSound();
  }

  private void createConfettiAnimation(StackPane winnerScreen) {
    winnerScreen.getChildren().clear();
    winnerScreen.setOpacity(1.0);
    winnerScreen.setVisible(true);
    winnerScreen.setMouseTransparent(false);

    HBox confettiContainer = new HBox();
    confettiContainer.setPrefWidth(winnerScreen.getWidth());
    winnerScreen.getChildren().add(confettiContainer);

    Random random = new Random();
    Platform.runLater(() -> {
      double stageWidth = winnerScreen.getWidth();
      double stageHeight = winnerScreen.getHeight();

      if (stageWidth <= 0 || stageHeight <= 0) {
        return;
      }

      for (int i = 0; i < 1000; i++) {
        int delay = i * 5; // 5 millisekunder mellom hver konfetti

        PauseTransition initialDelay = new PauseTransition(Duration.millis(delay));
        initialDelay.setOnFinished(e -> {
          Rectangle confetti =
              new Rectangle(10 + random.nextDouble() * 50, 10 + random.nextDouble() * 50);
          confetti.setFill(getRandomColor());
          confetti.setTranslateX(random.nextDouble() * (stageWidth * 0.8) + (stageWidth * 0.1));
          confetti.setTranslateY(stageHeight);
          confetti.setOpacity(1.0);
          confettiContainer.getChildren().add(confetti);

          double duration = 400 + random.nextDouble() * 400;

          TranslateTransition translate =
              new TranslateTransition(Duration.millis(duration), confetti);
          translate.setByX((random.nextDouble() - 0.5) * stageWidth * 0.2);
          translate.setByY(-stageHeight * (0.6 + random.nextDouble() * 0.4));
          translate.setInterpolator(Interpolator.EASE_OUT);

          RotateTransition rotate = new RotateTransition(Duration.millis(duration), confetti);
          rotate.setByAngle(180 + random.nextDouble() * 360);
          rotate.setInterpolator(Interpolator.LINEAR);

          FadeTransition fade = new FadeTransition(Duration.millis(duration), confetti);
          fade.setFromValue(1.0);
          fade.setToValue(0.0);

          ParallelTransition confettiGroup = new ParallelTransition(translate, rotate, fade);
          confettiGroup.setOnFinished(event -> {
            confettiContainer.getChildren().remove(confetti);
            if (confettiContainer.getChildren().isEmpty()) {
              winnerScreen.setMouseTransparent(true);
            }
          });

          confettiGroup.play();
        });

        initialDelay.play();
      }
    });
  }

  private Color getRandomColor() {
    Random random = new Random();
    return Color.rgb(
        random.nextInt(256),
        random.nextInt(256),
        random.nextInt(256),
        1
    );
  }
}
