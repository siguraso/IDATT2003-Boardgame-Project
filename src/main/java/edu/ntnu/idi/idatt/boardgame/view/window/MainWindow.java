package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.controller.LadderGameController;
import edu.ntnu.idi.idatt.boardgame.controller.ParioMartyGameController;
import edu.ntnu.idi.idatt.boardgame.controller.PlayersController;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.io.player.PlayersReaderCsv;
import edu.ntnu.idi.idatt.boardgame.model.io.player.PlayersWriterCsv;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Class to create the main window of the application. This winndow is the first window that is
 * displayed when the application is started.
 *
 * <p>Here, the user can start whatever game they wish to start, and the game will be initiated</p>
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class MainWindow implements Window {

  private final Stage window;

  // BoardType that is currently selected
  private BoardType boardType = null;

  private boolean isSidebarVisible = false;

  private final BorderPane root = new BorderPane();
  private final BorderPane boardGameSelection = new BorderPane();
  private final FlowPane ladderBoardSelection;
  private final FlowPane parioMartyBoardSelection;
  private final HBox buttonBar;
  private final Button ladderGameButton = new Button("Ladder Game");
  private final Button parioMartyButton = new Button("Pario Marty");
  private final Label sidebarHeader = new Label();

  // player selection view
  private final VBox playerSelectionView = new VBox();
  private final Label errorLabel = new Label();

  // start game buttons
  private final HBox startGameButtons = new HBox();
  private final Button startGameJsonButton = new Button("Start Game (From .json)");
  private final Button startGameButton = new Button("Start Game");

  // ArrayList of players used to store the players that are added to the game
  private final PlayersController playersController = new PlayersController();

  // check weather or not to use two dice
  private boolean useTwoDice = false;
  private boolean useJson = false;
  private String jsonFilePath = null;

  // warning dialog
  private final WarningDialogWindow warningDialog = new WarningDialogWindow();

  /**
   * Constructor for the MainWindow class.
   *
   * @param primaryStage the primary stage for this application.
   */
  public MainWindow(Stage primaryStage) {
    this.window = primaryStage;
    ladderBoardSelection = getLadderGamePage();
    parioMartyBoardSelection = getParioMartyPage();
    buttonBar = getButtonBar();
  }

  @Override
  public void init() {
    boardGameSelection.setCenter(ladderBoardSelection);
    boardGameSelection.setTop(buttonBar);

    root.setCenter(boardGameSelection);

    BorderPane.setAlignment(ladderBoardSelection, Pos.TOP_CENTER);

    root.setStyle("-fx-background-color: bg_300;");

    Scene scene = new Scene(root, 900, 600);
    scene.getStylesheets().add(Objects.requireNonNull(getClass()
        .getResource("/Styles/Style.css")).toExternalForm());

    window.setMinWidth(900);
    window.setMinHeight(735);
    window.setScene(scene);
  }

  @Override
  public void show() {
    window.show();
  }

  @Override
  public void close() {
    window.close();
  }

  private HBox getButtonBar() {
    HBox buttons = new HBox();

    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(10);

    buttons.getStyleClass().add("button-bar");

    ladderGameButton.setOnAction(onPressed -> {
      boardGameSelection.setCenter(ladderBoardSelection);
      boardType = null;
    });

    parioMartyButton.setOnAction(onPressed -> {
      boardGameSelection.setCenter(parioMartyBoardSelection);
      boardType = null;
    });

    buttons.getChildren().add(ladderGameButton);
    buttons.getChildren().add(parioMartyButton);

    return buttons;
  }

  private FlowPane getLadderGamePage() {
    FlowPane flowPane = new FlowPane();

    // add all the board selection views to the flow pane
    flowPane.getChildren().addAll(getBoardSelectionView(BoardType.LADDER_GAME_REGULAR,
            "Regular Ladder Game"),
        getBoardSelectionView(BoardType.LADDER_GAME_SPECIAL, "Special Ladder Game"),
        getBoardSelectionView(BoardType.LADDER_GAME_JSON, "Load Board From .json"));

    flowPane.setVgap(20);
    flowPane.setHgap(20);

    flowPane.setAlignment(Pos.CENTER);
    flowPane.setPadding(new Insets(20, 20, 20, 20));

    return flowPane;
  }

  private FlowPane getParioMartyPage() {
    FlowPane flowPane = new FlowPane();

    flowPane.getChildren().add(getBoardSelectionView(BoardType.PARIO_MARTY, "Pario Marty Game"));

    flowPane.setVgap(20);
    flowPane.setHgap(20);

    flowPane.setAlignment(Pos.CENTER);
    flowPane.setPadding(new Insets(20, 20, 20, 20));

    return flowPane;
  }

  private VBox getBoardSelectionView(BoardType board, String title) {
    VBox boardSelectionView = new VBox();
    boardSelectionView.getStyleClass().add("board-selection-view");

    Label titleHeader = new Label(title);
    titleHeader.getStyleClass().add("header");

    ImageView boardImage = new ImageView(new Image(
        Objects.requireNonNull(getClass().getResourceAsStream(board.getFilePath()))));

    boardImage.setFitWidth(200);
    boardImage.setFitHeight(200);

    Button chooseBoardButton = getChooseBoardButton(board);

    boardSelectionView.getChildren().addAll(titleHeader, boardImage, chooseBoardButton);
    boardSelectionView.setSpacing(10);
    boardSelectionView.setAlignment(Pos.CENTER);

    boardSelectionView.setPadding(new Insets(10));
    boardSelectionView.getStyleClass().add("board-selection-view");

    boardSelectionView.setAlignment(Pos.CENTER);

    return boardSelectionView;
  }

  private Button getChooseBoardButton(BoardType board) {
    Button chooseBoardButton = new Button("Choose Board");

    chooseBoardButton.setOnAction(onPressed -> {
      boardType = board;
      startGameButton.setText("Start Game");

      showSidebar();

      if (boardType == BoardType.LADDER_GAME_JSON) {
        startGameButton.setText("Open .json File");
        if (startGameButtons.getChildren().size() > 1) {
          startGameButtons.getChildren().remove(1);
        }

        // define the action for the start game button to fetch a json file
        startGameButton.setOnAction(onPress -> {
          try {
            jsonFileChooserSequence();
          } catch (IllegalStateException e) {
            warningDialog.update(
                "There is something wrong with the provided board file: \n" + e.getMessage(),
                "Error creating board");
            warningDialog.show();
            playersController.clearPlayers();

          } catch (RuntimeException e) {
            warningDialog.update("There was an error loading the file: \n" + e.getMessage(),
                "Error reading file");
            warningDialog.show();
            playersController.clearPlayers();
          }
        });

      } else if (boardType != BoardType.PARIO_MARTY) {
        if (startGameButtons.getChildren().size() < 2) {
          startGameButtons.getChildren().add(startGameJsonButton);
        }

        startGameButton.setOnAction(onPress ->
            startGame()
        );
      } else {
        if (startGameButtons.getChildren().size() > 1) {
          startGameButtons.getChildren().remove(1);
        }

        startGameButton.setOnAction(onPress ->
            startGame()
        );
      }

    });
    return chooseBoardButton;
  }

  private void showSidebar() {
    if (!isSidebarVisible) {
      VBox sidebar = new VBox();
      sidebar.getStyleClass().add("sidebar");

      sidebar.setPadding(new Insets(10, 10, 10, 10));
      sidebar.setMaxWidth(300);
      sidebar.setPrefWidth(300);

      sidebarHeader.setText("Current game: \n" + boardType.getBoardName());
      sidebarHeader.setStyle("-fx-font-size: 18px; -fx-text-alignment: center;");

      startGameButtons.getChildren().addAll(startGameButton, startGameJsonButton);
      startGameButtons.setAlignment(Pos.CENTER);
      startGameButtons.setSpacing(10);

      startGameButton.setOnAction(onPressed ->
          startGame()
      );

      startGameJsonButton.setOnAction(onPressed -> {
        useJson = true;
        startGame();
      });

      Line separator = new Line();
      separator.setStyle("-fx-stroke: bg_200; -fx-stroke-width: 1;");
      separator.setStartX(0);
      separator.setStartY(0);
      separator.setEndX(200);
      separator.setEndY(0);

      VBox startGameButtonsBox = new VBox();

      startGameButtonsBox.getChildren().addAll(separator, startGameButtons);
      startGameButtonsBox.setAlignment(Pos.CENTER);
      startGameButtonsBox.setSpacing(15);
      startGameButtonsBox.setPadding(new Insets(10, 0, 10, 0));

      sidebar.getChildren().addAll(sidebarHeader, getPlayerSelection(), getNumberOfDiceComponent(),
          startGameButtonsBox);
      sidebar.setAlignment(Pos.CENTER);
      sidebar.setSpacing(10);

      root.setRight(sidebar);

      isSidebarVisible = true;

    } else {
      sidebarHeader.setText("Current game: \n" + boardType.getBoardName());
    }
  }

  private VBox getPlayerSelection() {

    VBox playerSelection = new VBox();

    playerSelection.setSpacing(10);
    playerSelection.setAlignment(Pos.TOP_CENTER);
    playerSelection.setPadding(new Insets(10, 0, 10, 0));
    playerSelection.setPrefHeight(380);

    Button addPlayerButton = new Button("Add Player");

    // add two player profile editors to the player selection view to start with
    playerSelectionView.getChildren().add(getPlayerProfileEditor());
    playerSelectionView.getChildren().add(getPlayerProfileEditor());

    addPlayerButton.setOnAction(onPressed -> {
      if (playerSelectionView.getChildren().size() < 4) {
        playerSelectionView.getChildren().add(getPlayerProfileEditor());
      }
    });

    playerSelectionView.setAlignment(Pos.TOP_CENTER);
    playerSelectionView.setPadding(new Insets(15, 0, 15, 0));
    playerSelectionView.setSpacing(10);
    playerSelectionView.getStyleClass().add("player-selection-view");

    HBox fileButtons = new HBox();
    Button readButton = new Button("Load from .csv file");
    Button writeButton = new Button("Save to .csv file");
    fileButtons.getChildren().addAll(readButton, writeButton);
    fileButtons.setSpacing(10);
    fileButtons.setAlignment(Pos.CENTER);

    readButton.setOnAction(onPressed ->
        csvFileChooserSequence()
    );

    writeButton.setOnAction(onPressed ->
        showFileWriterCsv()
    );

    // add a line separator and header
    Line separator = new Line();

    separator.setStyle("-fx-stroke: bg_200; -fx-stroke-width: 1;");
    separator.setStartX(0);
    separator.setStartY(0);
    separator.setEndX(200);
    separator.setEndY(0);

    Label playerSelectionHeader = new Label("Add Players: ");

    errorLabel.getStyleClass().add("error-label");
    // placeholder text for error label so that the height of the VBox does not change when
    // showing the label.
    errorLabel.setText("error");
    errorLabel.setVisible(false);

    playerSelection.getChildren()
        .addAll(separator, playerSelectionHeader, addPlayerButton, playerSelectionView, errorLabel,
            fileButtons);

    return playerSelection;
  }

  private HBox getPlayerProfileEditor() {
    TextField playerName = new TextField();
    playerName.setPromptText("Enter Name");
    playerName.setPrefWidth(100);

    playerName.textProperty().addListener((observable, oldValue, newValue) -> {
      playerSelectionView.getStyleClass().remove("player-selection-view-error");
      errorLabel.setVisible(false);

      if (newValue.length() > 30) {
        playerName.setText(oldValue);
      }

    });

    ImageView playerImage = new ImageView(new
        Image(
        Objects.requireNonNull(
            getClass().getResourceAsStream("/Images/player-pieces/default.png"))));

    HBox playerPieceBox = new HBox();
    playerPieceBox.getChildren().addAll(playerImage);
    playerPieceBox.getStyleClass().add("piece-box");

    playerImage.setFitHeight(30);
    playerImage.setFitWidth(30);

    ComboBox<String> playerPiece = new ComboBox<>();
    playerPiece.setPromptText("Piece");
    playerPiece.getItems().addAll("Paul", "Evil Paul", "Konkey Dong", "Mariotinelli", "My Love",
        "My Love (hat)", "Propeller Accessories", "Locked in Snowman");

    playerPiece.valueProperty().addListener((observable, oldValue, newValue) -> {
      switch (newValue) {
        case "Paul" -> playerImage.setImage(new Image(
            Objects.requireNonNull(getClass()
                .getResourceAsStream("/Images/player-pieces/paul.png"))));

        case "Evil Paul" -> playerImage.setImage(new Image(
            Objects.requireNonNull(getClass()
                .getResourceAsStream("/Images/player-pieces/evil_paul.png"))));

        case "Konkey Dong" -> playerImage.setImage(new Image(
            Objects.requireNonNull(getClass()
                .getResourceAsStream("/Images/player-pieces/konkey_dong.png"))));

        case "Mariotinelli" -> playerImage.setImage(new Image(
            Objects.requireNonNull(getClass()
                .getResourceAsStream("/Images/player-pieces/mariotinelli.png"))));

        case "My Love" -> playerImage.setImage(new Image(
            Objects.requireNonNull(getClass()
                .getResourceAsStream("/Images/player-pieces/my_love.png"))));

        case "My Love (hat)" -> playerImage.setImage(new Image(
            Objects.requireNonNull(getClass()
                .getResourceAsStream("/Images/player-pieces/my_love_with_hat.png"))));

        case "Propeller Accessories" -> playerImage.setImage(new Image(
            Objects.requireNonNull(getClass()
                .getResourceAsStream("/Images/player-pieces/propeller_accessories.png"))));

        case "Locked in Snowman" -> playerImage.setImage(new Image(
            Objects.requireNonNull(getClass()
                .getResourceAsStream("/Images/player-pieces/locked_in_snowman.png"))));

        default -> playerImage.setImage(new Image(
            Objects.requireNonNull(getClass()
                .getResourceAsStream("/Images/player-pieces/default.png"))));

      }
    });

    Button removePlayerButton = new Button("—");
    removePlayerButton.getStyleClass().add("remove-button");

    removePlayerButton.setOnAction(onPressed -> {
      HBox parent = (HBox) removePlayerButton.getParent();
      VBox grandParent = (VBox) parent.getParent();

      if (grandParent.getChildren().size() > 2) {
        grandParent.getChildren().remove(parent);
      }
    });

    HBox playerProfileEditor = new HBox();
    playerProfileEditor.getChildren().addAll(playerPieceBox, playerName, playerPiece,
        removePlayerButton);

    playerProfileEditor.setAlignment(Pos.CENTER);
    playerProfileEditor.setSpacing(6);

    return playerProfileEditor;
  }

  private VBox getNumberOfDiceComponent() {
    ToggleGroup toggleGroup = new ToggleGroup();

    RadioButton oneDieRadioButton = new RadioButton("1 Die");
    oneDieRadioButton.setToggleGroup(toggleGroup);
    oneDieRadioButton.setSelected(true);

    RadioButton twoDiceRadioButton = new RadioButton("2 Dice");
    twoDiceRadioButton.setToggleGroup(toggleGroup);

    oneDieRadioButton.setOnAction(onPressed ->
        useTwoDice = false
    );

    twoDiceRadioButton.setOnAction(onPressed ->
        useTwoDice = true
    );

    HBox numberOfDiceToggle = new HBox();

    numberOfDiceToggle.getChildren().addAll(oneDieRadioButton, twoDiceRadioButton);
    numberOfDiceToggle.setSpacing(10);
    numberOfDiceToggle.setAlignment(Pos.CENTER);

    Line separator = new Line();

    separator.setStyle("-fx-stroke: bg_200; -fx-stroke-width: 1;");
    separator.setStartX(0);
    separator.setStartY(0);
    separator.setEndX(200);
    separator.setEndY(0);

    Label numberOfDiceHeader = new Label("Number of Dice: ");

    VBox numberOfDiceComponent = new VBox();

    numberOfDiceComponent.getChildren().addAll(separator, numberOfDiceHeader, numberOfDiceToggle);
    numberOfDiceComponent.setAlignment(Pos.CENTER);
    numberOfDiceComponent.setSpacing(10);
    numberOfDiceComponent.setPadding(new Insets(10, 10, 10, 10));

    VBox.setMargin(separator, new Insets(0, 0, 10, 0));

    return numberOfDiceComponent;
  }

  private void jsonFileChooserSequence() {
    if (arePlayersInvalid()) {
      playerSelectionView.getStyleClass().add("player-selection-view-error");
      errorLabel.setText("Please fill in all player fields!");
      errorLabel.setVisible(true);
      return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open JSON File");

    fileChooser.getExtensionFilters().add(new ExtensionFilter(
        "JSON files (*.json)", "*.json"));

    fileChooser.setInitialDirectory(
        new File(System.getProperty("user.home")));

    File file = fileChooser.showOpenDialog(window);

    if (file != null) {
      jsonFilePath = file.getAbsolutePath();
      useJson = true;

      startGame();
    }
  }

  private void csvFileChooserSequence() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open CSV File");

    fileChooser.getExtensionFilters().add(new ExtensionFilter(
        "CSV files (*.csv)", "*.csv"));

    fileChooser.setInitialDirectory(
        new File(System.getProperty("user.home")));

    File file = fileChooser.showOpenDialog(window);

    if (file != null) {
      try {

        PlayersReaderCsv playersReaderCsv = new PlayersReaderCsv();

        playersController.setPlayers(
            playersReaderCsv.readPlayersFile(file.getAbsolutePath()));

        if (playersController.getPlayers().size() > 4) {
          warningDialog.update("You can have max 4 players in the game!", "Too many players");
          warningDialog.show();
          return;
        } else if (playersController.getPlayers().size() < 2) {
          warningDialog.update("You need at least 2 players to start the game!", "Too few players");
          warningDialog.show();
          return;
        }

        int numberOfPlayers = playersController.getPlayers().size();
        int playersInSelection = playerSelectionView.getChildren().size();

        int delta = numberOfPlayers - playersInSelection;

        if (delta > 0) {
          for (int i = 0; i < delta; i++) {
            playerSelectionView.getChildren().add(getPlayerProfileEditor());
          }
        } else if (delta < 0) {
          for (int i = 0; i < Math.abs(delta); i++) {
            playerSelectionView.getChildren().removeLast();
          }
        }

        playersController.getPlayers().forEach(player -> {
          int index = playersController.getPlayers().indexOf(player);

          HBox playerProfileEditor = (HBox) playerSelectionView.getChildren().get(index);
          ((TextField) playerProfileEditor.getChildren().get(1)).setText(player.getName());
          ((ComboBox<String>) playerProfileEditor.getChildren().get(2))
              .setValue(player.getPlayerPiece().getPieceName());
        });
      } catch (IllegalArgumentException e) {
        if (e.getMessage().contains("Duplicate player name")) {
          playerSelectionView.getStyleClass().add("player-selection-view-error");
          errorLabel.setVisible(true);
          errorLabel.setText(e.getMessage() + "!");
        } else {
          warningDialog.update("There was an error reading the file. \n "
              + "Is it formatted correctly?", "Error reading file");
          warningDialog.show();
        }
      } catch (Exception e) {
        warningDialog.update("There was an error reading the file. \n "
            + "Is it formatted correctly?", "Error reading file");
        warningDialog.show();
      } finally {
        playersController.clearPlayers();
      }
    }
  }

  private void showFileWriterCsv() {
    if (arePlayersInvalid()) {
      playerSelectionView.getStyleClass().add("player-selection-view-error");
      errorLabel.setText("Please fill in all player fields!");
      errorLabel.setVisible(true);
      return;
    }

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save CSV File");

    fileChooser.getExtensionFilters().add(new ExtensionFilter(
        "CSV files (*.csv)", "*.csv"));

    fileChooser.setInitialDirectory(
        new File(System.getProperty("user.home")));

    File file = fileChooser.showSaveDialog(window);

    if (file != null) {
      playerSelectionView.getChildren().forEach(profile -> {
        String playerName = ((TextField) ((HBox) profile).getChildren().get(1)).getText();
        String playerPieceString = ((ComboBox<String>) ((HBox) profile).getChildren()
            .get(2)).getValue();

        PlayerPiece playerPiece = selectPlayerPiece(playerPieceString);

        playersController.addLadderGamePlayer(playerName, playerPiece);

      });

      PlayersWriterCsv playersWriter = new PlayersWriterCsv();

      try {
        playersWriter.writePlayersFile(file.getAbsolutePath(), file.getName(),
            new ArrayList<>(playersController.getPlayers()));
      } catch (Exception e) {
        warningDialog.update("There was an error writing the file!",
            "Error writing file");
        warningDialog.show();
      } finally {
        playersController.clearPlayers();
      }
    }
  }

  private void startGame() {
    try {
      if (boardType != BoardType.PARIO_MARTY) {
        // get all players
        playerSelectionView.getChildren().forEach(playerProfile -> {
          HBox playerProfileEditor = (HBox) playerProfile;

          PlayerPiece playerPiece = selectPlayerPiece(
              ((ComboBox<String>) playerProfileEditor.getChildren()
                  .get(2)).getValue());

          playersController.addLadderGamePlayer(
              ((TextField) playerProfileEditor.getChildren().get(1)).getText(),
              playerPiece);

        });

        GameController gameController = new LadderGameController(playersController, useTwoDice);
        gameController.setBoard(boardType, useJson, jsonFilePath);

        BoardGameWindow gameWindow = new LadderGameWindow(gameController, useTwoDice);

        close();
        gameWindow.show();

      } else {
        // get all players
        playerSelectionView.getChildren().forEach(playerProfile -> {
              HBox playerProfileEditor = (HBox) playerProfile;

              PlayerPiece playerPiece = selectPlayerPiece(
                  ((ComboBox<String>) playerProfileEditor.getChildren()
                      .get(2)).getValue());

              playersController.addParioMartyPlayer(
                  ((TextField) playerProfileEditor.getChildren().get(1)).getText(),
                  playerPiece);
            }
        );

        GameController gameController = new ParioMartyGameController(playersController, useTwoDice);
        gameController.setBoard(boardType, useJson, jsonFilePath);

        BoardGameWindow gameWindow = new ParioMartyGameWindow(gameController, useTwoDice);

        close();
        gameWindow.show();
      }
    } catch (NullPointerException e) {
      playersController.clearPlayers();

      playerSelectionView.getStyleClass().add("player-selection-view-error");
      errorLabel.setText("Please fill in all player fields!");
      errorLabel.setVisible(true);
    } catch (IllegalArgumentException e) {
      if (e.getMessage().contains("Duplicate player name")) {
        playersController.clearPlayers();
        playerSelectionView.getStyleClass().add("player-selection-view-error");
        errorLabel.setText(e.getMessage() + "!");
        errorLabel.setVisible(true);
      } else {
        playersController.clearPlayers();
        playerSelectionView.getStyleClass().add("player-selection-view-error");
        errorLabel.setText("Please fill in all player fields!");
        errorLabel.setVisible(true);
      }
    }

  }

  private PlayerPiece selectPlayerPiece(String playerPieceString) {
    switch (playerPieceString) {
      case "Evil Paul" -> {
        return PlayerPiece.EVIL_PAUL;
      }
      case "Konkey Dong" -> {
        return PlayerPiece.KONKEY_DONG;
      }
      case "Mariotinelli" -> {
        return PlayerPiece.MARIOTINELLI;
      }
      case "My Love" -> {
        return PlayerPiece.MY_LOVE;
      }
      case "My Love (hat)" -> {
        return PlayerPiece.MY_LOVE_WITH_HAT;
      }
      case "Propeller Accessories" -> {
        return PlayerPiece.PROPELLER_ACCESSORIES;
      }
      case "Locked in Snowman" -> {
        return PlayerPiece.LOCKED_IN_SNOWMAN;
      }
      default -> {
        return PlayerPiece.PAUL;
      }
    }
  }

  private boolean arePlayersInvalid() {
    var arePlayersValidWrapper = new Object() {
      boolean areValid = true;
    };

    playerSelectionView.getChildren().forEach(playerProfile -> {
      String piece = ((ComboBox<String>) ((HBox) playerProfile).getChildren().get(2)).getValue();
      String name = ((TextField) ((HBox) playerProfile).getChildren().get(1)).getText();

      if (piece == null || name == null || piece.isEmpty() || name.isBlank()) {
        arePlayersValidWrapper.areValid = false;
      }

    });
    return !arePlayersValidWrapper.areValid;
  }

}
