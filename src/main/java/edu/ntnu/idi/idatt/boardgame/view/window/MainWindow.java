package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.controller.PlayersController;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
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
  private final VBox playerSelectionView = new VBox();

  // ArrayList of players used to store the players that are added to the game
  private final PlayersController playersController = new PlayersController();

  // check weather or not to use two dice
  private boolean useTwoDice = false;

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
    window.setMinHeight(610);
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
        getBoardSelectionView(BoardType.LADDER_GAME_SPECIAL, "Special Ladder Game"));

    flowPane.setVgap(20);
    flowPane.setHgap(20);

    flowPane.setAlignment(Pos.CENTER);
    flowPane.setPadding(new Insets(20, 20, 20, 20));

    return flowPane;
  }

  private FlowPane getParioMartyPage() {
    FlowPane flowPane = new FlowPane();
    // TODO add all the board selection views to the flow pane

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

    Button chooseBoardButton = new Button("Choose Board");

    chooseBoardButton.setOnAction(onPressed -> {
      boardType = board;
      showSidebar();
    });

    boardSelectionView.getChildren().addAll(titleHeader, boardImage, chooseBoardButton);
    boardSelectionView.setSpacing(10);
    boardSelectionView.setAlignment(Pos.CENTER);

    boardSelectionView.setPadding(new Insets(10));
    boardSelectionView.getStyleClass().add("board-selection-view");

    boardSelectionView.setAlignment(Pos.CENTER);

    return boardSelectionView;
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

      HBox startGameButtons = new HBox();
      Button startGameButton = new Button("Start Game");
      Button startGameJsonButton = new Button("Start Game (JSON)");
      startGameButtons.getChildren().addAll(startGameButton, startGameJsonButton);
      startGameButtons.setAlignment(Pos.CENTER);
      startGameButtons.setSpacing(10);

      startGameButton.setOnAction(onPressed -> {
        playerSelectionView.getChildren().forEach(playerProfile -> {
          HBox playerProfileEditor = (HBox) playerProfile;
          String playerName = ((TextField) playerProfileEditor.getChildren().get(1)).getText();
          String playerPieceString = ((ComboBox<String>) playerProfileEditor.getChildren()
              .get(2)).getValue();
          PlayerPiece playerPiece;

          switch (playerPieceString) {
            case "Paul" -> playerPiece = PlayerPiece.PAUL;
            case "Evil Paul" -> playerPiece = PlayerPiece.EVIL_PAUL;
            case "Konkey Dong" -> playerPiece = PlayerPiece.KONKEY_DONG;
            case "Mariotinelli" -> playerPiece = PlayerPiece.MARIOTINELLI;
            case "My Love" -> playerPiece = PlayerPiece.MY_LOVE;
            case "My Love (hat)" -> playerPiece = PlayerPiece.MY_LOVE_WITH_HAT;
            case "Propeller Accessories" -> playerPiece = PlayerPiece.PROPELLER_ACCESSORIES;
            case "Locked in Snowman" -> playerPiece = PlayerPiece.LOCKED_IN_SNOWMAN;
            default -> playerPiece = null;
          }

          playersController.addPlayer(playerName, playerPiece);
        });

        // start the game by initiating the game controller and the game window
        GameController gameController = new GameController(playersController, useTwoDice);
        gameController.setBoard(boardType);

        BoardGameWindow gameWindow = new BoardGameWindow(gameController);

        window.close();
        gameWindow.show();
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
    playerSelection.setPrefHeight(340);

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

    readButton.setOnAction(onPressed -> {
      // TODO Implement read from file
    });

    writeButton.setOnAction(onPressed -> {
      // TODO Implement write to file
    });

    // add a line separator and header
    Line separator = new Line();

    separator.setStyle("-fx-stroke: bg_200; -fx-stroke-width: 1;");
    separator.setStartX(0);
    separator.setStartY(0);
    separator.setEndX(200);
    separator.setEndY(0);

    Label playerSelectionHeader = new Label("Add Players: ");

    playerSelection.getChildren()
        .addAll(separator, playerSelectionHeader, addPlayerButton, playerSelectionView,
            fileButtons);

    return playerSelection;
  }

  private HBox getPlayerProfileEditor() {
    TextField playerName = new TextField();
    playerName.setPromptText("Enter Name");
    playerName.setPrefWidth(100);

    ImageView playerImage = new ImageView(new
        Image(
        Objects.requireNonNull(
            getClass().getResourceAsStream("/Images/player-pieces/default.png"))));

    HBox playerPieceBox = new HBox();
    playerPieceBox.getChildren().addAll(playerImage);
    playerPieceBox.getStyleClass().add("speaker-box");

    playerImage.setFitHeight(30);
    playerImage.setFitWidth(30);

    ComboBox<String> playerPiece = new ComboBox<>();
    playerPiece.setPromptText("Piece");
    playerPiece.getItems().addAll("Paul", "Evil Paul", "Konkey Dong", "Mariotinelli");

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
    numberOfDiceHeader.getStyleClass().add("header");

    VBox numberOfDiceComponent = new VBox();

    numberOfDiceComponent.getChildren().addAll(separator, numberOfDiceHeader, numberOfDiceToggle);
    numberOfDiceComponent.setAlignment(Pos.CENTER);
    numberOfDiceComponent.setSpacing(10);
    numberOfDiceComponent.setPadding(new Insets(10, 10, 10, 10));

    return numberOfDiceComponent;
  }
}
