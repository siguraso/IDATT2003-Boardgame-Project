package edu.ntnu.idi.idatt.boardgame.view.window.components;


import edu.ntnu.idi.idatt.boardgame.controller.PlayersController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * A class for the Leaderboard component in the game window. The leaderboard is a table that
 * displays the players in the game, their current tile, and therefore placement.
 */
public class Leaderboard implements WindowComponent {

  private Map<String, Integer> players;

  private final PlayersController playersController;

  private final GridPane leaderboard = new GridPane();

  /**
   * A constructor for the Leaderboard class.
   *
   * @param playersController the controller for the players in the game
   */
  public Leaderboard(PlayersController playersController) {
    this.playersController = playersController;
  }

  /**
   * Updates the leaderboard with the current players in the game.
   */
  public void update() {
    // Clear the leaderboard grid
    this.leaderboard.getChildren().clear();

    this.players = playersController.getPlayerPositions();

    // Create a list of map entries and sort them by position (descending order)
    List<Map.Entry<String, Integer>> sortedPlayers = new ArrayList<>(players.entrySet());
    sortedPlayers.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

    // Add the players to the leaderboard grid
    sortedPlayers.forEach((playerEntry) -> {
      String playerName = playerEntry.getKey();
      Integer position = playerEntry.getValue();
      int i = sortedPlayers.indexOf(playerEntry);

      leaderboard.add(new Label((i + 1) + "."), 0, i);
      leaderboard.add(new Label(playerName), 1, i);
      leaderboard.add(new Label(position.toString()), 2, i);
    });
  }

  @Override
  public Node getComponent() {
    VBox leaderboard = new VBox();
    leaderboard.setMinHeight(110);
    leaderboard.setMinWidth(380);
    leaderboard.setMaxWidth(380);
    leaderboard.setAlignment(javafx.geometry.Pos.TOP_CENTER);
    leaderboard.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
    leaderboard.setSpacing(10);

    this.leaderboard.setHgap(30);
    this.leaderboard.setVgap(12);
    this.leaderboard.setAlignment(javafx.geometry.Pos.CENTER);

    this.leaderboard.getColumnConstraints().addAll(
        new ColumnConstraints(50),
        new ColumnConstraints(200),
        new ColumnConstraints(50)
    );

    update();

    Label header = new Label("Leaderboard");

    header.getStyleClass().add("header");

    leaderboard.getChildren().addAll(header, this.leaderboard);

    leaderboard.getStyleClass().add("leaderboard");

    return leaderboard;
  }


}
