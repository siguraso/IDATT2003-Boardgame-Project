package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.controller.PlayersController;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * A {@link WindowComponent} class representing the leaderboard for the ParioMarty game. The
 * leaderboard contains a list of up to four players that display their player piece, name, coins,
 * crowns and position (e.g. 1., 2., etc.).
 *
 * @author siguraso
 * @version 1.0
 * @since 1.0
 */
public class ParioMartyLeaderBoard implements WindowComponent {

  private final PlayersController playersController;

  private final VBox parioMartyLeaderboard = new VBox();

  private final Map<String, ImageView> playerPieces;

  /**
   * Constructor for the ParioMartyLeaderBoard class.
   *
   * @param playersController the controller for the players in the game, used to get the player
   *                          data.
   * @param playerPieces      a {@link Map} of player names to their corresponding {@link ImageView}
   *                          pieces.
   */
  public ParioMartyLeaderBoard(PlayersController playersController,
      Map<String, ImageView> playerPieces) {
    this.playersController = playersController;
    this.playerPieces = playerPieces;
  }

  /**
   * Updates the leaderboard with the current players in the game.
   */
  public void update() {
    // Clear the leaderboard grid
    this.parioMartyLeaderboard.getChildren().clear();

    Map<String, Integer[]> playersMap = new HashMap<>();

    playersController.getPlayersAsParioMarty().forEach(player ->
        // crowns is at index 1, coins is at index 0
        playersMap.put(player.getName(), new Integer[]{player.getCoins(), player.getCrowns()})
    );

    // Sort the players by coins and crowns
    List<Map.Entry<String, Integer[]>> sortedPlayers = new ArrayList<>(playersMap.entrySet());
    // compare by crowns first, then by coins
    sortedPlayers.sort(
        Comparator.comparingInt((Map.Entry<String, Integer[]> entry) -> entry.getValue()[1])
            .thenComparingInt(entry -> entry.getValue()[0]).reversed());

    sortedPlayers.forEach(entry -> {
      String playerName = entry.getKey();
      Integer[] playerData = entry.getValue();

      BorderPane playerRow = createPlayerRow(playerName, playerData);
      BorderPane playerBox = new BorderPane();

      Label placement = new Label((sortedPlayers.indexOf(entry) + 1) + ".");
      placement.setStyle("-fx-font-size: 30;");
      playerBox.setLeft(placement);
      playerBox.setRight(playerRow);

      parioMartyLeaderboard.getChildren().add(playerBox);
    });

  }

  @Override
  public Node getComponent() {
    VBox leaderboard = new VBox();
    parioMartyLeaderboard.setSpacing(10);
    leaderboard.setMinHeight(100);
    leaderboard.getChildren().addAll(new Label("Leaderboard"), parioMartyLeaderboard);

    leaderboard.setAlignment(Pos.CENTER);
    leaderboard.setPadding(new Insets(10));

    update();

    leaderboard.getStyleClass().add("leaderboard");

    return leaderboard;
  }

  private BorderPane createPlayerRow(String playerName, Integer[] playerData) {
    ImageView playerImage = new ImageView(playerPieces.get(playerName).getImage());

    HBox playerImageBox = new HBox();
    playerImageBox.getChildren().add(playerImage);
    playerImageBox.getStyleClass().add("piece-box");
    playerImage.setFitHeight(30);
    playerImage.setFitWidth(30);
    playerImageBox.setMaxHeight(30);
    playerImageBox.setMaxWidth(30);

    ImageView crownImage = new ImageView(new
        Image(
        Objects.requireNonNull(
            getClass().getResourceAsStream("/Images/crown_blk.png"))));
    crownImage.setFitHeight(30);
    crownImage.setFitWidth(34);

    HBox crownsBox = new HBox();
    crownsBox.getChildren().addAll(crownImage, new Label("x " + playerData[1]));
    crownsBox.getChildren().get(1).setStyle("-fx-font-size: 20;");
    crownsBox.setAlignment(Pos.CENTER);

    HBox coinsBox = new HBox();
    ImageView coinImage = new ImageView(new
        Image(
        Objects.requireNonNull(
            getClass().getResourceAsStream("/Images/coin_blk.png"))));
    coinImage.setFitHeight(30);
    coinImage.setFitWidth(34);
    coinsBox.setAlignment(Pos.CENTER);

    coinsBox.getChildren().addAll(coinImage, new Label("x " + playerData[0]));
    coinsBox.getChildren().get(1).setStyle("-fx-font-size: 20;");

    HBox dataBox = new HBox();
    dataBox.getChildren().addAll(coinsBox, crownsBox);
    dataBox.setAlignment(Pos.CENTER);
    dataBox.setSpacing(2);

    HBox playerBox = new HBox();
    Label playerNameLabel = new Label(playerName);
    playerNameLabel.setWrapText(true);
    playerNameLabel.setMaxWidth(145);
    playerBox.getChildren().addAll(playerImageBox, playerNameLabel);
    playerBox.setAlignment(Pos.CENTER);
    playerBox.setSpacing(5);

    BorderPane playerRow = new BorderPane();
    playerRow.setLeft(playerBox);
    playerRow.setRight(dataBox);
    BorderPane.setAlignment(dataBox, Pos.CENTER);

    playerRow.getStyleClass().add("player-row");

    return playerRow;
  }


}
