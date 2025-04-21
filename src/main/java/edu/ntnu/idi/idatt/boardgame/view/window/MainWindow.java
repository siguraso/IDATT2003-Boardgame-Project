package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.ArrayList;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
  private final FlowPane ladderBoardSelection;
  private final Button ladderGameButton = new Button("Ladder Game");
  private final Button parioMartyButton = new Button("Pario Marty");
  private final Label sidebarHeader = new Label();

  // ArrayList of players used to store the players that are added to the game
  // private final ArrayList<Player> players = new ArrayList<>();

  /**
   * Constructor for the MainWindow class.
   *
   * @param primaryStage the primary stage for this application.
   */
  public MainWindow(Stage primaryStage) {
    this.window = primaryStage;
    ladderBoardSelection = getLadderGamePage();
  }

  @Override
  public void init() {
    BorderPane boardGameSelection = new BorderPane();
    boardGameSelection.setCenter(ladderBoardSelection);
    boardGameSelection.setTop(getButtonBar());

    root.setCenter(boardGameSelection);

    BorderPane.setAlignment(ladderBoardSelection, Pos.TOP_CENTER);

    root.setStyle("-fx-background-color: bg_300;");

    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add(Objects.requireNonNull(getClass()
        .getResource("/Styles/Style.css")).toExternalForm());

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

  private HBox getButtonBar() {
    HBox buttons = new HBox();

    buttons.setAlignment(Pos.CENTER);
    buttons.setSpacing(10);

    buttons.getStyleClass().add("button-bar");

    buttons.getChildren().add(ladderGameButton);
    buttons.getChildren().add(parioMartyButton);

    return buttons;
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

      sidebar.getChildren().addAll(sidebarHeader, getPlayerSelection());
      sidebar.setAlignment(Pos.TOP_CENTER);
      sidebar.setSpacing(10);

      root.setRight(sidebar);

      isSidebarVisible = true;

    } else {
      sidebarHeader.setText("Current game: \n" + boardType.getBoardName());
    }
  }

  private VBox getPlayerSelection() {

    VBox playerSelection = new VBox();

    playerSelection.setSpacing(5);
    playerSelection.setAlignment(Pos.CENTER);

    Button addPlayerButton = new Button("Add Player");

    VBox playerSelectionView = new VBox();

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

    // add a line separator
    Line separator = new Line();

    separator.setStyle("-fx-stroke: bg_200; -fx-stroke-width: 1;");
    separator.setStartX(0);
    separator.setStartY(0);
    separator.setEndX(200);
    separator.setEndY(0);

    separator.setOpacity(0.5);

    Label playerSelectionHeader = new Label("Add Players: ");

    playerSelection.getChildren()
        .addAll(separator, playerSelectionHeader, addPlayerButton, playerSelectionView);

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
        case "Paul" -> {
          playerImage.setImage(new Image(
              Objects.requireNonNull(getClass()
                  .getResourceAsStream("/Images/player-pieces/paul.png"))));
        }
        case "Evil Paul" -> {
          playerImage.setImage(new Image(
              Objects.requireNonNull(getClass()
                  .getResourceAsStream("/Images/player-pieces/evil_paul.png"))));
        }
        case "Konkey Dong" -> {
          playerImage.setImage(new Image(
              Objects.requireNonNull(getClass()
                  .getResourceAsStream("/Images/player-pieces/konkey_dong.png"))));
        }
        case "Mariotinelli" -> {
          playerImage.setImage(new Image(
              Objects.requireNonNull(getClass()
                  .getResourceAsStream("/Images/player-pieces/mariotinelli.png"))));
        }
        default -> {
          playerImage.setImage(new Image(
              Objects.requireNonNull(getClass()
                  .getResourceAsStream("/Images/player-pieces/default.png"))));
        }
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

}
