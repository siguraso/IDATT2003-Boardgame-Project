package edu.ntnu.idi.idatt.boardgame.view.window.components.ladder;

import java.util.stream.IntStream;
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

  public void draw(double[] startPos, double[] endPos, int[] dimensions) {
    // This could involve rendering the board, players, and other game elements
    gc.beginPath();
    gc.setStroke(Paint.valueOf("Black"));
    gc.setLineCap(StrokeLineCap.ROUND);
    gc.setLineWidth(4);

    Point2D start = sceneToLocal(startPos[0], startPos[1]);
    Point2D end = sceneToLocal(endPos[0], endPos[1]);

    // Directional vector from start to end
    double dx = end.getX() - start.getX();
    double dy = end.getY() - start.getY();
    double length = Math.hypot(dx, dy);

    // perpendicular vector
    double nx = -dy / length;
    double ny = dx / length;

    // width of the ladder
    double widthLadder = (double) dimensions[0] / 4;

    // compute rail positions
    Point2D leftStart = new Point2D(start.getX() + nx * widthLadder,
        start.getY() + ny * widthLadder);
    Point2D leftEnd = new Point2D(end.getX() + nx * widthLadder,
        end.getY() + ny * widthLadder);

    Point2D rightStart = new Point2D(start.getX() - nx * widthLadder,
        start.getY() - ny * widthLadder);
    Point2D rightEnd = new Point2D(end.getX() - nx * widthLadder,
        end.getY() - ny * widthLadder);

    //Referance to the middle of the ladder. Can be removed if necessary.
    /*
    gc.moveTo(start.getX(), start.getY());
    gc.lineTo(end.getX(), end.getY());
    */

    // Draw the side rails
    gc.moveTo(leftStart.getX(), leftStart.getY());
    gc.lineTo(leftEnd.getX(), leftEnd.getY());

    gc.moveTo(rightStart.getX(), rightStart.getY());
    gc.lineTo(rightEnd.getX(), rightEnd.getY());


    // Draw steps in ladder
    int stepCount = (int) (length / ((double) dimensions[1] / 3));
    IntStream.range(0, stepCount).forEach(i -> {
      //Skipping the first and last step
      if (i == 0 || i == stepCount) {
        return;
      }

      double t = (double) i / stepCount;
      double x = start.getX() + t * dx;
      double y = start.getY() + t * dy;

      Point2D stepLeft = new Point2D(x + nx * widthLadder,
          y + ny * widthLadder);
      Point2D stepRight = new Point2D(x - nx * widthLadder,
          y - ny * widthLadder);

      gc.moveTo(stepLeft.getX(), stepLeft.getY());
      gc.lineTo(stepRight.getX(), stepRight.getY());
    });

    gc.stroke();
    gc.closePath();
  }

  public void clear() {
    // Clear the canvas
    System.out.println("Clearing the canvas...");
    //canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }
}
