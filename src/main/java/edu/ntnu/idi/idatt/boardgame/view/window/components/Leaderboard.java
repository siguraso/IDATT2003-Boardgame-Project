package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

  private final ArrayList<Player> players;

  private final GridPane leaderboard = new GridPane();

  /**
   * A constructor for the Leaderboard class.
   *
   * @param players A HashMap containing the players currently in the game.
   */
  public Leaderboard(ArrayList<Player> players) {
    // since the leaderboard is a 3 column table, the amount of rows are the top 3 players

    this.players = players;
  }

  /**
   * Updates the leaderboard with the current players in the game.
   */
  public void update() {
    leaderboard.getChildren().clear();

    players.sort(Comparator.comparingInt(Player::getPosition));

    List<Player> sortedPlayers = players.reversed();

    // Add the players to the leaderboard grid
    sortedPlayers.forEach(player -> {
      if (sortedPlayers.indexOf(player) > 2) {
        return;
      }

      this.leaderboard.add(new Label((sortedPlayers.indexOf(player) + 1) + "."), 0,
          sortedPlayers.indexOf(player));
      this.leaderboard.add(new Label(player.getName()), 1,
          sortedPlayers.indexOf(player));
      this.leaderboard.add(new Label(player.getPosition() + ""), 2,
          sortedPlayers.indexOf(player));
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

    this.leaderboard.setHgap(30);
    this.leaderboard.setVgap(12);
    this.leaderboard.setAlignment(javafx.geometry.Pos.CENTER);

    this.leaderboard.getColumnConstraints().addAll(
        new ColumnConstraints(50),
        new ColumnConstraints(200),
        new ColumnConstraints(50)
    );

    update();

    Label header = new Label("Top 3 Players");
    header.getStyleClass().add("header");

    leaderboard.getChildren().addAll(header, this.leaderboard);

    leaderboard.getStyleClass().add("leaderboard");

    return leaderboard;
  }


}
