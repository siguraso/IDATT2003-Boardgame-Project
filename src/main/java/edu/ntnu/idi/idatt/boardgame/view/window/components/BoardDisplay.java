package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import java.util.HashMap;
import java.util.Objects;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class BoardDisplay implements WindowComponent {

  private GridPane boardGrid;
  private final int ROWS = 10;
  private final int COLS = 9;
  private final HashMap<Integer, FlowPane> gridTiles = new HashMap<>();
  private final HashMap<Integer, StackPane> gridTileStack = new HashMap<>();
  private final GameController gameController;

  public BoardDisplay(GameController gameController) {
    this.gameController = gameController;
  }

  public void init(int tileWidth, int tileHeight, HashMap<Integer, String> tileTypes) {
    boardGrid = new GridPane();
    boardGrid.setMaxWidth(tileWidth * COLS);
    boardGrid.setMaxHeight(tileHeight * ROWS);
    boardGrid.setAlignment(javafx.geometry.Pos.CENTER);
    boardGrid.getStyleClass().add("board");

    tileTypes.keySet().forEach(t -> {
      StackPane tilePane = new StackPane();
      tilePane.setMinWidth(tileWidth);
      tilePane.setMinHeight(tileHeight);
      tilePane.setMaxWidth(tileWidth);
      tilePane.setMaxHeight(tileHeight);

      int row = (ROWS - 1) - ((t - 1) / COLS);
      int col = ((row % 2) == (ROWS % 2)) ? (COLS - 1 - (t - 1) % COLS) : (t - 1) % COLS;

      tilePane.setAlignment(javafx.geometry.Pos.CENTER);

      if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
        throw new IllegalStateException("Invalid grid position: Row " + row + ", Col " + col);
      }

      // add tile number to tileBox
      FlowPane playersBox = new FlowPane();
      playersBox.setMinWidth(tileWidth);
      playersBox.setMinHeight(tileHeight);
      playersBox.setMaxWidth(tileWidth);
      playersBox.setMaxHeight(tileHeight);
      playersBox.setAlignment(Pos.CENTER);
      playersBox.setVgap(5);
      playersBox.setHgap(5);

      Label tileNumber = new Label(t + "");
      tileNumber.setPadding(new Insets(3, 6, 0, 0));
      tileNumber.getStyleClass().add("tile-number");
      tilePane.getChildren().addAll(tileNumber, playersBox);
      StackPane.setAlignment(tileNumber, Pos.TOP_RIGHT);

      boardGrid.add(tilePane, col, row);
      gridTileStack.put(t, tilePane);
      gridTiles.put(t, playersBox);
    });

    // set tile styling for the tiles
    gridTileStack.keySet().forEach(t -> {
      switch (tileTypes.get(t)) {
        case "LadderTile" -> {
          int deltaTileNumber = gameController.getLadderDestinationTileNumber(t) - t;

          if (deltaTileNumber > 0) {
            gridTileStack.get(t).getStyleClass().add("ladder-tile-positive");
            gridTileStack.get(gameController.getLadderDestinationTileNumber(t)).getStyleClass()
                .add("ladder-tile-positive-destination");
          } else {
            gridTileStack.get(t).getStyleClass().add("ladder-tile-negative");
            gridTileStack.get(gameController.getLadderDestinationTileNumber(t)).getStyleClass()
                .add("ladder-tile-negative-destination");
          }
        }

        case "ReturnToStartTile" -> {
          gridTileStack.get(t).getStyleClass().add("return-to-start-tile");
          ImageView icon = new ImageView(
              new Image(Objects.requireNonNull(
                  this.getClass()
                      .getResourceAsStream(
                          "/Images/boards/tile-icons/return_to_start.png"))));

          // wonky image size to not stretch the image (I picked bad image dimensions :( )
          icon.setFitWidth(60);
          icon.setFitHeight(54);

          gridTileStack.get(t).getChildren().add(icon);
          icon.toBack();
        }

        case "RollAgainTile" -> {
          gridTileStack.get(t).getStyleClass().add("roll-again-tile");
          ImageView icon = new ImageView(
              new Image(Objects.requireNonNull(
                  this.getClass()
                      .getResourceAsStream("/Images/boards/tile-icons/roll_again.png"))));
          icon.setFitWidth(60);
          icon.setFitHeight(54);

          gridTileStack.get(t).getChildren().add(icon);
          icon.toBack();
        }

        case "RandomActionTile" -> {
          gridTileStack.get(t).getStyleClass().add("random-action-tile");

          ImageView icon = new ImageView(
              new Image(Objects.requireNonNull(
                  this.getClass()
                      .getResourceAsStream("/Images/boards/tile-icons/random_action.png"))));
          icon.setFitWidth(60);
          icon.setFitHeight(54);

          gridTileStack.get(t).getChildren().add(icon);
          icon.toBack();
        }

        case "WinnerTile" -> {
          gridTileStack.get(t).getStyleClass().add("winner-tile");
        }

        default -> {
          gridTileStack.get(t).getStyleClass().add("normal-tile");
        }
      }

    });

    // set start tile styling
    gridTileStack.get(1).getStyleClass().add("start-tile");
    Label startLabel = new Label("START");
    gridTileStack.get(1).getChildren().add(startLabel);
    startLabel.toBack();

  }

  public HashMap<Integer, FlowPane> getGridTiles() {
    return gridTiles;
  }

  @Override
  public Node getComponent() {
    return boardGrid;
  }
}