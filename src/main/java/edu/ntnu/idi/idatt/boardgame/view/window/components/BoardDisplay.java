package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardFactory;
import edu.ntnu.idi.idatt.boardgame.model.board.BoardType;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class BoardDisplay implements WindowComponent {

  private GridPane boardGrid;
  private final int ROWS = 10;
  private final int COLS = 9;
  private final HashMap<Integer, FlowPane> gridTiles = new HashMap<>();

  public BoardDisplay() {
  }

  private FlowPane createTileNode(int w, int h) {
    FlowPane tileBox = new FlowPane();
    //Tile width: 82px, height: 75px
    tileBox.setMaxWidth(w);
    tileBox.setMinWidth(w);
    tileBox.setMaxHeight(h);
    tileBox.setMinHeight(h);
    tileBox.setVgap(5);
    tileBox.setHgap(10);

    tileBox.getStyleClass().add("tile");
    return tileBox;
  }

  public void init(int widthPerTile, int heightPerTile) {
    final Board board = BoardFactory.createBoard(BoardType.LADDER_GAME_REGULAR);
    boardGrid = new GridPane();
    boardGrid.setMaxWidth(widthPerTile * COLS);
    boardGrid.setMaxHeight(heightPerTile * ROWS);
    boardGrid.setAlignment(javafx.geometry.Pos.CENTER);

    board.getTiles().values().forEach(t -> {
      FlowPane tileBox = createTileNode(widthPerTile, heightPerTile);
      final int row = t.getOnscreenPosition()[1];
      final int col = t.getOnscreenPosition()[0];
      /*
      Code for placeholder picture.
      ImageView iv = new ImageView("file:src/main/resources/Images/placeholder.jpg");
      iv.setFitHeight(height);
      iv.setFitWidth(widthPerTile);
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

  public HashMap<Integer, FlowPane> getGridTiles() {
    return gridTiles;
  }

  @Override
  public Node getComponent() {
    return boardGrid;
  }
}