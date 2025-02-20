
package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.model.player.Player;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * A class for the Leaderboard component in the game window. The leaderboard is a table that
 * displays the players in the game, their current tile, and therefore placement.
 */
public class Leaderboard implements WindowComponent {

  private final HashMap<Integer, Player> players;

  private final GridPane leaderboard = new GridPane();

  /**
   * A constructor for the Leaderboard class.
   *
   * @param players A HashMap containing the players currently in the game.
   */
  public Leaderboard(HashMap<Integer, Player> players) {
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
      leaderboard.add(new Label(players.get(player).getCurrentTile().getTileNumber() + ""), 1,
          player);
      leaderboard.add(new Label(player + 1 + ""), 2, player);
    });
  }

  @Override
  public Node getComponent() {
    GridPane leaderboard = new GridPane();
    leaderboard.setMinHeight(123);
    leaderboard.setMinWidth(300);
    leaderboard.setHgap(30);
    leaderboard.setVgap(10);
    leaderboard.getColumnConstraints().addAll(
        new ColumnConstraints(100),
        new ColumnConstraints(100),
        new ColumnConstraints(100)
    );

    this.players.keySet().forEach(player -> {
      leaderboard.add(new Label(players.get(player).getName()), 0, player);
      leaderboard.add(new Label(players.get(player).getCurrentTile().getTileNumber() + ""), 1,
          player);
      leaderboard.add(new Label(player + 1 + "."), 2, player);
    });

    return leaderboard;
  }


}
