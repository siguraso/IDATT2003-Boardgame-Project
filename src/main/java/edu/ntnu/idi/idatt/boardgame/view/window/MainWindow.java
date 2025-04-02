package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.controller.PlayersController;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardFactory;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
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

  /**
   * Constructor for the MainWindow class.
   */
  public MainWindow(Stage primaryStage) {
    this.window = primaryStage;
  }

  @Override
  public void init() {
    Button startButton = new Button("Start game");

    startButton.setOnAction(e -> {
      // initiate the die, which is more or less going to control the game loop
      Die die = new Die(6);

      // initiate board game here
      HashMap<String, Player> players = new HashMap<>();
      players.put("player1", new Player("player1", PlayerPiece.MARIOTINELLI, die));
      players.put("player2", new Player("player2", PlayerPiece.PAUL, die));
      players.put("player3", new Player("player3", PlayerPiece.EVIL_PAUL, die));
      players.put("player4", new Player("player4", PlayerPiece.KONKEY_DONG, die));
      // add the players to the players hashmap
      // build the board
      // put alla that into the gameWindow

      PlayersController playersController = new PlayersController(players);

      Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_VANILLA);

      GameController gameController = new GameController(die, playersController, board);
      gameController.getPlayersController().setCurrentPlayer("player1");

      BoardGameWindow boardGameWindow = new BoardGameWindow(board, gameController);

      window.close();
      boardGameWindow.show();
    });

    HBox layout = new HBox(20);
    layout.setAlignment(javafx.geometry.Pos.CENTER);

    Scene scene = new Scene(layout, 300, 300);
    layout.getChildren().addAll(startButton);
    scene.getStylesheets().add("file:src/main/resources/Styles/Style.css");

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
}
