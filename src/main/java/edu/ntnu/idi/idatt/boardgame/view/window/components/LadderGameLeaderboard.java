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
 * A class for the LadderGameLeaderboard component in the game window. The leaderboard is a table
 * that displays the players in the game, their current tile, and therefore placement.
 */
public class LadderGameLeaderboard implements WindowComponent {

  private final PlayersController playersController;

  private final GridPane leaderboard = new GridPane();

  /**
   * A constructor for the LadderGameLeaderboard class.
   *
   * @param playersController the controller for the players in the game
   */
  public LadderGameLeaderboard(PlayersController playersController) {
    this.playersController = playersController;
  }

  /**
   * Updates the leaderboard with the current players in the game.
   */
  public void update() {
    // Clear the leaderboard grid
    this.leaderboard.getChildren().clear();

    Map<String, Integer> players = playersController.getPlayerPositions();

    // Create a list of map entries and sort them by position (descending order)
    List<Map.Entry<String, Integer>> sortedPlayers = new ArrayList<>(players.entrySet());
    sortedPlayers.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

    // Add the players to the leaderboard grid
    sortedPlayers.forEach(playerEntry -> {
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
    VBox leaderboardVbox = new VBox();
    leaderboardVbox.setMinHeight(110);
    leaderboardVbox.setMinWidth(380);
    leaderboardVbox.setMaxWidth(380);
    leaderboardVbox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
    leaderboardVbox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
    leaderboardVbox.setSpacing(10);

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

    leaderboardVbox.getChildren().addAll(header, this.leaderboard);

    leaderboardVbox.getStyleClass().add("leaderboard");

    return leaderboardVbox;
  }


}
