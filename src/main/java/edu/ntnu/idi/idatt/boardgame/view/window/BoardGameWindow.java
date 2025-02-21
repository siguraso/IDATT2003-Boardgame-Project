package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.view.window.components.HappeningDialogBox;
import edu.ntnu.idi.idatt.boardgame.view.window.components.Leaderboard;
import edu.ntnu.idi.idatt.boardgame.view.window.components.WindowComponent;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class to create a window that displays a board game, with a sidebar.
 *
 * @author siguraso & MagnusNaesanGaarder
 * @version 1.0
 * @since 1.0
 */
public class BoardGameWindow implements Window {

  private final Stage window = new Stage();
  private final BorderPane sidebar = new BorderPane();
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
    boardDisplay.setMinWidth(1000);
    boardDisplay.setMinHeight(800);
    boardDisplay.getStyleClass().add("root");

    ImageView boardImage = new ImageView(
        new Image("file:src/main/resources/Images/LadderGameBoard.png"));
    boardImage.setFitHeight(800);
    boardImage.setFitWidth(800);

    boardDisplay.getChildren().add(boardImage);
    boardDisplay.getStyleClass().add("board-region");

    return boardDisplay;
  }

  private BorderPane getSidebar() {
    sidebar.setMinWidth(400);
    sidebar.setPadding(new javafx.geometry.Insets(20, 10, 20, 10));

    // placeholder for die
    // TODO get actual sprites for the die
    ImageView diePlaceholder = new ImageView(
        new Image("file:src/main/resources/Images/placeholder.jpg"));
    diePlaceholder.setFitWidth(200);
    diePlaceholder.setFitHeight(200);
    // TODO make the button actually roll the die
    Button rollDieButton = new Button("Roll die");
    rollDieButton.setOnAction(e -> {
      rollDieButton.setDisable(true);
      diePlaceholder.setImage(new Image("file:src/main/resources/Images/placeholder2.png"));
      setDialog(new HappeningDialogBox("morra di er mann "));
      updateLeaderboard(new HashMap<>());
    });

    VBox dieBox = new VBox(diePlaceholder, rollDieButton);
    dieBox.setAlignment(javafx.geometry.Pos.CENTER);
    dieBox.setSpacing(20);
    dieBox.getStyleClass().add("sidebar");

    setDialog(new HappeningDialogBox(
        "This is a test message that i both love and hate, as i, as a male in society, has come to accept."));

    sidebar.setCenter(dieBox);

    // add leaderboard
    HashMap<Integer, Player> players = new HashMap<>();
    players.put(1, new Player("Donny yommy"));
    players.get(1).move(new NormalTile(1, new int[]{12, 12}));

    players.put(2, new Player("Doniell tommy"));
    players.get(2).move(new NormalTile(2, new int[]{12, 12}));

    players.put(3, new Player("morra di er mann og faren din liker menn"));
    players.get(3).move(new NormalTile(3, new int[]{12, 12}));

    leaderboard = new Leaderboard(players);

    sidebar.setBottom(leaderboard.getComponent());

    sidebar.getStyleClass().add("sidebar");
    return sidebar;
  }

}
