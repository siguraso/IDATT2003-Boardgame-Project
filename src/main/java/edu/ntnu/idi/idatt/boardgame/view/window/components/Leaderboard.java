package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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

  private final HashMap<String, Player> players;

  private final GridPane leaderboard = new GridPane();

  /**
   * A constructor for the Leaderboard class.
   *
   * @param players A HashMap containing the players currently in the game.
   */
  public Leaderboard(HashMap<String, Player> players) {
    // since the leaderboard is a 2 column table, the amount of rows is the amount of players

    this.players = players;
  }

  /**
   * Updates the leaderboard with the current players in the game.
   *
   * @param players The players in the game.
   */
  public void updateLeaderboard(HashMap<Integer, Player> players) {
    leaderboard.getChildren().clear();

    players.keySet().forEach(player -> {
      leaderboard.add(new Label(players.get(player).getName()), 0, player);
      leaderboard.add(new Label(players.get(player).getPosition() + ""), 1,
          player);
      leaderboard.add(new Label(player + 1 + ""), 2, player);
    });
  }

  @Override
  public Node getComponent() {
    VBox leaderboard = new VBox();
    leaderboard.setMinHeight(150);
    leaderboard.setMaxHeight(150);
    leaderboard.setMinWidth(380);
    leaderboard.setMaxWidth(380);
    leaderboard.setAlignment(javafx.geometry.Pos.TOP_CENTER);
    leaderboard.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
    leaderboard.setSpacing(10);

    GridPane leaderboardGrid = new GridPane();
    leaderboardGrid.setHgap(30);
    leaderboardGrid.setVgap(12);
    leaderboardGrid.setAlignment(javafx.geometry.Pos.CENTER);

    leaderboardGrid.getColumnConstraints().addAll(
        new ColumnConstraints(50),
        new ColumnConstraints(200),
        new ColumnConstraints(50)
    );

    // Convert the players map to a list
    ArrayList<Player> playerList = new ArrayList<>(players.values());

    // Sort the list by the current tile number
    playerList.sort(Comparator.comparingInt(Player::getPosition));

    // Add the players to the leaderboard grid
    playerList.forEach(player -> {
      if (playerList.indexOf(player) > 2) {
        return;
      }

      leaderboardGrid.add(new Label((playerList.indexOf(player) + 1) + "."), 0,
          playerList.indexOf(player));
      leaderboardGrid.add(new Label(player.getName()), 1,
          playerList.indexOf(player));
      leaderboardGrid.add(new Label(player.getPosition() + ""), 2,
          playerList.indexOf(player));
    });

    Label header = new Label("Top 3 Players");
    header.getStyleClass().add("header");

    leaderboard.getChildren().addAll(header, leaderboardGrid);

    leaderboard.getStyleClass().add("leaderboard");

    return leaderboard;
  }


}
