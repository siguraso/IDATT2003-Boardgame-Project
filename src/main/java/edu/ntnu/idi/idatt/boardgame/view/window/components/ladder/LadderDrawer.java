package edu.ntnu.idi.idatt.boardgame.view.window.components.ladder;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;

public final class LadderDrawer extends Canvas {
  private final GraphicsContext gc = getGraphicsContext2D();

  public LadderDrawer() {
    this.setWidth(800);
    this.setHeight(800);
  }

  public LadderDrawer(double width, double height) {
    this.setWidth(width);
    this.setHeight(height);
  }

  public void draw(double[] startPos, double[] endPos) {
    // This could involve rendering the board, players, and other game elements
    gc.beginPath();
    gc.setStroke(Paint.valueOf("Black"));
    gc.setLineCap(StrokeLineCap.ROUND);
    gc.setLineWidth(10);

    Point2D canvasStart = sceneToLocal(startPos[0], startPos[1]);
    Point2D canvasEnd = sceneToLocal(endPos[0], endPos[1]);

    gc.moveTo(canvasStart.getX(), canvasStart.getY());
    gc.lineTo(canvasEnd.getX(), canvasEnd.getY());
    gc.stroke();
    gc.closePath();
  }

  public void clear() {
    // Clear the canvas
    System.out.println("Clearing the canvas...");
    //canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }
}
