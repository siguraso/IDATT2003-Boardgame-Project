package edu.ntnu.idi.idatt.boardgame.view.window;

import edu.ntnu.idi.idatt.boardgame.engine.BoardGame;
import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardFactory;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import edu.ntnu.idi.idatt.boardgame.model.dice.Die;
import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import edu.ntnu.idi.idatt.boardgame.model.player.PlayerPiece;
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Class to create the main window of the application. This winndow is the first window that is
 * displayed when the application is started.
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
      // initiate board game here
      HashMap<String, Player> players = new HashMap<>();
      players.put("player1", new Player("player1", PlayerPiece.MARIOTINELLI));
      players.put("player2", new Player("player2", PlayerPiece.PAUL));
      players.put("player3", new Player("player3", PlayerPiece.PROPELLER_ACCESSORIES));
      players.put("player4", new Player("player4", PlayerPiece.MY_LOVE));
      // add the players to the players hashmap
      // build the board
      // put alla that into the gameWindow

      BoardFactory boardFactory = new BoardFactory();
      Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_SPECIAL);

      BoardGame boardGame = new BoardGame(board, players, new Die(6));

      window.close();
      boardGame.startGame();
    });

    window.setScene(new Scene(startButton, 300, 300));
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
