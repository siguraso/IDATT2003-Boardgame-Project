package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class BoardDisplay implements WindowComponent {

  private GridPane boardGrid;
  private final int ROWS = 10;
  private final int COLS = 9;
  private final HashMap<Integer, FlowPane> gridTiles = new HashMap<>();
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
      FlowPane tileBox = new FlowPane();
      tileBox.setMinWidth(tileWidth);
      tileBox.setMinHeight(tileHeight);
      tileBox.setMaxWidth(tileWidth);
      tileBox.setMaxHeight(tileHeight);

      int row = (ROWS - 1) - ((t - 1) / COLS);
      int col = ((row % 2) == (ROWS % 2)) ? (COLS - 1 - (t - 1) % COLS) : (t - 1) % COLS;

      tileBox.setAlignment(javafx.geometry.Pos.CENTER);

      if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
        throw new IllegalStateException("Invalid grid position: Row " + row + ", Col " + col);
      }

      boardGrid.add(tileBox, col, row);
      gridTiles.put(t, tileBox);
    });

    gridTiles.keySet().forEach(t -> {
      switch (tileTypes.get(t)) {
        case "LadderTile" -> {
          gridTiles.get(t).getStyleClass().add("ladder-tile-positive");
          gridTiles.get(gameController.getLadderDestinationTileNumber(t)).getStyleClass()
              .add("ladder-tile-positive-destination");
        }
        default -> {
          gridTiles.get(t).getStyleClass().add("normal-tile");
        }
      }

    });
  }

  public HashMap<Integer, FlowPane> getGridTiles() {
    return gridTiles;
  }

  @Override
  public Node getComponent() {
    return boardGrid;
  }
}