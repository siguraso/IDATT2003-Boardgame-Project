package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.controller.ParioMartyGameController;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.TileType;
import edu.ntnu.idi.idatt.boardgame.util.sound.SoundFile;
import edu.ntnu.idi.idatt.boardgame.view.window.components.MowserActionComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.ParioMartyLeaderBoard;
import edu.ntnu.idi.idatt.boardgame.view.window.components.RandomActionComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.dialogBox.HappeningDialogBox;
import java.util.Objects;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
  private Label turns;
  private StackPane mowserActionPane;

  /**
   * Constructor for the ParioMartyGameWindow class.
   *
   * @param gameController the {@link GameController} object that controls the game and its players
   *                       through the PlayerController
   * @param useTwoDice     boolean indicating whether to use two dice or not
   */
  public ParioMartyGameWindow(GameController gameController, boolean useTwoDice) {
    super(gameController, useTwoDice, true);

    window.setTitle("Pario Marty");
  }

  @Override
  protected Node getBoardRegion() {

    int tileWidth = (800 - (2 * 29)) / 9;
    int tileHeight = (800 - (2 * 28)) / 10;

    ImageView arrows = new ImageView(new Image(
        Objects.requireNonNull(
            this.getClass().getResourceAsStream("/Images/boards/pario_marty_arrows.png"))));

    arrows.setFitWidth(800);
    arrows.setFitHeight(800);

    Label header = new Label("Pario Marty");
    header.getStyleClass().add("pario-marty-header");

    this.boardDisplay.init(tileWidth, tileHeight, gameController.getBoard().getTileTypes());
    boardGrid.getChildren().addAll(arrows, header, this.boardDisplay.getComponent());

    turns = new Label("Turn 1 of 15");

    VBox boardVBox = new VBox();
    turns.getStyleClass().add("turns-label");

    boardVBox.getStyleClass().add("board-region");

    boardVBox.getChildren().addAll(turns, boardGrid);
    boardVBox.setPadding(new Insets(20, 0, 0, 0));

    placeCrownTile(null);
    placeCrownTile(null);

    return boardVBox;
  }

  @Override
  protected void initSidebar() {
    sidebar.setMinWidth(400);
    sidebar.setMinHeight(800);
    sidebar.setPadding(new javafx.geometry.Insets(20, 10, 20, 10));

    dialogBox = new HappeningDialogBox(
        gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");
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

    int[] initialPlayerPositions = new int[4];

    gameController.getPlayersController().getPlayers().forEach(player ->
        initialPlayerPositions[gameController.getPlayersController().getPlayers()
            .indexOf(player)] = player.getPosition()
    );

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
          dieBox.getRollDieButton().setDisable(false);
          sfxPlayer.playSound();
          dialogBox.refresh(
              gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");
        }
        case "RemoveCoinsTile" -> {
          leaderboard.update();
          sfxPlayer.stopSound();
          sfxPlayer.openSoundFile(SoundFile.REMOVE_COINS);
          dieBox.getRollDieButton().setDisable(false);
          sfxPlayer.playSound();
          dialogBox.refresh(
              gameController.getPlayersController().getCurrentPlayer().getName() + "'s turn!");
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
              dialogBox.refresh(e.getMessage());

              updatePlayerPositions(initialPlayerPositions);

            }
          });

          ((HappeningDialogBox) dialogBox).getNoButton().setOnAction(dontBuyCrown -> {
            leaderboard.update();
            ((ParioMartyGameController) gameController).checkCrownPurchase(false);

            dialogBox.refresh(gameController.getPlayersController().getPreviousPlayer().getName()
                + " did not buy a crown.");

            dieBox.getRollDieButton().setDisable(false);

            updatePlayerPositions(initialPlayerPositions);
          });
        }
        case "RandomActionTile" -> {
          leaderboard.update();

          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);
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
        case "ReturnToStartTile" -> {
          leaderboard.update();
          ((HappeningDialogBox) dialogBox).getConfirmationButton().setDisable(false);
          dialogBox.refresh(
              gameController.getPlayersController().getPreviousPlayer().getName()
                  + " fell down a hole and returned to start!");

          sfxPlayer.openSoundFile(SoundFile.RETURN_TO_START);

          updatePlayerPositions(initialPlayerPositions);
        }
        case "MowserTile" -> {
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

  protected void showMowserActionList(String mowserAction, int[] initialPlayerPositions) {
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


}
