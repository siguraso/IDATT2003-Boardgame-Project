package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.NormalTile;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.Tile;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class BoardDisplay implements WindowComponent {
  private GridPane boardGrid;
  private final int ROWS = 10;
  private final int COLS = 9;

  private final HashMap<Integer, HBox> gridTiles = new HashMap<>();

  public BoardDisplay() {
  }

  private Board testCreateBoard() {
    HashMap<Integer, Tile> tiles = new HashMap<>();

    for (int i = 0; i < 90; i++) {
      int row = (ROWS - 1) - (i / COLS);
      int col = ((row % 2) == (ROWS % 2)) ? (COLS - 1 - i % COLS) : i % COLS;

      int[] position = new int[] {col, row};
      Tile tile = new NormalTile(i + 1, position);
      tiles.put(tile.getTileNumber(), tile);
    }

    // Debugging: Print all tile positions
    System.out.println("All tiles: ");
    /*tiles.values().forEach(t -> System.out.println(
        "Tile " + t.getTileNumber() + " -> Row: " + t.getOnscreenPosition()[1] +
            ", Col: " + t.getOnscreenPosition()[0]));*/
    return new Board(tiles);
  }

  private HBox createTileNode(int w, int h) {
    HBox tileBox = new HBox();
    //Tile width: 82px, height: 75px
    tileBox.setMaxWidth(w);
    tileBox.setMinWidth(w);
    tileBox.setMaxHeight(h);
    tileBox.setMinHeight(h);
    tileBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

    tileBox.getStyleClass().add("tile");
    return tileBox;
  }

  public void init(int width, int height) {
    final Board board = testCreateBoard();
    boardGrid = new GridPane();
    boardGrid.setMaxWidth(width * COLS);
    boardGrid.setMaxHeight(height * ROWS);
    boardGrid.setAlignment(javafx.geometry.Pos.CENTER);

    board.getTiles().values().forEach(t -> {
      HBox tileBox = createTileNode(width, height);
      final int row = t.getOnscreenPosition()[1];
      final int col = t.getOnscreenPosition()[0];
      /*
      Code for placeholder picture.
      ImageView iv = new ImageView("file:src/main/resources/Images/placeholder.jpg");
      iv.setFitHeight(height);
      iv.setFitWidth(width);
      iv.setOpacity((double) t.getTileNumber() / 90);

      tileBox.getChildren().add(iv);
      */
      tileBox.setAlignment(javafx.geometry.Pos.CENTER);

      // Debug print
      //System.out.println("Tile: " + t.getTileNumber() + " -> Row: " + row + ", Col: " + col);

      if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
        throw new IllegalStateException("Invalid grid position: Row " + row + ", Col " + col);
      }

      boardGrid.add(tileBox, col, row);
      gridTiles.put(t.getTileNumber(), tileBox);
    });

  }

  // Update the board display, delete if neccecary
  public void update(StackPane owner) {
    owner.getChildren().clear();
    int tileWidth = (int) owner.getWidth() / 9;
    int tileHeight = (int) owner.getHeight() / 10;

    init(tileWidth, tileHeight);
    owner.getChildren().add(getComponent());
  }

  @Override
  public Node getComponent() {
    return boardGrid;
  }
}