package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
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
public class BoardGameWindow implements Window {

  // window stage
  private final Stage window = new Stage();

  // different components of the window
  private final BorderPane sidebar = new BorderPane();
  private final BorderPane board = new BorderPane();
  private Board gameBoard;
  private Leaderboard leaderboard;
  private final DieComponent dieBox = new DieComponent(this);

  // player pieces

  private final ImageView[] playerPieces = new ImageView[4];
  private ImageView currentPlayerPiece;

  private final BoardDisplay boardGridDisplay = new BoardDisplay();
  private final StackPane boardGrid = new StackPane();

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

    // TODO add actual players and pieces, and remove foo
    ImageView foo = new ImageView("file:src/main/resources/Images/placeholder2.png");
    foo.setFitHeight(50);
    foo.setFitWidth(50);
    currentPlayerPiece = foo;

    boardGridDisplay.getGridTiles().get(1).getChildren().add(foo);

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

    boardGrid.getChildren().add(boardGridDisplay.getComponent());

    ImageView boardImage = new ImageView(
        new Image("file:src/main/resources/Images/LadderGameBoard_default.png"));
    boardImage.setFitHeight(800);
    boardImage.setFitWidth(800);

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
    players.put(1, new Player("Donny yommy", PlayerPiece.PAUL));
    players.get(1).move(new NormalTile(1, new int[]{12, 12}));

    players.put(2, new Player("Doniell tommy", PlayerPiece.EVIL_PAUL));
    players.get(2).move(new NormalTile(2, new int[]{12, 12}));

    players.put(3,
        new Player("morra di er mann og faren din liker menn", PlayerPiece.MARIOTINELLI));
    players.get(3).move(new NormalTile(3, new int[]{12, 12}));

    leaderboard = new Leaderboard(players);

    sidebar.setBottom(leaderboard.getComponent());

    sidebar.getStyleClass().add("sidebar");
    return sidebar;
  }

  public void moveCurrentPlayer(int steps, int initialPosition) {
    int currentPlayerPosition = initialPosition;

    // create a timeline to animate the player's movement
    Timeline timeline = new Timeline();

    for (int i = 1; i <= steps; i++) {
      int nextPosition = currentPlayerPosition + i;

      KeyFrame keyFrame = new KeyFrame(Duration.millis(300 * i), event -> {
        // Remove the player from the current position
        boardGridDisplay.getGridTiles().get(nextPosition - 1).getChildren().clear();
        // Add the player to the new position
        boardGridDisplay.getGridTiles().get(nextPosition).getChildren().add(currentPlayerPiece);
      });

      timeline.getKeyFrames().add(keyFrame);
    }

    timeline.play();
  }

}
