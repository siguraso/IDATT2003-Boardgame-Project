package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPieces;
import edu.ntnu.idi.idatt.boardgame.view.window.components.BoardDisplay;
import edu.ntnu.idi.idatt.boardgame.view.window.components.DieComponent;
import edu.ntnu.idi.idatt.boardgame.view.window.components.HappeningDialogBox;
import edu.ntnu.idi.idatt.boardgame.view.window.components.Leaderboard;
import edu.ntnu.idi.idatt.boardgame.view.window.components.WindowComponent;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Class to create a window that displays a board game, with a sidebar.
 *
 * @author siguraso & MagnusNaesanGaarder
 * @version 1.0
 * @since 1.0
 */
public class BoardGameWindow implements Window {

  // window stage
  private final Stage window = new Stage();

  // different components of the window
  private final BorderPane sidebar = new BorderPane();
  private final BorderPane board = new BorderPane();
  private final DieComponent dieBox = new DieComponent();
  private final BoardDisplay boardGridDisplay = new BoardDisplay();
  private Board gameBoard;
  private Leaderboard leaderboard;

  // methods for window initializing, opening and closing a window.

  @Override
  public void show() {
    window.show();
  }

  @Override
  public void init() {
    // Initialize the window
    BorderPane root = new BorderPane();
    root.setLeft(getBoardRegion());
    root.setRight(getSidebar());

    Scene scene = new Scene(root, 1400, 800);

    scene.getStylesheets().add("file:src/main/resources/Styles/Style.css");

    window.setResizable(false);
    window.setScene(scene);
  }

  @Override
  public void close() {
    window.close();
  }

  /**
   * Method to change the dialog window when needed.
   *
   * @param dialog the node of the dialog that is to be displayed.
   */
  public void setDialog(WindowComponent dialog) {
    sidebar.setTop(null);

    Node newDialog = dialog.getComponent();
    sidebar.setTop(newDialog);
  }

  /**
   * Method to update the leaderboard with the current players in the game.
   *
   * @param players The players in the game.
   */
  public void updateLeaderboard(HashMap<Integer, Player> players) {
    sidebar.setBottom(null);

    leaderboard.updateLeaderboard(players);
    sidebar.setBottom(leaderboard.getComponent());
  }

  // individual methods for setting up different parts of the window.

  private StackPane getBoardRegion() {
    StackPane boardDisplay = new StackPane();
    // padding top: 28px, side: 29px
    boardDisplay.setMinWidth(1000);
    boardDisplay.setMinHeight(800);
    boardDisplay.getStyleClass().add("root");
    int tileWidth = (800 - (2 * 29)) / 9;
    int tileHeight = (800 - (2 * 28)) / 10;

    boardGridDisplay.init(tileWidth, tileHeight);

    StackPane boardGrid = new StackPane();
    boardGrid.getChildren().add(boardGridDisplay.getComponent());


    ImageView boardImage = new ImageView(
        new Image("file:src/main/resources/Images/LadderGameBoard.png"));
    boardImage.setFitHeight(800);
    boardImage.setFitWidth(800);

    //: TODO: changing tiles into special tiles according to the boardlayout

    boardDisplay.getChildren().addAll(boardImage, boardGrid);
    boardDisplay.setAlignment(javafx.geometry.Pos.CENTER);
    boardDisplay.getStyleClass().add("board-region");

    return boardDisplay;
  }

  private BorderPane getSidebar() {
    sidebar.setMinWidth(400);
    sidebar.setPadding(new javafx.geometry.Insets(20, 10, 20, 10));
    sidebar.setTop(new HappeningDialogBox(
        "this is a test message that i, as a male in modern society, has come to accept.")
        .getComponent());
    sidebar.setCenter(dieBox.getComponent());

    // add leaderboard
    HashMap<Integer, Player> players = new HashMap<>();
    players.put(1, new Player("Donny yommy", PlayerPieces.PAUL));
    players.get(1).move(new NormalTile(1, new int[] {12, 12}));

    players.put(2, new Player("Doniell tommy", PlayerPieces.EVIL_PAUL));
    players.get(2).move(new NormalTile(2, new int[] {12, 12}));

    players.put(3, new Player("morra di er mann og faren din liker menn",
        PlayerPieces.PROPELLER_ACCESSORIES));
    players.get(3).move(new NormalTile(3, new int[] {12, 12}));

    leaderboard = new Leaderboard(players);

    sidebar.setBottom(leaderboard.getComponent());

    sidebar.getStyleClass().add("sidebar");
    return sidebar;
  }

}
