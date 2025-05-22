package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.controller.LadderGameController;
import edu.ntnu.idi.idatt.boardgame.model.observerPattern.BoardGameObserver;
import edu.ntnu.idi.idatt.boardgame.util.sound.SfxPlayer;
import edu.ntnu.idi.idatt.boardgame.util.sound.SoundFile;
import edu.ntnu.idi.idatt.boardgame.view.window.components.BoardDisplay;
import edu.ntnu.idi.idatt.boardgame.view.window.components.DieComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.RandomActionComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.DialogBox;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.HappeningDialogBox;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Abstract class to create a window that displays a board game, with a sidebar. This class is
 * extended by specific game windows, and contains the common functionality for all game windows.
 *
 * @author siguraso & MagnusNaesanGaarder
 * @version 1.0
 * @since 1.0
 */
public abstract class BoardGameWindow implements Window, BoardGameObserver {

  // window stage
  protected final Stage window = new Stage();

  // different components of the window
  protected final BorderPane sidebar = new BorderPane();
  protected final DieComponent dieBox;
  protected DialogBox dialogBox;
  protected StackPane randomActionPane;

  // player HashMap containing all the player pieves corresponding to the player profiles given.
  // the key is defined as the player's name.
  protected final HashMap<String, ImageView> playerPieces = new HashMap<>();

  // all board elements
  protected final BoardDisplay boardDisplay;
  protected final StackPane boardGrid = new StackPane();
  protected final StackPane allElements = new StackPane();

  // sounds
  protected final SfxPlayer sfxPlayer = new SfxPlayer();

  // game logic classes
  protected final GameController gameController;

  // movement animation (changes based on the current player and the die throw)
  protected Timeline movementAnimation;


  /**
   * Constructor for the BoardGameWindow class.
   *
   * @param gameController The controller object for the game.
   */
  public BoardGameWindow(GameController gameController, boolean useTwoDice, boolean isParioMarty) {
    this.dieBox = new DieComponent(gameController, useTwoDice);
    this.gameController = gameController;
    gameController.addObserver(this);

    this.boardDisplay = new BoardDisplay(gameController, isParioMarty);
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

    this.gameController.getPlayersController().getPlayers().forEach(player -> {

      ImageView playerPiece = new ImageView(
          player.getPlayerPiece().getImagePath());

      playerPiece.setFitHeight(30);
      playerPiece.setFitWidth(30);

      // add the player piece to the player piece hashmap, set the key as the player name
      playerPieces.put(player.getName(), playerPiece);

      boardDisplay.getPlayerGrid().get(1).getChildren().add(playerPiece);

    });

    initSidebar();

    root.setRight(sidebar);

    allElements.getChildren().add(root);

    Scene scene = new Scene(allElements, 1200, 850);
    scene.getStylesheets().add(
        Objects.requireNonNull(BoardGameWindow.class.getResource("/Styles/Style.css"))
            .toExternalForm());

    window.setMinWidth(1200);
    window.setMinHeight(840);
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

  /**
   * Method to set the contents of the board area of the window.
   *
   * @return a {@link Node} containing the board area.
   */
  protected abstract Node getBoardRegion();

  /**
   * Method to set the contents of the sidebar area of the window.
   */
  protected abstract void initSidebar();

  /**
   * Creates a timeline that moves the current player piece on the board based on the number of
   * steps rolled on the die.
   *
   * @param steps the number of steps to move the player piece.
   * @return a {@link Timeline} that moves the current player piece on the board.
   */
  protected abstract Timeline moveCurrentPlayerAnimation(int steps);

  /**
   * Finishes the current turn by updating the game state based on what tile the player landed on,
   * and updating the player positions on the board.
   */
  protected abstract void finishTurn();

  /**
   * Updates the player positions immediately on the board (without any animation). This is used
   * when the player lands on a tile that has moved the players on the board.
   *
   * @param initialPlayerPositions The initial positions of the players before the action is
   *                               performed.
   */
  protected void updatePlayerPositions(int[] initialPlayerPositions) {
    checkForWinner();

    ((HappeningDialogBox) dialogBox).getConfirmationButton().setOnAction(onPress -> {

      gameController.getPlayersController().getPlayers().forEach(player -> {
        String name = player.getName();
        ImageView playerPiece = playerPieces.get(name);

        // remove the player piece from the current tile
        boardDisplay.getPlayerGrid()
            .get(initialPlayerPositions[gameController.getPlayersController().getPlayers()
                .indexOf(player)]).getChildren().remove(playerPiece);

        // add the player piece to the new tile

        boardDisplay.getPlayerGrid().get(player.getPosition()).getChildren()
            .add(playerPiece);
      });

      // disable the confirmation button
      dialogBox.refresh(
          gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");

      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);

      dieBox.getRollDieButton().setDisable(false);

      // play the sound that was opened earlier
      sfxPlayer.playSound();
    });
  }

  /**
   * Checks if the game is in a state where a player has won. If a player has won, it shows the
   * winner screen.
   */
  protected void checkForWinner() {
    if (gameController.getPlayersController().getPreviousPlayer().isWinner()) {
      sfxPlayer.stopSound();
      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);
      dieBox.getRollDieButton().setDisable(true);
      showWinnerScreen();
    }
  }

  /**
   * Shows the list of random actions that can be performed when a player lands on a random action
   * tile. It then performs an animation showing the player the action that was selected.
   *
   * @param tileAction             The action that was randomly selected.
   * @param initialPlayerPositions The initial positions of the players before the action is
   *                               performed.
   */
  protected void showRandomActionList(String tileAction, int[] initialPlayerPositions) {
    sfxPlayer.stopSound();

    RandomActionComponent randomActionComponent = new RandomActionComponent();

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

    randomActionComponent.randomActionSequence(tileAction, SoundFile.RANDOM_ACTION_MOVE,
        SoundFile.RANDOM_ACTION_SELECT, SoundFile.RANDOM_ACTION_SHOW);

  }

  /**
   * Handles the logic for when a player lands on a random action tile. It shows a dialog box
   * prompting the player to select a random action, and then performs the action that was
   * selected.
   *
   * @param initialPlayerPositions the initial positions of the players before the action is
   *                               performed.
   */
  protected void doRandomActionTileLogic(int[] initialPlayerPositions) {
    dialogBox.refresh(
        gameController.getPlayersController().getPreviousPlayer().getName()
            + " landed on a random action tile! They get to do a random action!");

    String randomAction;

    switch (gameController.getLastRandomAction()) {
      case 0 -> randomAction = "Return to start";
      case 1 -> randomAction = "Roll again";
      case 2 -> randomAction = "Swap spaces with a random player";
      case 3 -> randomAction = "Move to a random tile";
      default -> randomAction = null;
    }

    ((HappeningDialogBox) dialogBox).getConfirmationButton().setOnAction(onPress -> {
      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);
      showRandomActionList(randomAction, initialPlayerPositions);
    });
  }

  /**
   * Gets the player positions on the board. This is used to update the player positions when a
   * player lands on a special tile.
   *
   * @return an array of integers containing the player positions on the board.
   */
  protected int[] getPlayerPositions() {
    int[] playerPositions = new int[gameController.getPlayersController().getPlayers().size()];

    gameController.getPlayersController().getPlayers().forEach((player) -> {
      playerPositions[gameController.getPlayersController().getPlayers()
          .indexOf(player)] = player.getPosition();
    });

    return playerPositions;
  }

  /**
   * Shows the winner screen when a player wins the game. It displays the winner's name and gives
   * the option to keep playing (if there are more than 2 players left) or return to the main menu.
   */
  protected void showWinnerScreen() {
    sfxPlayer.stopSound();
    StackPane winnerScreen = new StackPane();

    winnerScreen.getStyleClass().add("winner-screen");
    winnerScreen.setAlignment(Pos.CENTER);

    Button keepPlayingButton = new Button("Keep playing");

    keepPlayingButton.setOnAction(e -> {
      // remove the winning player pieces from the board
      gameController.getPlayersController().getPlayers().forEach(player -> {
        if (player.isWinner()) {
          boardDisplay.getPlayerGrid().get(player.getPosition()).getChildren()
              .remove(playerPieces.get(player.getName()));
          playerPieces.remove(player.getName());
        }
      });

      // remove the winners from the game
      ((LadderGameController) gameController).removeWinners();

      dialogBox.refresh(
          gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");

      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);
      dieBox.getRollDieButton().setDisable(false);

      allElements.getChildren().remove(winnerScreen);
      sfxPlayer.stopSound();
    });

    Button exitButton = new Button("Return to main menu");
    exitButton.setOnAction(e -> {
      Stage MainWindowStage = new Stage();

      MainWindow mainWindow = new MainWindow(MainWindowStage);
      mainWindow.init();

      sfxPlayer.stopSound();
      close();
      mainWindow.show();
    });

    HBox buttonsBox = new HBox();

    if (gameController.getPlayersController().getPlayers().size() > 2) {
      buttonsBox.getChildren().addAll(keepPlayingButton, exitButton);

      keepPlayingButton.setAlignment(Pos.CENTER);
    } else {
      buttonsBox.getChildren().add(exitButton);
    }

    exitButton.setAlignment(Pos.CENTER);

    buttonsBox.getStyleClass().add("winner-buttons");
    buttonsBox.setAlignment(Pos.CENTER);
    buttonsBox.setSpacing(20);

    Label winnerText = new Label("Congratulations, "
        + gameController.getPlayersController().getPreviousPlayer().getName() + "!\n"
        + "You have won the game!");
    winnerText.setAlignment(Pos.CENTER);
    winnerText.getStyleClass().add("winner-label");

    VBox winnerScreenBox = new VBox(winnerText, buttonsBox);
    winnerScreenBox.setAlignment(Pos.CENTER);
    winnerScreenBox.setSpacing(50);

    createConfettiAnimation(winnerScreen);

    winnerScreen.getChildren().add(winnerScreenBox);

    allElements.getChildren().add(winnerScreen);

    sfxPlayer.openSoundFile(SoundFile.GAME_WON);
    sfxPlayer.playSound();
  }

  protected void createConfettiAnimation(StackPane winnerScreen) {
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

          confettiGroup.setOnFinished(event -> allElements.getChildren().remove(confetti));

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
