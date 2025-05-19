package edu.ntnu.idi.idatt.boardgame.view.window.components;

import edu.ntnu.idi.idatt.boardgame.controller.GameController;
import edu.ntnu.idi.idatt.boardgame.view.window.components.ladder.LadderDrawer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Displays the game board in the window. Renders the board and the tiles on the board based on what
 * {@link HashMap} of tileTypes is given.
 *
 * @author siguraso & MagnusNaessanGaarder
 * @version 1.0
 * @since 1.0
 */
public class BoardDisplay implements WindowComponent {

  private final Logger logger = Logger.getLogger("BoardDisplay");
  private final GridPane boardGrid = new GridPane();
  private final GridPane playersGrid = new GridPane();
  private static final int ROWS = 10;
  private static final int COLS = 9;
  private final HashMap<Integer, FlowPane> gridTiles = new HashMap<>();
  private final HashMap<Integer, StackPane> gridTileStack = new HashMap<>();
  private final GameController gameController;
  private final LadderDrawer ld = new LadderDrawer();
  private final StackPane boardPane = new StackPane();

  private final boolean isParioMarty;

  /**
   * Constructor for the BoardDisplay class.
   *
   * @param gameController the game controller, used to get the different tile types based on the
   *                       board that is loaded, and where the ladder tiles go.
   */
  public BoardDisplay(GameController gameController, boolean isParioMarty) {
    this.gameController = gameController;
    this.isParioMarty = isParioMarty;
  }

  /**
   * Initializes the board display with the given tile width, tile height and tile types.
   *
   * @param tileWidth  the width of the each tile.
   * @param tileHeight the height of the each tile.
   * @param tileTypes  a {@link HashMap} containing the tile types for each tile on the board. The
   *                   key designates what tile number it is.
   */
  public void init(int tileWidth, int tileHeight, Map<Integer, String> tileTypes) {
    boardGrid.setMaxWidth(tileWidth * COLS);
    boardGrid.setMaxHeight(tileHeight * ROWS);
    boardGrid.setAlignment(javafx.geometry.Pos.CENTER);

    playersGrid.setMaxWidth(tileWidth * COLS);
    playersGrid.setMaxHeight(tileHeight * ROWS);
    playersGrid.setAlignment(javafx.geometry.Pos.CENTER);

    // fill out the grid with empty tiles if it is a ParioMarty board, because of the more complex
    // tile layout
    if (isParioMarty) {
      fillOutEmptyGrids(tileWidth, tileHeight);
    } else {
      boardGrid.getStyleClass().add("board");
    }

    tileTypes.keySet().forEach(t ->
        createTile(t, tileWidth, tileHeight)
    );

    // set tile styling for the tiles

    if (isParioMarty) {
      setParioMartyGameStyles(tileTypes);
    } else {
      setLadderGameStyles(tileTypes);
    }

  }

  public Map<Integer, FlowPane> getGridTiles() {
    return gridTiles;
  }

  @Override
  public Node getComponent() {
    return boardPane;
  }

  private void fillOutEmptyGrids(int tileWidth, int tileHeight) {
    IntStream.range(0, ROWS).forEach(row ->
        IntStream.range(0, COLS).forEach(col -> {
          if (gridTileStack.get(row * COLS + col) == null) {
            Pane emptyTile = new StackPane();
            emptyTile.setMinWidth(tileWidth);
            emptyTile.setMinHeight(tileHeight);
            emptyTile.setMaxWidth(tileWidth);
            emptyTile.setMaxHeight(tileHeight);
            boardGrid.add(emptyTile, col, row);
          }
        })
    );
  }

  private void createTile(int t, int tileWidth, int tileHeight) {
    StackPane tilePane = new StackPane();
    tilePane.setMinWidth(tileWidth);
    tilePane.setMinHeight(tileHeight);
    tilePane.setMaxWidth(tileWidth);
    tilePane.setMaxHeight(tileHeight);

    int row = gameController.getBoard().tiles().get(t).getOnscreenPosition()[1];
    int col = gameController.getBoard().tiles().get(t).getOnscreenPosition()[0];

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
    tilePane.getChildren().add(tileNumber);
    StackPane.setAlignment(tileNumber, Pos.TOP_RIGHT);

    boardGrid.add(tilePane, col, row);
    playersGrid.add(playersBox, col, row);
    gridTileStack.put(t, tilePane);
    gridTiles.put(t, playersBox);
  }

  private void setLadderGameStyles(Map<Integer, String> tileTypes) {
    gridTileStack.keySet().forEach(t -> {
      switch (tileTypes.get(t)) {
        case "LadderTile" -> {
          int destTileNum = gameController.getLadderDestinationTileNumber(t);

          /*logger.log(Level.INFO, "Ladder tile " + t + " has destination tile "
              + gameController.getLadderDestinationTileNumber(t));*/
          int deltaTileNumber = destTileNum - t;

          if (deltaTileNumber > 0) {
            gridTileStack.get(t).getStyleClass().add("ladder-tile-positive");
            gridTileStack.get(destTileNum).getStyleClass()
                .add("ladder-tile-positive-destination");
          } else {
            gridTileStack.get(t).getStyleClass().add("ladder-tile-negative");
            gridTileStack.get(destTileNum).getStyleClass()
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

        case "WinnerTile" -> gridTileStack.get(t).getStyleClass().add("winner-tile");

        default -> gridTileStack.get(t).getStyleClass().add("normal-tile");

      }

    });

    // set start tile styling
    gridTileStack.get(1).getStyleClass().add("start-tile");
    Label startLabel = new Label("START");
    gridTileStack.get(1).getChildren().add(startLabel);
    startLabel.toBack();

    boardPane.getChildren().addAll(boardGrid, ld, playersGrid);
  }

  private void setParioMartyGameStyles(Map<Integer, String> tileTypes) {
    gridTileStack.keySet().forEach(t -> {
      switch (tileTypes.get(t)) {
        case "AddCoinsTile" -> {
          gridTileStack.get(t).getStyleClass().add("add-coins-tile");
          ImageView icon = new ImageView(
              new Image(Objects.requireNonNull(
                  this.getClass()
                      .getResourceAsStream("/Images/boards/tile-icons/add_coin.png"))));
          icon.setFitWidth(60);
          icon.setFitHeight(54);

          gridTileStack.get(t).getChildren().add(icon);
          icon.toBack();
        }

        case "RemoveCoinsTile" -> {
          gridTileStack.get(t).getStyleClass().add("remove-coins-tile");
          ImageView icon = new ImageView(
              new Image(Objects.requireNonNull(
                  this.getClass()
                      .getResourceAsStream("/Images/boards/tile-icons/remove_coin.png"))));
          icon.setFitWidth(55);
          icon.setFitHeight(51);

          gridTileStack.get(t).getChildren().add(icon);
          icon.toBack();
        }

        case "AddCrownTile" -> {
          gridTileStack.get(t).getStyleClass().add("add-crown-tile");
          ImageView icon = new ImageView(
              new Image(Objects.requireNonNull(
                  this.getClass()
                      .getResourceAsStream("/Images/boards/tile-icons/add_crown.png"))));
          icon.setFitWidth(60);
          icon.setFitHeight(54);

          gridTileStack.get(t).getChildren().add(icon);
          icon.toBack();
        }

        case "MowserTile" -> {
          gridTileStack.get(t).getStyleClass().add("mowser-tile");
          ImageView icon = new ImageView(
              new Image(Objects.requireNonNull(
                  this.getClass()
                      .getResourceAsStream("/Images/boards/tile-icons/mowser.png"))));
          icon.setFitWidth(60);
          icon.setFitHeight(54);

          gridTileStack.get(t).getChildren().add(icon);
          icon.toBack();
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

        case "WinnerTile" -> gridTileStack.get(t).getStyleClass().add("winner-tile");

        default -> gridTileStack.get(t).getStyleClass().add("normal-tile");
      }

    });

    // set start tile styling
    gridTileStack.get(1).getStyleClass().add("start-tile");
    Label startLabel = new Label("START");
    gridTileStack.get(1).getChildren().add(startLabel);
    startLabel.toBack();

    boardPane.getChildren().addAll(boardGrid, ld, playersGrid);
  }

  /**
   * Draws the ladders on the board. This method is called when the game is started, and it draws
   * the ladders based on their positions on the board.
   *
   * @param tileTypes      a {@link Map} containing the tile types for each tile on the board. The
   * @param tileDimensions an array containing the width and height of the tiles.
   */
  public void drawLadders(Map<Integer, String> tileTypes, int[] tileDimensions) {
    Platform.runLater(() -> gridTileStack.keySet().stream()
        .filter(i -> tileTypes.get(i).equals("LadderTile"))
        .forEach(t -> {
          int destTileNum = gameController.getLadderDestinationTileNumber(t);
          Bounds startBounds = gridTileStack.get(t).localToScene(
              gridTileStack.get(t).getBoundsInLocal());
          double[] startPos = {
              startBounds.getMinX() + (double) tileDimensions[0] / 2,
              startBounds.getMinY() + (double) tileDimensions[1] / 2};

          Bounds endBounds = gridTileStack.get(destTileNum).localToScene(
              gridTileStack.get(destTileNum).getBoundsInLocal());
          double[] endPos = {
              endBounds.getMinX() + (double) tileDimensions[0] / 2,
              endBounds.getMinY() + (double) tileDimensions[1] / 2};

          ld.draw(startPos, endPos, tileDimensions);
        }));
  }
}