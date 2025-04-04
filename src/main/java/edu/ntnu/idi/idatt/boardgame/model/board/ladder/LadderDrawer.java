package edu.ntnu.idi.idatt.boardgame.model.board.ladder;

import edu.ntnu.idi.idatt.boardgame.model.board.Board;
import edu.ntnu.idi.idatt.boardgame.model.board.tile.LadderTile;
import javafx.scene.canvas.Canvas;

public class LadderDrawer {
  private final Board board;
  private final Canvas canvas;

  public LadderDrawer(Board board) {
    this.board = board;
    this.canvas = new Canvas(800, 800); // Example size, adjust as needed
  }

  public void draw() {
    // This could involve rendering the board, players, and other game elements
    System.out.println("Drawing the ladder game...");
    switch (board.getBoardType()) {
      case LADDER_GAME_VANILLA -> drawVanillaLadders();
      case LADDER_GAME_SPECIAL -> drawSpecialLadders();
      default -> throw new IllegalStateException("Unexpected value: " + board.getBoardType());
    }
  }

  private void drawVanillaLadders() {
    // Logic to draw the vanilla ladder game
    System.out.println("Drawing the vanilla ladder game...");
  }

  private void drawSpecialLadders() {
    // Logic to draw the special ladder game
    System.out.println("Drawing the special ladder game...");

    board.getTiles().values().stream()
        .filter(t -> t.getTileType().equals("LadderTile"))
        .forEach(t -> {
          // Draw the ladder tile on the canvas
          System.out.println("Drawing ladder tile: " + t.getTileNumber());
          drawLadder();
        });
  }

  private void drawLadder() {
    
  }

  public void update() {
    clear();
    draw();
  }

  public void clear() {
    // Clear the canvas
    System.out.println("Clearing the canvas...");
    canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }
}
