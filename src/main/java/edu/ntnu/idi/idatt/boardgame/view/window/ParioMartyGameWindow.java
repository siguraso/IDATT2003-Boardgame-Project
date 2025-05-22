package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.controller.ParioMartyGameController;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.util.sound.SoundFile;
import edu.ntnu.idi.idatt.boardgame.view.window.components.MowserActionComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.ParioMartyLeaderBoard;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.HappeningDialogBox;
import java.util.Arrays;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * A class that constructs the game window for the Pario Marty game. This class extends the
 * {@link BoardGameWindow} class and implements the game-specific UserInterface logic for the Pario
 * Marty game.
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class ParioMartyGameWindow extends BoardGameWindow {

  private ParioMartyLeaderBoard leaderboard;

  private final BoardType boardType;

  private StackPane mowserActionPane;
  private Label turns;


  /**
   * Constructor for the ParioMartyGameWindow class.
   *
   * @param gameController the {@link GameController} object that controls the game and its players
   *                       through the PlayerController
   * @param useTwoDice     boolean indicating whether to use two dice or not
   */
  public ParioMartyGameWindow(GameController gameController, boolean useTwoDice,
      BoardType boardType) {
    super(gameController, useTwoDice, true);
    this.boardType = boardType;
    init();

    window.setTitle("Pario Marty");
  }

  @Override
  protected Node getBoardRegion() {

    ImageView arrows = new ImageView(new Image(
        Objects.requireNonNull(
            this.getClass().getResourceAsStream("/Images/boards/pario_marty_arrows.png"))));

    arrows.setFitWidth(800);
    arrows.setFitHeight(800);

    Label header = new Label("Pario Marty");
    header.getStyleClass().add("pario-marty-header");

    int tileWidth = (800 - (2 * 29)) / 9;
    int tileHeight = (800 - (2 * 28)) / 10;

    this.boardDisplay.init(tileWidth, tileHeight, gameController.getBoard().getTileTypes());
    boardGrid.getChildren().addAll(arrows, header, this.boardDisplay.getComponent());

    turns = new Label("Turn 1 of 20");

    VBox boardVbox = new VBox();
    turns.getStyleClass().add("turns-label");

    boardVbox.getStyleClass().add("board-region");

    boardVbox.getChildren().addAll(turns, boardGrid);
    boardVbox.setPadding(new Insets(20, 0, 0, 0));

    placeCrownTile(null);
    placeCrownTile(null);

    return boardVbox;
  }

  @Override
  protected void initSidebar() {
    sidebar.setMinWidth(400);
    sidebar.setMinHeight(800);
    sidebar.setPadding(new javafx.geometry.Insets(20, 10, 20, 10));

    dialogBox = new HappeningDialogBox(
        gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!", boardType);
    ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);

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

    for (int i = 1; i <= steps; i++) {
      KeyFrame keyFrame = new KeyFrame(Duration.millis(300 * i), e -> {
        if (nextTileWrapper.nextTile == 36) {
          boardDisplay.getPlayerGrid().get(nextTileWrapper.nextTile - 1).getChildren()
              .remove(currentPlayerPiece);
          boardDisplay.getPlayerGrid().get(2).getChildren().add(currentPlayerPiece);

          nextTileWrapper.nextTile = 3;
        } else {
          boardDisplay.getPlayerGrid().get(nextTileWrapper.nextTile - 1).getChildren()
              .remove(currentPlayerPiece);
          boardDisplay.getPlayerGrid().get(nextTileWrapper.nextTile).getChildren()
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
    dieBox.getRollDieButton().setDisable(true);

    int[] initialPlayerPositions = getPlayerPositions();

    gameController.finishTurn();

    String currentTileType = gameController.getBoard().tiles()
        .get(initialPlayerPositions[gameController.getPlayersController().getPlayers()
            .indexOf(gameController.getPlayersController().getPreviousPlayer())]).getTileType();

    // if the player is on a special tile, tell the players, and perform on button click
    if (!currentTileType.equals(TileType.NORMAL.getTileType())) {

      switch (currentTileType) {
        case "AddCoinsTile" -> {
          leaderboard.update();
          sfxPlayer.stopSound();
          sfxPlayer.openSoundFile(SoundFile.ADD_COINS);
          sfxPlayer.playSound();

          dieBox.getRollDieButton().setDisable(false);

          dialogBox.refresh(
              gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");

          checkForWinner();
        }
        case "RemoveCoinsTile" -> {
          leaderboard.update();
          sfxPlayer.stopSound();
          sfxPlayer.openSoundFile(SoundFile.REMOVE_COINS);
          sfxPlayer.playSound();

          dieBox.getRollDieButton().setDisable(false);

          dialogBox.refresh(
              gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");

          checkForWinner();
        }
        case "RollAgainTile" -> {
          leaderboard.update();
          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " landed on a roll again tile! They get to roll again!");

          sfxPlayer.openSoundFile(SoundFile.ROLL_AGAIN);

          updatePlayerPositions(initialPlayerPositions);
        }
        case "AddCrownTile" -> {
          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);
          ((HappeningDialogBox) dialogBox).showYesNoDialogBox();

          dialogBox.refresh(
              "Congratulations " + gameController.getPlayersController().getPreviousPlayer()
                  .getName() + "! You have landed on a crown tile! "
                  + "Would you like to buy a crown for 10 coins?");

          ((HappeningDialogBox) dialogBox).getYesButton().setOnAction(buyCrown -> {
            try {
              leaderboard.update();
              ((ParioMartyGameController) gameController).checkCrownPurchase(true);

              ((HappeningDialogBox) dialogBox).showOkDialogBox();

              dialogBox.refresh(gameController.getPlayersController().getPreviousPlayer().getName()
                  + " got a crown!");

              placeCrownTile(
                  gameController.getPlayersController().getPreviousPlayer().getPosition());

              leaderboard.update();

              sfxPlayer.openSoundFile(SoundFile.GET_CROWN);

              updatePlayerPositions(initialPlayerPositions);

            } catch (IllegalArgumentException e) {
              leaderboard.update();
              ((HappeningDialogBox) dialogBox).showOkDialogBox();
              dialogBox.refresh(gameController.getPlayersController().getPreviousPlayer().getName()
                  + " does not have enough coins to buy a crown!");

              sfxPlayer.openSoundFile(SoundFile.MOWSER_SHOW);

              updatePlayerPositions(initialPlayerPositions);

            }
          });

          ((HappeningDialogBox) dialogBox).getNoButton().setOnAction(dontBuyCrown -> {
            leaderboard.update();
            ((ParioMartyGameController) gameController).checkCrownPurchase(false);

            ((HappeningDialogBox) dialogBox).showOkDialogBox();

            dialogBox.refresh(gameController.getPlayersController().getPreviousPlayer().getName()
                + " did not buy a crown.");

            dieBox.getRollDieButton().setDisable(false);

            updatePlayerPositions(initialPlayerPositions);
          });
        }
        case "RandomActionTile" -> {
          leaderboard.update();

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          doRandomActionTileLogic(initialPlayerPositions);

        }
        case "ReturnToStartTile" -> {
          leaderboard.update();
          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " fell down a hole and returned to start!");

          sfxPlayer.openSoundFile(SoundFile.RETURN_TO_START);

          updatePlayerPositions(initialPlayerPositions);
        }
        // default case is mowser tile
        default -> {
          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          sfxPlayer.stopSound();
          sfxPlayer.openSoundFile(SoundFile.MOWSER_LAND);
          sfxPlayer.playSound();

          dialogBox.refresh(
              "Oh No! " + gameController.getPlayersController().getPreviousPlayer().getName()
                  + " landed on a Mowser tile! What is going to happen?!");

          String mowserAction;

          switch (gameController.getLastRandomAction()) {
            case 0 -> mowserAction = "Lose 20 coins!";
            case 1 -> mowserAction = "Lose all of your coins!";
            case 2 -> mowserAction = "Lose a crown!";
            case 3 -> mowserAction = "Return back to start!";
            default -> mowserAction = null;
          }

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setOnAction(onPress -> {
            ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);
            sfxPlayer.stopSound();
            showMowserActionList(mowserAction, initialPlayerPositions);
          });
        }
      }
    } else {

      dialogBox.refresh(
          gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");

    }

    if (((ParioMartyGameController) gameController).getCurrentTurn() <= 20) {
      turns.setText(
          "Turn " + ((ParioMartyGameController) gameController).getCurrentTurn() + " of 20");
    }

  }

  private void placeCrownTile(Integer lastCrownTile) {
    ((ParioMartyGameController) gameController).setCrownTile(lastCrownTile);

    // if the current player is on the crown tile, the crown tile is removed
    if (lastCrownTile != null) {
      boardDisplay.getGridTileStack().get(lastCrownTile).getChildren().removeFirst();

      boardDisplay.getGridTileStack().get(lastCrownTile).getStyleClass().clear();

      boardDisplay.getGridTileStack().get(lastCrownTile).getStyleClass().add("add-coins-tile");

      ImageView icon = new ImageView(new Image(Objects.requireNonNull(this.getClass()
          .getResourceAsStream("/Images/boards/tile-icons/add_coin.png"))));
      icon.setFitWidth(60);
      icon.setFitHeight(54);

      boardDisplay.getGridTileStack().get(lastCrownTile).getChildren().add(icon);
      icon.toBack();
    }

    int currentCrownTile = ((ParioMartyGameController) gameController).getCurrentCrownTile();

    boardDisplay.getGridTileStack().get(currentCrownTile).getChildren().removeFirst();

    boardDisplay.getGridTileStack().get(currentCrownTile).getStyleClass().clear();

    boardDisplay.getGridTileStack().get(currentCrownTile).getStyleClass().add("add-crown-tile");

    ImageView icon = new ImageView(
        new Image(Objects.requireNonNull(
            this.getClass()
                .getResourceAsStream("/Images/boards/tile-icons/crown_gold.png"))));
    icon.setFitWidth(60);
    icon.setFitHeight(54);

    boardDisplay.getGridTileStack().get(currentCrownTile).getChildren().add(icon);
    icon.toBack();


  }

  private void showMowserActionList(String mowserAction, int[] initialPlayerPositions) {
    sfxPlayer.stopSound();

    MowserActionComponent mowserActionComponent = new MowserActionComponent();

    mowserActionPane = (StackPane) mowserActionComponent.getComponent();

    allElements.getChildren().add(mowserActionPane);

    mowserActionComponent.getRandomActionTimeline().setOnFinished(onFinished -> {
      allElements.getChildren().remove(mowserActionPane);

      // perform the action that was selected
      switch (mowserAction) {
        case "Lose 20 coins!" -> {
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " lost 20 coins!");

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          sfxPlayer.openSoundFile(SoundFile.REMOVE_COINS);

          updatePlayerPositions(initialPlayerPositions);
        }
        case "Lose a crown!" -> {
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " lost a crown!");

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          sfxPlayer.openSoundFile(SoundFile.LOSE_CROWN);

          updatePlayerPositions(initialPlayerPositions);
        }
        case "Lose all of your coins!" -> {
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " lost all of their coins!");

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          sfxPlayer.openSoundFile(SoundFile.REMOVE_COINS);

          updatePlayerPositions(initialPlayerPositions);
        }

        default -> {
          // default case is return to start.

          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " returned to start!");

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);

          sfxPlayer.openSoundFile(SoundFile.RETURN_TO_START);

          updatePlayerPositions(initialPlayerPositions);
        }
      }

      leaderboard.update();
    });

    mowserActionComponent.randomActionSequence(mowserAction, SoundFile.RANDOM_ACTION_MOVE,
        SoundFile.MOWSER_SELECT, SoundFile.MOWSER_SHOW);

  }

  @Override
  protected void showWinnerScreen() {
    sfxPlayer.stopSound();
    StackPane winnerScreen = new StackPane();

    winnerScreen.getStyleClass().add("winner-screen");
    winnerScreen.setAlignment(Pos.CENTER);

    Button exitButton = new Button("Return to main menu");
    exitButton.setOnAction(e -> {
      sfxPlayer.stopSound();
      Stage mainWindowStage = new Stage();

      MainWindow mainWindow = new MainWindow(mainWindowStage);
      mainWindow.init();

      close();
      mainWindow.show();
    });

    String[] winnerNames = ((ParioMartyGameController) gameController).getWinnerNames();

    Label winnerText;

    if (winnerNames.length > 1) {
      StringBuilder winnerTextBuilder = new StringBuilder("Congratulations, \n");
      Arrays.stream(winnerNames).toList().forEach(winner -> {
        winnerTextBuilder.append(winner);
        if (Arrays.stream(winnerNames).toList().indexOf(winner) < winnerNames.length - 1) {
          winnerTextBuilder.append(",\n");
        } else {
          winnerTextBuilder.append("\nHave won the game!");
        }
      });

      winnerText = new Label(winnerTextBuilder.toString());
      winnerText.setAlignment(Pos.CENTER);
      winnerText.getStyleClass().add("winner-label");
    } else {
      winnerText = new Label("Congratulations, "
          + winnerNames[0] + "!\n"
          + "You have won the game!");
      winnerText.setAlignment(Pos.CENTER);
    }

    winnerText.getStyleClass().add("winner-label");

    exitButton.setAlignment(Pos.CENTER);

    VBox winnerScreenBox = new VBox(winnerText, exitButton);
    winnerScreenBox.setAlignment(Pos.CENTER);
    winnerScreenBox.setSpacing(50);

    exitButton.getStyleClass().add("winner-buttons");

    createConfettiAnimation(winnerScreen);

    winnerScreen.getChildren().add(winnerScreenBox);

    allElements.getChildren().add(winnerScreen);

    sfxPlayer.openSoundFile(SoundFile.GAME_WON);
    sfxPlayer.playSound();
  }

  @Override
  protected void checkForWinner() {
    if (gameController.isGameOver()) {
      sfxPlayer.stopSound();
      ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(true);
      dieBox.getRollDieButton().setDisable(true);
      showWinnerScreen();
    }
  }

}
